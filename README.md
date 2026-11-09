# Agentic workflows

This repository contains shared SDD and engineering rules for home-automation, photography and plant-journal. [SDD](.agents/skills/sdd/SKILL.md) gives the applicable rules for each task.

Living docs give strategic project knowledge that code search cannot give.

Reviews must include source evidence and analysis of design signals. A format change does not correct a design defect.

All prose must obey [STE100 and high-signal writing](.agents/skills/sdd/writing/SKILL.md). Short text must also give information necessary for a decision.

## Resolve guidance

- Use machine-local Git configuration `agentic.workflowsPath` to select this checkout. Keep machine-local paths and revision pins out of consumer source.
- If Git tracks the global configuration, put the setting in untracked `~/.gitconfig.local`. Include that portable filename in the global configuration.
- The resolver reads local Git objects into an ignored task snapshot. It does not change the checkout or use the network.
- Update this checkout between tasks. Keep the recorded revision for each task.
- A new task rejects a reference checkout with uncommitted changes. A task with a recorded revision can continue during subsequent edits.
- Use pinned revisions for evaluation cases. Keep evaluation pins apart from policy adoption for new tasks.

Do the guidance check with `python3 -B scripts/workflows.py check`.

Do the resolver tests with `python3 -B -m unittest discover -s scripts`.

## Evaluate agent behavior

Automated checks examine packaging, not design, strategic signal or STE compliance. For behavior evaluation, examine the decisions and outputs.

Compare revisions with the same source, harness and model. Include an addition with precedent, a new responsibility, a design signal, a lifecycle spec and an interrupted task.

Record active time, approval delays, exploration, duplicate checks and maintainer assessment. Reject faster work that gives incorrect results.
