# Design decision

For spec-backed model changes, include this decision in the spec PR and link it from the proposal; chat and ignored work state cannot substitute for reviewed design. For work without a spec, present it in chat when execution requires approval and record that approval in work state.

## Existing model

Name the concepts, valid states, transitions and current owner affected by the decision. Cite the existing flow or implementation that establishes them.

## Proposed change

| Decision needing approval | Existing owner or contract | Proposed evolution | Why the existing model is insufficient | Affected supplier/consumer and proof boundary |
| --- | --- | --- | --- | --- |

- Include changed guarantees, effects, lifetimes, configuration ownership or ordering only when they bear on the decision.
- New types, operations and ports must represent a real distinction or independently changing responsibility. Explain why refining the existing owner does not suffice.
- State the recommended choice, its actual cost and any unanswered question. Approval covers this decision, not unrelated later changes.
