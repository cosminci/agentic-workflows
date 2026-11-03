---
name: sdd
description: Use for product changes, investigations, change specs and living-document synchronization.
---
# SDD

- Follow [execution](executor.md); adapt existing patterns within authorized scope.
- Resolver fields: `project` is the Git root; `owner` contains product documentation and commands.

## Context routing

| When | Read |
| --- | --- |
| Starting work, including investigation or clarification | Project AGENTS, project `.agents/skills/sdd/local.md` and [execution: understand and decide](executor.md#understand-and-decide) |
| Changing domain, use cases or ports | [Principles](principles.md); use the [design decision](templates/design-map.md) when execution requires approval |
| Implementing | [Principles](principles.md), [conventions](conventions.md), selected profile and [quality gates](references/quality-gates.md); load its examples and local contracts for each changed surface |
| Writing a proposal | [Change template](templates/change-spec.md), [principles](principles.md) and [spec gates](references/quality-gates.md#spec-and-completion); model evolution is mandatory review content |
| Reviewing or repairing | [Principles](principles.md), applicable [conventions](conventions.md), selected profile and [quality gates](references/quality-gates.md); [review protocol](reviewer.md) and [review template](templates/review.md) for independent review |
| Planning Doc Sync or changing living docs | [Context policy and templates](context.md) |

## Approvals and delivery

- Approval covers only the stated scope. Spec approval, design approval and merge permission are distinct.
- When a spec is required, merge its initial PR before implementation; amendments discovered during implementation follow execution's amendment rule. Prefer one cohesive implementation PR; split only for independently useful delivery or a maintainer request.
- Each implementation PR works without later PRs. Include call-site migrations; resolve sequencing conflicts with the maintainer.
- When delivery is split, keep one feature branch. After each authorized merge, fetch main and reconcile without replaying squash-merged work.
- Preserve other agents' edits; use a separate worktree when another agent owns the checkout.
- Production-state changes, destructive operations and rule exceptions require explicit scoped authorization.
- Publication requires execution/local checks and review; merge requires maintainer authorization.
