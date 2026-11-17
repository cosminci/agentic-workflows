# Engineer-owned design

Use this process for unsettled intent, consequential design or spec amendments. Execution determines when a spec is necessary.

Use [execution](executor.md#understand-and-decide) for decision authority and experiment rules.

The agent collects evidence, explains options and recommends decisions. The maintainer makes the decisions, including when asked to delegate judgment.

## Discovery and decisions

1. Read the request, living docs, prior decisions and affected source, callers, tests and schemas.
2. Discuss sourced facts, unknowns, intent, scope and expected outcomes.
3. Recommend supporting activities only for specific uncertainties. Run them with maintainer agreement; otherwise clarify directly.
4. Discuss findings, alternatives and tradeoffs. Review behavior and design surfaces; collect evidence or missing decisions until confirmed.
5. Select the outcome: direct implementation for settled work, a spec when warranted, no change or an inconclusive report.
6. For a warranted spec, record agreed results in `proposal.md` for maintainer review. Correct document errors; reopen design questions for a decision before approval.

Ask one focused question at a time, resolving prerequisites first. Give a recommendation with its reason and cost, not an assumed decision.

| Supporting activity | When it helps |
| --- | --- |
| Focused grilling | Challenge assumptions, failure cases or choices during design review. |
| Domain modeling | Clarify concepts, valid states and responsibility boundaries. |
| Brainstorming | Explore an unclear problem or several plausible approaches. |
| Prototype or spike | Resolve a concrete behavior or feasibility question through an agreed experiment. |

An experiment that operates correctly does not prove production readiness.

### Completion before a draft

Get explicit maintainer confirmation for each [proposal section](templates/change-spec.md) and each design surface.

Cover APIs/domain types, dependency direction/composition/external boundaries, storage/migrations, and retained/changed/retired responsibilities and contracts.

Record decisions, discovery evidence, approval scope, confirmed non-applicability and unchanged-surface findings in work state. Evidence alone does not supply confirmation.

Keep rejected and superseded choices in that record.

Reuse prior approvals only after discussing their source and continued applicability with the maintainer. Retained design can receive grouped confirmation.

Reused contracts keep their existing behavior. Missing confirmations or unresolved requirements and design choices block drafting.

## Spec preparation

Use the change template to write the confirmed design, including its technical choices, in one `proposal.md`.

Source facts describe existing behavior. A proposed API, guarantee or migration requires a confirmed design decision, including for safety.

Get maintainer review of the draft. Separate document corrections from questions that require another design decision.

Use execution for [approval and delivery](executor.md#approvals-and-delivery).
