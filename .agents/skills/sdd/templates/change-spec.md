# <Change>

- **Classification:** feature | investigation
- **Evidence:** baseline behavior or experiment findings from a session with approval

For investigations, include the cause with source evidence.

Give links to evidence for existing behavior. Put sourced requirements, decisions with approval, proposals and open questions in different categories.

## What and why

Give the change from baseline to intended observable behavior, with its reason.

## Contracts

Give changed guarantees, transition/failure order and the enforcing owner.

Give storage constraints and business decisions different roles.

Remove this section when it gives no necessary information.

## Model evolution

Give changed domain concepts, responsibilities and contracts for review before implementation.

| Baseline concept / responsibility / boundary | Proposed evolution | Invariants, inputs/results and failure semantics | Changed suppliers / consumers |
| --- | --- | --- | --- |

Give baseline owners and changed responsibilities.

Compare new responsibilities with contract reuse, evolution and removal.

Record areas without changes and choices without decisions. Get review of important model choices before implementation.

## Acceptance

Give one observable behavior for each criterion with applicable failure, boundary and accessibility cases.

Keep additions to baseline requirements as proposals until approval.

## Doc Sync

Use `None` as the default. Use [strategic admission](../context.md#admission-test) before proposed text.

For `None`, record examined owners and evidence that their strategic statements are correct.

If the change keeps all doc owners' facts correct, give the source evidence for that conclusion.

| Owning document / section | Changed or missing strategic fact | Reader decision and effect | Proposed delta |
| --- | --- | --- | --- |

Keep implementation discovery out of strategic deltas.

For provided HTTP docs, give a link to OpenAPI. For consumed boundaries, show the correct protocols and exchanges.

Add invariants, tradeoffs, alternatives or scope only for a necessary decision.

Keep scope exclusions as proposals until agreement.

Use a linked [design decision](design-map.md) for types and signatures.

Keep file layouts, diffs and test inventories in implementation work.

Make sure that each section adds different information.

For each tradeoff, give its cost and the person or system with that cost.
