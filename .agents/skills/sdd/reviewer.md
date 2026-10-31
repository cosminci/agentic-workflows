# Independent review

- Execution and local policy decide whether independent review is required. When required, use one review phase per PR and [the review template](templates/review.md).
- Start a fresh, messageable reviewer with scope, baseline/candidate, sources and report path; save its ID. It reads source before assessing conclusions; present disputed questions without a predetermined verdict.
- Account for every candidate file. Check precedent fit for adaptations; trace states, owners, consumers and failures more deeply when they changed.
- Reuse applicable preparatory evidence; independently verify conclusions, not every preparatory command.
- Report the candidate, files inspected, findings and unresolved questions. Each finding names the rule, source location and concrete consequence; a design signal needs analysis under [guardrails](principles.md#guardrails), not a threshold-only verdict.
- The reviewer neither edits nor grants merge permission. The implementer fixes findings or rejects them with source evidence; only the maintainer approves exceptions.
- Send repairs and affected late changes to the same reviewer. Independently verify those changes and their consequences against the actual candidate. Ensure affected checks cover that candidate; reuse applicable passing results unless the repair or review introduces changed evidence or a new question.
- Replace an unavailable reviewer with the saved report and remaining questions. Repeat the whole review only by explicit decision.
- Finish when every candidate file is accounted for, findings are resolved, repairs are verified and required checks pass.
- For a maintainer-reported miss, inspect the failed decision and affected consequences. When changing a review rule, replay the missed case and a valid control; ordinary implementation repairs use their affected checks.
