# Review

## Self-review

- Read each candidate file against [quality gates](references/quality-gates.md), conventions, stack/local contracts and examples.
- Get conclusions from requirements with approval and source after the last edit.
- Keep previous implementation pass labels out of the evidence.
- Fill the [review evidence tables](templates/review.md) in ignored work state.
- Record the specified candidate, examined scope, findings and decisions.
- Examine [Doc Sync admission](context.md#admission-test) and `None` decisions against docs whose facts can change.
- Candidate-file coverage alone cannot show a missing doc edit.
- Put maintainer intent and implementation conclusions in different categories.
- Get maintainer approval for exceptions.
- Examine repairs, subsequent edits and their effects against the candidate.
- Open only gates and checks whose evidence changed.
- Close only with all files examined, corrected findings and mandatory checks without errors.

## Independent review

- Use execution and local policy for mandatory independent-review decisions.
- When independent review is mandatory, keep one review phase for each PR.
- Start a new reviewer that can receive messages.
- Give scope, baseline/candidate, maintainer intent, decisions, constraints, sources, open questions and report path.
- Save its ID.
- Give the reviewer source before implementer conclusions.
- Keep disputed questions open, without a predetermined verdict.
- Examine each applicable gate independently of executor statuses.
- Architecture and conventions can reject a candidate with tests without errors.
- Record a review status for each candidate file.
- Examine changed states, owners, callers and failures.
- Make sure that precedent is applicable to the changed design.
- Use valid preparatory evidence without duplicate commands.
- Examine conclusions independently.
- Fill gate, signal, suite and doc-admission evidence from source after the last edit.
- Include docs without edits whose facts can change, and `None` decisions.
- Until the reviewer examines an area, keep it open.
- Report the candidate, examined files, findings and open questions.
- Give each finding its rule, source location and effect.
- Examine design signals against [guardrails](principles.md#guardrails).
- A threshold-only verdict or format-only change does not show correct design.
- Keep review, edits and merge permission as different actions.
- Correct findings or reject them with source evidence.
- Examine a suggestion's rule, evidence and effect before acceptance.
- Get maintainer decisions for proposed scope or guarantee changes.
- Send repairs and subsequent edits to the same reviewer.
- Examine their source and results whose evidence changed.
- Keep checks without errors unless changed evidence or a new question makes a new check necessary.
- If the reviewer is not available, give its replacement the saved report and open questions.
- Do the full review again only after a maintainer decision.
- Close only with all files examined, corrected findings, repair verification and mandatory checks without errors.

## Reported misses

- Examine the failed decision across the changed design and its consumers, not only the specified symptom.
- Find the cause: guidance without examination, a rule that is not clear, weak evidence or premature closure.
- For a changed review rule, independently replay the missed case and a valid control.
- Record the rule, verdict and result.
- Make sure that the replay distinguishes a structural defect from a justified signal.
- Added prose or a format token alone is not enforcement evidence.
- Use checks for the changed source for general implementation repairs.
- After more than one miss, examine the enforcement mechanism again, not a longer symptom list.
