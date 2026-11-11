# Engineer-owned design

Use this process before a spec or its amendment. Execution determines when a spec is necessary.

Use [execution](executor.md#understand-and-decide) for general decision authority and [experiment rules](executor.md#understand-and-decide).

The agent gathers evidence, explains options and recommends decisions. The maintainer makes the decisions, including when asked to delegate judgment.

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
    Decisions{"All required facts discussed<br/>and decisions confirmed?"}
    More["Gather evidence or request missing decision"]
    Draft["Draft spec and linked design<br/>from covered information"]
    Check{"Maintainer review"}
    Correct["Correct document"]
    Ready(["Approved spec package"])
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

Routine evidence-gathering choices remain with the agent.

| Supporting activity | When it helps |
| --- | --- |
| Focused grilling | Challenge assumptions, failure cases or choices during design review. |
| Domain modeling | Clarify concepts, valid states and responsibility boundaries. |
| Brainstorming | Explore an unclear problem or several plausible approaches. |
| Prototype or spike | Resolve a concrete behavior or feasibility question through an agreed experiment. |

These activities are optional, not a sequence. Supporting skills must preserve the ownership and approval rules here.

If the maintainer declines an activity, clarify directly or continue with sufficient evidence and explicit decisions.

A working experiment does not establish production readiness.

### Required design review

Present every applicable section of the [change](templates/change-spec.md) and [design](templates/design-map.md) templates for explicit confirmation.

Get confirmation when a surface does not apply. Evidence or precedent supplies facts, not permission to omit confirmation.

Retained design can receive grouped confirmation. Reuse prior approvals after discussing their source and continued applicability.

### Completion before a draft

Every spec fact must have been explicitly discussed with the maintainer. Give source evidence for technical facts.

Every requirement and design choice must have a maintainer decision or an applicable prior approval covered in that discussion.

Record discussion coverage, sources, decisions and approval scope in work state. Keep recommendations and unresolved questions there until decided.

Use execution's [investigation outcomes](executor.md#start) when the evidence supports no change or remains inconclusive.

## Spec preparation

Use the change template and linked design decision as one spec package.

Apply the pre-draft gate above to alternatives, exclusions and acceptance as well as the main design.

Populate required sections from that material. If necessary information is missing, return to discovery instead of inventing content.

Get maintainer review of the draft. Separate document corrections from questions that require another design decision.

Use execution for [approval and delivery](executor.md#approvals-and-delivery).

### Implementation handoff

This diagram is a navigation map for [execution](executor.md#implement-and-check), not another rule owner.

```mermaid
flowchart TD
    Start(["Implementation"])
    Read["Read approved spec, linked design and current source"]
    Ready{"Execution prerequisites satisfied?"}
    Missing["Request missing decision or permission"]
    Design["Return to discovery and execution's amendment path"]
    Tests["Follow execution's test-first step"]
    Implement["Follow implementation and Doc Sync steps"]
    Question{"New design decision needed?"}
    Check["Run applicable checks and required review"]
    Fix["Correct implementation defects within approval"]
    Publish["Follow publication approval"]
    Merge["Follow merge approval"]
    Merged["Complete execution's deliveries"]
    Docs["Follow execution's completion and archive steps"]
    Done(["Complete"])

    Start --> Read --> Ready
    Ready -->|No| Missing
    Missing -->|Design decision| Design --> Read
    Missing -->|Permission supplied| Read
    Ready -->|Yes| Tests --> Implement --> Question
    Question -->|Yes| Design
    Question -->|No| Check
    Check -->|Implementation defect| Fix --> Implement
    Check -->|Design defect| Design
    Check -->|Checks and review complete| Publish --> Merge --> Merged --> Docs --> Done
```
