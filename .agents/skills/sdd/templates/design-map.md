# Design decision

For spec-backed model changes, include this decision in the spec PR.

Give a link from the proposal. Chat does not replace committed review content.

When execution makes approval mandatory without a spec, give the decision in chat.

Record approval in work state.

## Existing model

Give the concepts, states and transitions that can change, with their baseline owner.

Give the flow or source evidence for that model.

## Proposed change

| Decision for approval | Baseline owner or contract | Proposed evolution | Why the model is not sufficient | Changed supplier/consumer and proof boundary |
| --- | --- | --- | --- | --- |

- Include changed guarantees, effects, lifetimes, configuration ownership or ordering only for the decision.
- Add types, operations and ports only for a different concept or independent reason for change.
- Give the reason that evolution of the baseline owner is not sufficient.
- Give the recommendation, its cost and open questions.
- Keep approval restricted to this decision, not subsequent changes with different responsibilities.
