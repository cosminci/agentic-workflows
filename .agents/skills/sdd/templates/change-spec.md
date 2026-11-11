# <Change>

- **Classification:** feature | investigation
- **Evidence:** baseline behavior or experiment findings from a session with approval

For investigations, include the cause with source evidence.

Before a draft, complete [engineer-owned discovery and design review](../discovery.md).

Give links to evidence for existing behavior and the source authority for intended behavior.

## What and why

Give the change from baseline to intended observable behavior, with its reason.

Keep what/why and acceptance nonempty.

## Contracts

Give changed guarantees, valid states, transition/failure order and the enforcing owner.

Show the trigger-to-committed-state flow, including UI delivery where applicable.

Give storage constraints and business decisions different roles.

Remove this section when it gives no necessary information.

## Model evolution

Give changed domain concepts, responsibilities and contracts for review before implementation.

| Baseline concept / responsibility / boundary | Agreed evolution | Invariants, inputs/results and failure semantics | Changed suppliers / consumers |
| --- | --- | --- | --- |

Give baseline owners and changed responsibilities.

Compare new responsibilities with contract reuse, evolution and removal.

Record areas without changes.

## Acceptance

Give one observable behavior for each criterion, with its actor, condition, outcome and failure behavior.

Include applicable boundary and accessibility cases.

Give deferred slice criteria explicit owners.

## Doc Sync

Apply [strategic admission](../context.md#admission-test), including its `None` evidence rule.

| Owning document / section | Changed or missing strategic fact | Reader decision and effect | Proposed delta |
| --- | --- | --- | --- |

## Supporting decisions

Include invariants, tradeoffs, alternatives or scope only when necessary to understand the design.

Use a linked [design decision](design-map.md#spec-design) for concrete design.

Keep file layouts, diffs and test inventories in implementation work.

Use tables or diagrams for contracts and transitions. Add prose only for missing information.

For each tradeoff, give its cost and the person or system with that cost.
