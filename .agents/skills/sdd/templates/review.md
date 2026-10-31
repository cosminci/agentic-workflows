# Review record

- **Kind:** spec | implementation | archive/context
- **Baseline / exact candidate:** commit or content manifest
- **Files:** every changed/untracked candidate file and its review status
- **Sources:** applicable rule/contract/precedent pointers

Use the relevant section. Record findings and unresolved questions with source evidence; omit inapplicable steps rather than filling a checklist with N/A.

## Spec steps

1. Check scope, evidence and whether execution requires a spec.
2. Check normal behavior, failures, edge cases and where each guarantee is enforced.
3. Check that criteria are testable, sections add distinct information and the proposal describes behavior rather than implementation.
4. Check documentation ownership and Markdown source wrapping.

## Implementation steps

1. Verify authorized scope, precedent fit, required design approvals and that this PR works without later deliveries.
2. Trace changed responsibilities through existing data and consumers. Investigate more deeply for a new responsibility, changed contract or unresolved question.
3. For changed domain states, derivations, ordering or projections, check the existing lifecycle and the responsibility that owns each rule.
4. Check each changed API's callers, suppliers, inputs, valid outcomes and effects. Verify dependency lifetimes and resource/concurrency guarantees where affected.
5. Check applicable conventions. Explain the design concern behind any threshold violation and its repair or approved exception.
6. Check relevant behavior and edges at their owning tests, real boundary evidence, required automated checks and any required demonstration.

## Archive/context steps

1. Compare merged behavior with agreed scope and any required integrated checks.
2. Check that each documentation change reaches its owning section and matches current source.
3. Read complete affected documents against [documentation ownership and abstraction rules](../context.md). Check every addition for necessary system-level meaning, fit with existing contents, duplication of authoritative definitions and obsolete content; remove or replace misplaced detail with a source link before closure.
4. Confirm the archive location, that archival adds no behavior and that task cleanup is complete.

## Findings and closure

| ID | Rule / source location | Problem and consequence | Decision / repair evidence | Final-candidate verification |
| --- | --- | --- | --- | --- |

- **Unresolved findings / questions:** ...
- **Final candidate and assessment:** ...
- Record accepted repairs and affected late changes against their actual candidate; a previous pass cannot cover later edits.
