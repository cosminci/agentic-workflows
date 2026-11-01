# <Change>

- **Classification:** feature | investigation
- **Evidence:** current behavior or approved experiment findings; investigations include the established cause. Use pointers for implementation evidence.

## What and why

Current to intended observable behavior and reason.

## Contracts

Only changed domain or boundary guarantees, transition/failure order and the responsibility enforcing them. Distinguish storage constraints from business decisions. Omit when unnecessary.

## Model evolution

The spec is the maintainer's implementation-driving review contract: observable outcomes alone leave identity, ownership and boundary decisions undisclosed. Show how the existing domain, use cases and ports evolve so architectural gaps and competing authorities surface before implementation.

| Existing concept / operation / port | Proposed evolution | Invariants, inputs/results and failure semantics | Migrated suppliers / consumers |
| --- | --- | --- | --- |

Name concrete existing owners and changed types/operations; justify each new responsibility against reuse or evolution, including replaced or retired contracts. Mark unchanged surfaces and unresolved decisions explicitly; significant model choices require review rather than silent implementation discretion.

## Acceptance

Short observable criteria, including relevant failure/boundary/accessibility cases; one behavior each.

## Doc Sync

Owning document/section and the change to enduring system understanding. Link authoritative details; say `None` when unchanged.

Add existing invariants, actual tradeoffs or unresolved scope only when they add useful information. A linked [design decision](design-map.md) may carry concrete shapes and signatures as committed spec review content; file layouts, diffs and test inventories remain implementation work.
- A tradeoff names the actual cost and who bears it.
- Inspect the rendered proposal; each criterion states one observable behavior and each section adds unique information.
