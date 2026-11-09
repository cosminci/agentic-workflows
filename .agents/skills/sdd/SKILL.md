---
name: sdd
description: Use for product changes, investigations, change specs and updates to living docs.
---
# SDD

- Obey [execution](executor.md) in the scope of approval. Before you use a pattern, examine its design.
- Read [the eight engineering rules](foundations.md) for all work. Local rules can increase these obligations, but cannot decrease them.
- Use [STE100 and high-signal writing](writing/SKILL.md) for all prose. This rule includes guidance, responses, specs and reviews.
- In resolver output, `project` is the Git root. The `owner` directory contains product docs and commands.

## Context routing

| When | Read |
| --- | --- |
| Task start, investigation or clarification | Read the project AGENTS, the project `.agents/skills/sdd/local.md` and [execution: understand and decide](executor.md#understand-and-decide) |
| All prose | Read [writing](writing/SKILL.md) |
| Domain, usecase or port changes | Read [principles](principles.md). When execution makes approval mandatory, use the [design decision](templates/design-map.md) |
| Implementation | Read [principles](principles.md), [conventions](conventions.md), the selected profile and [quality gates](references/quality-gates.md). Read examples and local contracts for each changed boundary |
| Proposal | Read the [change template](templates/change-spec.md), [principles](principles.md) and [spec gates](references/quality-gates.md#spec-and-completion). Include model evolution in the review |
| Review or repair | Read [principles](principles.md), [conventions](conventions.md), the selected profile, [quality gates](references/quality-gates.md), the [review protocol](reviewer.md) and [review template](templates/review.md) |
| Doc Sync or living-doc changes | Read [strategic admission and context ownership](context.md) |

## Approvals and delivery

- Approval gives permission only for the specified scope. Spec approval, design approval and merge permission are different decisions.
- When a spec is mandatory, merge its first PR before implementation. Use execution's amendment rule for subsequent changes.
- Keep the implementation in one PR.
- For deliveries with independent value or a maintainer request, use separate PRs.
- Make each implementation PR compile and pass gates without subsequent PRs. Include caller migrations in that PR.
- Get a maintainer decision for delivery-order conflicts.
- For separate deliveries, keep one feature branch. After each permitted merge, fetch main.
- Update that branch without duplicate squash-merged work.
- Keep other agents' edits. If a different agent controls the checkout, use a separate worktree.
- Get maintainer approval for production-state changes, destructive actions and rule exceptions. Record the scope of that approval.
- Before publication, complete execution/local checks and review. Get maintainer permission before a merge.
