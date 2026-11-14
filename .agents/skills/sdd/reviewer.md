# Review procedure

Use execution's [review triggers](executor.md#implement-and-check) to select self-review or independent review.

This file owns review procedure and closure. [Quality gates](references/quality-gates.md) select evidence from rule owners, not substitute rules.

## Scope and preparation

1. Identify the baseline and final candidate, including every changed/untracked file and affected consumers outside the diff.
2. Read applicable owners through SDD routing and the boundary rows in quality gates.
3. Use approved requirements and final source, not previous pass labels or implementation reasoning, for conclusions.
4. Record evidence in the [review template](templates/review.md).

For architecture or code review, examine changed traits, implementations, callers and owning suites in full.

For documentation review, examine affected owners without edits as well as candidate files. Use context's admission test for `None`.

Use maintainer examples as evidence of a failure class, not the review's scope limit.

Keep tests, formatter results and precedent separate from architecture and design evidence.

## Examination and closure

- Apply each relevant rule against source. Record its guarantee or concern and the scope reason for any exclusion.
- Give each gate `pass`, `fail`, `blocked` or `not applicable`.
- Keep unread source, missing access/checks and unanswered design concerns open, not passed or inapplicable.
- Examine all eight foundations, even when no automation enforces a rule.
- Use [design-signal analysis](principles.md#guardrails) for suspected defects and evidence-backed kept signals.
- Record every acceptance criterion and edge case with a named scenario and test evidence at its enforcing boundary.
- Examine `proposal.md` through the spec evidence rows, including design evolution and supporting decisions.
- Examine source, diagrams and tables together for contradictory facts or duplicated ownership.
- Examine writing through its own source and dictionary, not wrapping results alone.
- Correct findings before checks for the repair.
- After a repair or later edit, examine conclusions whose source or dependencies changed.
- Use execution's check-reuse rules. Do more verification only for a new question or changed evidence.
- Get execution's scoped approval for proposed scope/guarantee changes or exceptions.
- Close only after all candidate files are examined, findings are corrected and mandatory checks have no errors.

Architecture, conventions and design findings remain blocking without a runtime bug. A coverage result does not prove complete behavior or design.

## Independent review

- Keep one review phase per PR when independent review is mandatory.
- Start a new reviewer that can receive messages. Save its ID and report path.
- Give scope, baseline/candidate, maintainer intent, decisions, constraints, sources, open questions and report path.
- Give source before implementer conclusions. Keep disputed questions open without a predetermined verdict.
- Require the reviewer to examine rules independently, including precedent applicability and affected document owners.
- Record a review status for each candidate file.
- For each finding, give its rule, source location, effect and necessary correction.
- Examine a suggestion's rule, evidence and effect before acceptance.
- Correct findings or reject them with source evidence.
- Send repairs and later edits to the same reviewer for focused verification.
- If that reviewer is unavailable, give its replacement the saved report and open questions.
- Repeat the full review only after a maintainer decision.
- Review grants neither edit nor merge permission.

Use execution's fallback when mandatory review is unavailable or prohibited.

## Reported misses

- Open the failed gate across the affected design and consumers, not only the reported example.
- Identify the cause: unclear ownership/rule, missing source examination, weak evidence or premature closure.
- For a changed review rule, independently replay the missed case and a valid control.
- Record candidate, rule, verdict and consumer effect using [replay examples](references/examples.md#review-replays).
- Verify the final repaired source. Added guidance or token detection alone is not enforcement proof.
- After more than one miss, examine the enforcement mechanism instead of expanding a symptom list.
