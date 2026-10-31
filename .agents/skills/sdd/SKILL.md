---
name: sdd
description: Use for product changes, investigations, change specs and living-document synchronization.
---
# SDD

- Use [execution](executor.md) for the current phase only. Established patterns discharge design choices: inspect their applicability, approve the delta, then adapt.
- Use the resolver's `project` for Git/evidence paths and `owner` for product documentation/commands.

## Context routing

| When | Read |
| --- | --- |
| Starting work | Project AGENTS and project `.agents/skills/sdd/local.md` |
| Changing domain, use cases or ports | [Principles](principles.md) and [design delta](templates/design-map.md) |
| Implementing | [Principles](principles.md), [conventions](conventions.md) and selected profile; its explicit stack exceptions take precedence |
| Writing a proposal | [Change template](templates/change-spec.md) |
| Reviewing or repairing | [Principles](principles.md), applicable [conventions](conventions.md), selected profile, [review protocol](reviewer.md) and [review template](templates/review.md) |
| Planning Doc Sync or changing living docs | [Context policy and templates](context.md) |

## Approvals and delivery

- Spec applicability, design approval, demonstration and merge are separate decisions; completion of one does not authorize another.
- For spec-backed work, merge the Spec PR first; deliver core domain/use cases/ports, dependency-ordered focused adapters, then frontend, then integrated verification and Archive + Living Docs.
- Each candidate builds with later slices absent. Mechanical existing-call-site migrations are explicit; sequencing conflicts require maintainer resolution, not placeholders.
- Use one feature branch across slices; after each authorized merge, fetch main and reconcile/rebase without replaying squash-merged work.
- Preserve other agents' edits; use a separate worktree when another agent owns the checkout.
- Production state, destructive operations and exceptions to governing rules require explicit scoped authorization.
- The publication gate is final-candidate review, repaired findings, applicable automated checks and maintainer confirmation of the delivered slice.
- Shared guidance plus each project's local wrappers fit 480 physical lines; the resolver's `check` enforces the effective budget. Product docs and executable tools are not instruction overflow.
