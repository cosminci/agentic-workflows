# Agentic workflows

- Shared SDD, engineering rules and context policy for home-automation, photography and plant-journal.
- Consumers resolve this checkout through machine-local Git configuration `agentic.workflowsPath`; no personal path or revision pin belongs in their source.
- If global Git configuration is tracked, put this setting in untracked `~/.gitconfig.local` and include that portable filename.
- The resolver reads local Git objects and writes an ignored task snapshot; it never clones, fetches, pulls or changes a checkout.
- Refresh this shared checkout deliberately between tasks; a task keeps its recorded revision.
- Evaluation examples and red/green cases use pinned source revisions, separate from live policy adoption.
- Run `python3 -B scripts/workflows.py check` and `python3 -B -m unittest discover -s scripts`.
