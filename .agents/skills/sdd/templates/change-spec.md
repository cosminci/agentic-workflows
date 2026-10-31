# <Change>

- **Classification:** feature | investigation
- **Evidence:** current behavior or approved experiment findings; investigations include the established cause. Use pointers for implementation evidence.

## What and why

Current to intended observable behavior and reason.

## Contracts

Only changed domain or boundary guarantees, transition/failure order and the responsibility enforcing them. Distinguish storage constraints from business decisions. Omit when unnecessary.

## Acceptance

Short observable criteria, including relevant failure/boundary/accessibility cases; one behavior each.

## Doc Sync

Owning document/section and the change to enduring system understanding. Link authoritative details; omit when unchanged.

Add existing invariants, actual tradeoffs or unresolved scope only when they add useful information. Implementation signatures and layout belong in the design decision, not this proposal.
- A tradeoff names the actual cost and who bears it.
- Inspect the rendered proposal; each criterion states one observable behavior and each section adds unique information.
