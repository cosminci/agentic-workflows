# Design decision

Use this template in chat when execution requires a design decision without a spec.

For spec-backed work, use the single-file [change template](change-spec.md).

## Existing model

Give the concepts, states and transitions that can change, with their baseline owner.

Give the flow or source evidence for that model.

## Proposed change

| Decision for approval | Baseline owner or contract | Proposed evolution | Why the model is not sufficient | Changed supplier/consumer and proof boundary |
| --- | --- | --- | --- | --- |

- Include changed guarantees, effects, lifetimes, configuration ownership or ordering only for the decision.
- Give the [contract-evolution comparison](../principles.md#evolve-existing-responsibilities) for added types, operations and ports.
- Give the recommendation, its cost and open questions for the maintainer's decision.
- State the decision's approval scope.
