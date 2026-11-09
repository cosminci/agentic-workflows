# Review record

- **Kind:** spec | implementation | archive/context
- **Baseline / specified candidate:** commit or content manifest
- **Files:** all changed/untracked candidate files and their review status
- **Sources:** applicable rule/contract/precedent pointers

Use the applicable steps and evidence tables. Until the reviewer examines a check, keep it open.

## Spec steps

1. Examine scope, evidence and execution's spec criteria.
2. Examine normal behavior, failures, edge cases and the enforcing owner for each guarantee.
3. Make sure that criteria are testable.
4. Examine sections for different information and behavior-level intent, not implementation detail.
5. Examine Doc Sync and `None` decisions against docs whose facts can change.
6. Examine signal, clarity and Markdown source wrapping.

## Implementation steps

1. Examine scope of approval, precedent fit, mandatory design approvals and independent delivery.
2. Follow changed responsibilities through baseline data and consumers.
3. Examine new responsibilities, changed contracts and open questions in greater depth.
4. For domain states, derivations, ordering or projections, examine the lifecycle and each rule's owner.
5. Examine changed APIs through callers, suppliers, inputs, valid outcomes and effects.
6. Examine dependency lifetimes and resource/concurrency guarantees that can change.
7. Fill [gate evidence](#gate-evidence) from source with stack/local contracts and examples.
8. Examine each signal in [design signals](#design-signals).
9. Examine relevant behavior and edges at owning tests, external boundaries, mandatory automated checks and demonstrations with approval.

## Archive/context steps

1. Compare merged behavior with the scope of approval and mandatory integrated checks.
2. Make sure that each doc change is in its owning section and agrees with source.
3. Read all docs whose facts can change against [strategic admission](../context.md).
4. Examine reader value, protocol contracts, duplicate content and missing knowledge.
5. Examine archive location, behavior continuity and task cleanup.

## Gate evidence

| Gate | Examined files / declarations / calls / suites | Result and source evidence |
| --- | --- | --- |

Include each applicable gate and a scope reason for each exclusion.

Use `blocked` for missing access or inspection, not `not applicable`.

Record all eight foundations. A command without errors shows only its specified checks.

## Design signals

| Signal / source | Suspected design defect and effect | Alternatives | Decision and proof |
| --- | --- | --- | --- |

Include wide/shallow APIs, sequencing, fixture construction and suite size when present.

Record suite size before and after the change, with scenario responsibilities.

Give source evidence of cohesion for a kept signal.

Correct the defect without hidden syntax or relocated parameters.

## Suite ownership

| Usecase / port trait | One owning suite | Acceptance behaviors | Substituted collaborators / external seam |
| --- | --- | --- | --- |

Record a review status for each backend suite in the change and approval for other owners.

Domain types have no suites.

## Living-doc admission

| Examined owner / section | Decision: edit or None | Strategic fact / no-edit evidence | Reader effect / authority |
| --- | --- | --- | --- |

Include documents without edits whose facts can change, not only candidate files.

For `None`, give source evidence that the change keeps each doc owner's facts correct.

Keep source evidence here, not implementation pointers in living docs.

Examine reader value and duplicate content across those owners.

For provided HTTP APIs, examine OpenAPI and its links.

For consumed boundaries, examine correct protocols, endpoints and exchanges.

## Findings and closure

| ID | Rule / source location | Problem and effect | Decision / repair evidence | Candidate verification after the last edit |
| --- | --- | --- | --- | --- |

- **Open findings / questions:** ...
- **Writing:** all examined files, dictionary and grammar findings, technical-term categories, signal and clarity
- **Candidate after the last edit and assessment:** ...

Examine accepted repairs and subsequent changes against their candidate. A previous pass does not include subsequent edits.

Close only with all files examined, corrected findings and mandatory checks without errors.
