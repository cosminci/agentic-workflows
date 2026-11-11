---
name: sdd
description: Use for product changes, investigations, change specs and updates to living docs.
---
# SDD

Read the applicable owners below. Follow their conditional links for the changed boundary, not every document in this pack.

Each subject has one rule owner. Other documents link to it or supply a different artifact, application-specific constraint or concrete example.

When guidance changes, edit its owner. Move unique obligations before replacing another definition with a pointer.

Use foundations for universal minimums and execution for scoped approval and exception decisions.

## Context routing

| When | Read |
| --- | --- |
| Every task | Project AGENTS, project `.agents/skills/sdd/local.md`, [engineering foundations](foundations.md) and [execution](executor.md) |
| All prose | [Writing rules](writing/SKILL.md) |
| Design or implementation | [Design principles](principles.md) |
| Code changes | [Code conventions](conventions.md) and the selected [direct-style](profiles/direct-style.md) or [Typelevel](profiles/typelevel.md) profile |
| Pre-spec discovery or spec amendment | [Discovery and decision process](discovery.md) |
| Spec draft | [Change template](templates/change-spec.md) and linked [design template](templates/design-map.md) |
| Review or repair | [Review procedure](reviewer.md), which selects rule owners and evidence |
| Doc Sync or living-doc changes | [Knowledge admission and document ownership](context.md) |

## Specialized owners

| Subject | Owner and load condition |
| --- | --- |
| Review evidence | [Quality gates](references/quality-gates.md), selected by the review procedure |
| Review output | [Review record](templates/review.md), when recording a review |
| Application-specific differences | [Local contracts](references/local-contracts.md), only the applicable application section |
| Worked cases and replay fixtures | [Examples](references/examples.md), only the changed boundary or reported failure class |

Resolver output uses `project` for the Git root and `owner` for the directory with product docs and commands.
