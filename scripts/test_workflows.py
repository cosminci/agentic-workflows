import contextlib
import io
import json
import os
import shutil
import subprocess
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

import workflows


class WorkflowTest(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        base = Path(self.temporary.name)
        self.reference = (base / "reference").resolve()
        self.reference.mkdir()
        shutil.copytree(workflows.ROOT / ".agents", self.reference / ".agents")
        shutil.copytree(workflows.ROOT / "scripts", self.reference / "scripts",
                        ignore=shutil.ignore_patterns("__pycache__"))
        for name in ("AGENTS.md", "README.md", ".gitignore"):
            shutil.copyfile(workflows.ROOT / name, self.reference / name)
        self.git("init", "-q")
        self.commit()
        self.project = (base / "project").resolve()
        (self.project / workflows.SKILL).mkdir(parents=True)
        for name in ("AGENTS.md", "CONTRIBUTING.md", "DESIGN-PRINCIPLES.md",
                     ".agents/skills/sdd/local.md"):
            (self.project / name).write_text("# Local\n")
        (self.project / ".agents/workflows.json").write_text('{"owner":".","profile":"typelevel"}')
        self.root_patch = patch.object(workflows, "ROOT", self.reference)
        self.root_patch.start()
        self.addCleanup(self.root_patch.stop)

    def git(self, *args):
        return subprocess.run(["git", "-C", str(self.reference), *args],
                              capture_output=True, text=True, check=True).stdout.strip()

    def commit(self):
        self.git("add", ".")
        self.git("-c", "commit.gpgsign=false", "-c", "user.name=WorkflowTest",
                 "-c", "user.email=workflow@example.invalid", "commit", "-qm", "Fixture")

    def output(self, function, *args):
        with contextlib.redirect_stdout(io.StringIO()) as output:
            function(*args)
        return output.getvalue()

    def test_should_validate_shared_and_effective_guidance(self):
        self.assertLessEqual(workflows.validate(None), 480)
        self.assertLessEqual(workflows.validate(self.project), 480)

    def test_should_reject_missing_shared_owner(self):
        (self.reference / workflows.SKILL / "principles.md").unlink()
        with self.assertRaisesRegex(ValueError, "missing shared"):
            workflows.validate(None)

    def test_should_reject_local_instruction_overflow(self):
        directory = self.project / ".github/instructions"
        directory.mkdir(parents=True)
        (directory / "policy.md").write_text("\n" * 481)
        with self.assertRaisesRegex(ValueError, "physical lines"):
            workflows.validate(self.project)

    def test_should_reject_owner_escape(self):
        (self.project / ".agents/workflows.json").write_text('{"owner":"..","profile":"typelevel"}')
        with self.assertRaisesRegex(ValueError, "invalid workflow"):
            workflows.configuration(self.project)

    def test_should_reject_untyped_profile(self):
        (self.project / ".agents/workflows.json").write_text('{"owner":".","profile":[]}')
        with self.assertRaisesRegex(ValueError, "must be strings"):
            workflows.configuration(self.project)

    def test_should_snapshot_local_committed_context_without_remote(self):
        result = self.output(workflows.context, self.project, None)
        manifest = json.loads(result[result.index("{"):])
        self.assertEqual(manifest["revision"], self.git("rev-parse", "HEAD"))
        self.assertTrue(all(Path(path).is_file() for path in manifest["context"]))
        self.assertFalse(any(path.endswith("direct-style.md") for path in manifest["context"]))
        self.assertTrue(Path(manifest["entrypoint"]).is_file())
        self.assertTrue(Path(manifest["profileFile"]).is_file())
        self.assertTrue(Path(manifest["localGuidance"]).is_file())

    def test_should_reuse_old_revision_after_reference_changes(self):
        previous = self.git("rev-parse", "HEAD")
        original = self.output(workflows.context, self.project, previous)
        (self.reference / workflows.SKILL / "new.md").write_text("# New\n")
        self.commit()
        self.assertEqual(original, self.output(workflows.context, self.project, previous))

    def test_should_reject_dirty_reference(self):
        recorded = self.git("rev-parse", "HEAD")
        (self.reference / "uncommitted").write_text("pending\n")
        with self.assertRaisesRegex(ValueError, "dirty"):
            workflows.context(self.project, None)
        resumed = json.loads(self.output(workflows.context, self.project, recorded))
        self.assertEqual(resumed["revision"], recorded)

    def test_should_reject_modified_task_snapshot(self):
        output = self.output(workflows.context, self.project, None)
        manifest = json.loads(output[output.index("{"):])
        Path(manifest["context"][0]).write_text("modified\n")
        with self.assertRaisesRegex(ValueError, "snapshot was modified"):
            workflows.context(self.project, None)

    def test_should_reject_missing_committed_execution_source(self):
        (self.reference / workflows.SKILL / "executor.md").unlink()
        self.commit()
        with self.assertRaisesRegex(ValueError, "required source: executor"):
            workflows.context(self.project, None)

    def test_should_reject_missing_consumer_entrypoint(self):
        (self.project / "AGENTS.md").unlink()
        with self.assertRaisesRegex(ValueError, "missing local entrypoint"):
            workflows.context(self.project, None)

    def test_should_reject_deleted_or_extra_snapshot_sources(self):
        output = json.loads(self.output(workflows.context, self.project, None))
        target = Path(output["context"][0])
        original = target.read_text()
        target.unlink()
        with self.assertRaisesRegex(ValueError, "file set was modified"):
            workflows.context(self.project, None)
        target.write_text(original)
        (target.parent / "extra.md").write_text("# Extra\n")
        with self.assertRaisesRegex(ValueError, "file set was modified"):
            workflows.context(self.project, None)

    def test_should_reject_snapshot_root_and_ancestor_symlinks_before_writing(self):
        outside = self.project.parent / "outside"
        outside.mkdir()
        revision = self.git("rev-parse", "HEAD")
        for relative in (".agent-work", ".agent-work/workflows", f".agent-work/workflows/{revision}"):
            with self.subTest(relative=relative):
                link = self.project / relative
                link.parent.mkdir(parents=True, exist_ok=True)
                link.symlink_to(outside, target_is_directory=True)
                with self.assertRaisesRegex(ValueError, "path contains a symlink"):
                    workflows.context(self.project, None)
                self.assertFalse(any(outside.iterdir()))
                link.unlink()

    def test_should_resolve_machine_config_and_fail_without_it(self):
        subprocess.run(["git", "-C", str(self.project), "init", "-q"], check=True)
        config = self.project.parent / "machine.gitconfig"
        config.write_text("")
        environment = {**os.environ, "GIT_CONFIG_GLOBAL": str(config), "GIT_CONFIG_NOSYSTEM": "1"}
        command = ["bash", str(self.reference / "scripts/bootstrap.sh"), "context"]
        missing = subprocess.run(command, cwd=self.project, env=environment, capture_output=True, text=True)
        self.assertNotEqual(missing.returncode, 0)
        self.assertIn("machine-local git config", missing.stderr)
        subprocess.run(["git", "config", "--file", str(config), "agentic.workflowsPath",
                        str(self.reference / "missing")], check=True)
        unavailable = subprocess.run(command, cwd=self.project, env=environment, capture_output=True, text=True)
        self.assertNotEqual(unavailable.returncode, 0)
        self.assertIn("registered shared checkout is unavailable", unavailable.stderr)
        subprocess.run(["git", "config", "--file", str(config), "agentic.workflowsPath",
                        str(self.reference)], check=True)
        resolved = subprocess.run(command, cwd=self.project, env=environment, capture_output=True, text=True)
        self.assertEqual(resolved.returncode, 0, resolved.stderr)
        self.assertEqual(json.loads(resolved.stdout)["reference"], str(self.reference))


if __name__ == "__main__":
    unittest.main()
