# <Change>

Write only `proposal.md`. Replace instructions with confirmed changes.

Keep What and why, Acceptance and Doc Sync. Include other sections only when they contain agreed content.

- **Classification:** feature | investigation
Before a draft, complete [engineer-owned discovery and design review](../discovery.md).

Before drafting, select an approved proposal of comparable scope. Record the reference and a word limit of 125% of its count in work state.

Count the complete Markdown source, including tables, diagrams and code, with `len(re.findall(r"\b[\w]+(?:[-'][\w]+)*\b", text))`.

Before presentation and after each correction, run the count and record the result. A proposal above the limit is not ready for review.

Shorten repetition while preserving confirmed requirements and the required structure. If no comparable reference exists or necessary content cannot fit, get maintainer approval for a numeric limit.

## What and why

Give the agreed behavior change and its reason.

For investigations, include the cause with source evidence.

## Contracts

Give changed system guarantees, states and failure order. Include unchanged context only when necessary to interpret the change.

Name the enforcing owner and show the trigger-to-result flow, including UI delivery where applicable.

For writes, include the committed state.

Give required supplier and caller adaptations here.

Give compatibility requirements for changed APIs.

For changed service relationships, show dependency direction, composition and external boundaries without a module inventory.

For database changes, give schema relationships, cardinality, constraints, indexes, transaction boundaries and migration/compatibility decisions.

For each agreed tradeoff, give its cost and the person or system with that cost.

Use tables or diagrams for contracts and transitions. Add prose only for missing information.

## Design evolution

Give only new or changed signatures and type definitions for ports, usecases and domain types.

| API | Before | After |
| --- | --- | --- |

For additions, put `New` in Before.

When no APIs change, omit this heading and section.

## Acceptance

Give one observable behavior for each criterion, with its actor, condition, outcome and failure behavior.

Include applicable boundary and accessibility cases.

Give deferred slice criteria explicit owners.

## Doc Sync

Replace `None` only with a `Document / section | Required edit` table of edits that pass [strategic admission](../context.md#admission-test).

None
