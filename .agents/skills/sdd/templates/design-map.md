# Design decision

Use only when execution requires approval. Present the decision in chat and record its approval in work state.

## Existing model

Name the concepts, valid states, transitions and current owner affected by the decision. Cite the existing flow or implementation that establishes them.

## Proposed change

| Decision needing approval | Existing owner or contract | Proposed evolution | Why the existing model is insufficient | Affected supplier/consumer and proof boundary |
| --- | --- | --- | --- | --- |

- Include changed guarantees, effects, lifetimes, configuration ownership or ordering only when they bear on the decision.
- New types, operations and ports must represent a real distinction or independently changing responsibility. Explain why refining the existing owner does not suffice.
- State the recommended choice, its actual cost and any unanswered question. Approval covers this decision, not unrelated later changes.
