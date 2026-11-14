# Engineer-owned design

Use this process before a spec or its amendment. Execution determines when a spec is necessary.

Use [execution](executor.md#understand-and-decide) for decision authority and experiment rules.

The agent collects evidence, explains options and recommends decisions. The maintainer makes the decisions, including when asked to delegate judgment.

## Discovery and decisions

The diagram owns the activity order. The sections below define its decisions and completion conditions.

```mermaid
flowchart TD
    Start(["Design"])
    Read["Read request, living docs, prior decisions<br/>and affected source, callers, tests and schemas"]
    Evidence["Discuss sourced facts, unknowns,<br/>intent, scope and expected outcomes"]
    Need{"Supporting activity useful<br/>for a specific uncertainty?"}
    Offer["Recommend activity and purpose"]
    Agree{"Maintainer agrees?"}
    Activity["Run agreed discussion or experiment"]
    Direct["Clarify directly"]
    Findings["Discuss findings, alternatives and tradeoffs"]
    Review["Review the proposal and design surfaces"]
    Decisions{"Behavior and design<br/>confirmed?"}
    More["Collect evidence or request missing decision"]
    Draft["Draft proposal.md<br/>from the decision record"]
    Check{"Maintainer review"}
    Correct["Correct document"]
    Ready(["Approved proposal.md"])
    Report(["No change or inconclusive report"])

    Start --> Read --> Evidence --> Need
    Need -->|Yes| Offer --> Agree
    Agree -->|Yes| Activity --> Findings
    Agree -->|No| Direct --> Findings
    Need -->|No| Findings
    Findings --> Review --> Decisions
    Decisions -->|No| More --> Findings
    Decisions -->|Yes| Draft --> Check
    Check -->|Document correction| Correct --> Check
    Check -->|Design question| Review
    Check -->|Approved| Ready
    Review -->|Maintainer concludes| Report
```

Ask one focused question at a time. Give a recommendation with its reason and cost, not an assumed decision.

| Supporting activity | When it helps |
| --- | --- |
| Focused grilling | Challenge assumptions, failure cases or choices during design review. |
| Domain modeling | Clarify concepts, valid states and responsibility boundaries. |
| Brainstorming | Explore an unclear problem or several plausible approaches. |
| Prototype or spike | Resolve a concrete behavior or feasibility question through an agreed experiment. |

An experiment that operates correctly is not sufficient to make sure of production readiness.

### Completion before a draft

Get explicit maintainer confirmation for each [proposal section](templates/change-spec.md) and each design surface.

Cover APIs/domain types, dependency direction/composition/external boundaries, storage/migrations, and retained/changed/retired responsibilities and contracts.

Record decisions, source evidence, approval scope and confirmed non-applicability in work state. Evidence alone does not supply confirmation.

Keep rejected and superseded choices in that record.

Reuse prior approvals only after discussing their source and continued applicability with the maintainer. Retained design can receive grouped confirmation.

Reused contracts keep their existing behavior. Missing confirmations or unresolved requirements and design choices block drafting.

Use execution's [investigation outcomes](executor.md#start) when the evidence supports no change or remains inconclusive.

## Spec preparation

Use the change template to write the confirmed design, including its technical choices, in one `proposal.md`.

Keep discovery evidence, rejected options and unchanged-surface findings in work state.

Source facts describe existing behavior. A proposed API, guarantee or migration requires a confirmed design decision, including for safety.

Get maintainer review of the draft. Separate document corrections from questions that require another design decision.

Use execution for [approval and delivery](executor.md#approvals-and-delivery).
