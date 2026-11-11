# Design decision

For spec-backed model changes, include this decision in the spec PR.

For a spec, use [discovery](../discovery.md) before filling this template.

Give a link from the proposal. Chat does not replace committed review content.

When execution makes approval mandatory without a spec, give the decision in chat.

## Existing model

Give the concepts, states and transitions that can change, with their baseline owner.

Give the flow or source evidence for that model.

## Proposed change

| Decision for approval | Baseline owner or contract | Proposed evolution | Why the model is not sufficient | Changed supplier/consumer and proof boundary |
| --- | --- | --- | --- | --- |

- Include changed guarantees, effects, lifetimes, configuration ownership or ordering only for the decision.
- Give the [contract-evolution comparison](../principles.md#evolve-existing-responsibilities) for added types, operations and ports.
- Give the reason that evolution of the baseline owner is not sufficient.
- Without a spec, give the recommendation, its cost and open questions for the maintainer's decision.
- State the decision's approval scope.

For a spec, record the agreed choice and its cost.

## Spec design

For spec-backed work, use these surfaces in discovery and record the resulting design here.

| Surface | Required design content |
| --- | --- |
| Usecases and ports | Responsibilities, traits, operations, signatures, inputs, results and guarantees. |
| Service modules | Overall dependency graph, retained and changed relationships, dependency direction, composition and external adapters. |
| Database | Schema, relationships, cardinality, constraints, indexes, transaction boundaries and migration/compatibility decisions. |
| Existing design | Responsibilities and contracts retained, evolved or retired, with reasons and affected suppliers/consumers. |

Keep behavior and acceptance in the proposal. Link each design decision from it instead of copying concrete design there.

Record the applicability decision for unused surfaces.
