import argparse
import json
import subprocess
from pathlib import Path

from check_markdown_wrapping import check

ROOT = Path(__file__).resolve().parents[1]
SKILL = Path(".agents/skills/sdd")
LIMIT = 480
PROFILES = {"typelevel", "direct-style"}


def git(*args: str) -> str:
    result = subprocess.run(["git", "-C", str(ROOT), *args], text=True, capture_output=True)
    if result.returncode:
        raise ValueError(result.stderr.strip() or "shared checkout has no committed revision")
    return result.stdout.strip()


def shared_files(profile: str | None = None) -> list[Path]:
    return sorted(path for path in (ROOT / SKILL).rglob("*.md")
                  if profile is None or "profiles" not in path.parts or path.stem == profile)


def local_files(project: Path, owner: Path) -> list[Path]:
    files = set((project / SKILL).rglob("*.md"))
    files.update((project / ".agents").glob("*.md"))
    files.update((owner / "specs/templates").rglob("*.md"))
    files.update((owner / "frontend").glob("*PATTERNS*.md"))
    files.update((project / ".github/instructions").rglob("*.md"))
    for directory in {project, owner}:
        for name in ("AGENTS.md", "CLAUDE.md", "CONTRIBUTING.md", "DESIGN-PRINCIPLES.md"):
            path = directory / name
            if path.exists():
                files.add(path)
    wrapper = project / ".github/copilot-instructions.md"
    if wrapper.exists():
        files.add(wrapper)
    return sorted(files)


def configuration(project: Path) -> tuple[str, Path]:
    config = json.loads((project / ".agents/workflows.json").read_text())
    profile = config["profile"]
    if not isinstance(profile, str) or not isinstance(config["owner"], str):
        raise ValueError("workflow profile and owner must be strings")
    owner = (project / config["owner"]).resolve()
    if profile not in PROFILES or not owner.is_relative_to(project) or not owner.is_dir():
        raise ValueError("invalid workflow profile or owning directory")
    if not (project / SKILL / "local.md").is_file():
        raise ValueError("missing project-local guidance")
    return profile, owner


def validate(project: Path | None) -> int:
    for name in ("SKILL.md", "executor.md", "reviewer.md", "principles.md", "conventions.md",
                 "context.md", "templates/change-spec.md", "templates/design-map.md",
                 "templates/review.md", "profiles/typelevel.md", "profiles/direct-style.md"):
        if not (ROOT / SKILL / name).is_file():
            raise ValueError(f"missing shared guidance: {name}")
    if project is None:
        files = local_files(ROOT, ROOT) + [ROOT / "README.md"]
    else:
        profile, owner = configuration(project)
        entrypoints = {project / "AGENTS.md", owner / "AGENTS.md", owner / "CONTRIBUTING.md",
                       owner / "DESIGN-PRINCIPLES.md"}
        for path in entrypoints:
            if not path.is_file():
                raise ValueError(f"missing local entrypoint: {path}")
        files = shared_files(profile) + local_files(project, owner)
    total = sum(len(path.read_text().splitlines()) for path in files)
    if total > LIMIT:
        raise ValueError(f"guidance has {total} physical lines; limit is {LIMIT}")
    for path in files:
        errors = check(path)
        if errors:
            raise ValueError(f"{path}: invalid Markdown wrapping: {errors}")
    return total


def context(project: Path, revision: str | None) -> None:
    profile, owner = configuration(project)
    if git("status", "--porcelain"):
        raise ValueError("shared checkout is dirty; commit or resolve its edits before starting a task")
    revision = git("rev-parse", "--verify", f"{revision or 'HEAD'}^{{commit}}")
    contents = {}
    for name in git("ls-tree", "-r", "--name-only", revision, str(SKILL)).splitlines():
        relative = Path(name)
        if relative.suffix != ".md" or ("profiles" in relative.parts and relative.stem != profile):
            continue
        result = subprocess.run(["git", "-C", str(ROOT), "show", f"{revision}:{relative}"],
                                text=True, capture_output=True, check=True)
        contents[relative] = result.stdout
    total = sum(len(text.splitlines()) for text in contents.values())
    total += sum(len(path.read_text().splitlines()) for path in local_files(project, owner))
    if total > LIMIT:
        raise ValueError(f"guidance has {total} physical lines; limit is {LIMIT}")
    if SKILL / "SKILL.md" not in contents or SKILL / "profiles" / f"{profile}.md" not in contents:
        raise ValueError("shared revision lacks its router or selected profile")
    destination = project / ".agent-work/workflows" / revision
    destination.mkdir(parents=True, exist_ok=True)
    paths = []
    for relative, text in contents.items():
        target = destination / relative.relative_to(SKILL)
        target.parent.mkdir(parents=True, exist_ok=True)
        if target.exists() and target.read_text() != text:
            raise ValueError(f"workflow snapshot was modified: {target}")
        target.write_text(text)
        paths.append(str(target))
    print(json.dumps({"reference": str(ROOT), "revision": revision, "project": str(project),
                      "owner": str(owner), "profile": profile, "guidanceLines": total,
                      "context": paths}, indent=2))


def main() -> None:
    parser = argparse.ArgumentParser(description="Resolve local workflow context without network access.")
    parser.add_argument("command", choices=("context", "check"))
    parser.add_argument("--project", type=Path)
    parser.add_argument("--revision")
    args = parser.parse_args()
    project = args.project.resolve() if args.project else None
    try:
        if args.command == "check":
            print(f"{validate(project)}/{LIMIT} guidance lines")
        elif project is None:
            raise ValueError("context requires --project")
        else:
            context(project, args.revision)
    except (OSError, ValueError, KeyError, subprocess.CalledProcessError) as error:
        parser.exit(1, f"workflows: {error}\n")


if __name__ == "__main__":
    main()
