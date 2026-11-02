# Execution

## Start

- Read project entrypoints and task-relevant sources. On resumption, reuse recorded decisions and results; reread changed sources or those needed for a new question.
- Keep scope, baseline, workflow revision, decisions, test mapping and check results in ignored `.agent-work/<slug>/state.md`.
- For an investigation, establish the cause from evidence before choosing a repair. Record tested hypotheses and their results.
- Require a spec for a new user-visible capability, changed supported contract/lifecycle or unresolved product behavior. Restoring agreed behavior, internal refactoring and documentation corrections need none.
- Without a spec, review against existing behavior, contracts and engineering rules.

## Understand and decide

- Read each changed boundary's implementation, callers and tests; apply [contract evolution](principles.md#evolve-existing-responsibilities) before choosing the implementation.
- For domain work, trace existing concepts, valid states, transitions and use cases to their consumers. Apply the evolution rules in principles.
- Proceed with approved additions when precedent fits and existing responsibilities and guarantees are preserved.
- Ask before introducing or moving a responsibility, changing previously agreed guarantees, choosing an unresolved product tradeoff or taking a rule exception. Use [the design template](templates/design-map.md) for that decision.
- Investigate only named behavior, ownership or feasibility questions. Stop when answered; reopen decisions only for contradictory evidence.
- Inspect existing UX in the application. When inspection cannot answer a material question, agree on an experiment's question, evidence and budget before running it.
- An approved experiment defaults to Haiku 5.5, targets 10 minutes and stops at 15 minutes of active work. Continuing or changing the model requires approval; report an unavailable model.
- Check the experiment once; coverage may be skipped. Keep it isolated and uncommitted; stop owned services and remove its resolved worktree unless retained for an agreed comparison.

## Spec

- Use [the change template](templates/change-spec.md), current living documentation and confirmed evidence; its model evolution is required reviewed spec content, alongside observable behavior and guarantees.
- Use tables or diagrams for contracts and transitions; prose adds only missing information.
- Check acceptance, concrete model evolution and documentation ownership before publication; expose unresolved design decisions in the spec PR. Implementation begins after its authorized merge; later model changes follow the amendment rule below.

## Implement and check

- Adapt the inspected pattern only if it still fits. Resolve [design signals](principles.md#guardrails) before editing; remove unnecessary machinery in the affected design and prove its required invariants still hold.
- For new behavior, batch its tests and confirm they fail for the intended missing behavior before changing production code.
- Finish a coherent code-and-test change before targeted validation. Run an earlier check only for a named compiler or type uncertainty.
- Extend owning scenarios for changed behavior. Cover relevant failures and edges at the boundary responsible for them: core tests substitute ports; adapter tests cross the real external boundary; frontend tests exercise behavior and accessibility.
- Run the smallest existing checks that cover the change, then any additional checks required for publication. Reuse results while their code and dependencies remain unchanged.
- Perform one self-check of changed behavior, ownership and applicable conventions. Fix findings and rerun affected checks; settled areas need no repeat inspection.
- Fill [quality gates](references/quality-gates.md) from actual changed declarations/calls/suites and named evidence; load the selected surface's examples/local contracts. Spec and completion checks cover every criterion and edge case, not just nearby passing tests.
- Independent review is required for new/moved responsibilities, changes to previously supported guarantees, concurrency/resource-safety changes, rule exceptions or local/maintainer requirements. Approved additions preserving existing guarantees otherwise finish with the self-check; use [the review protocol](reviewer.md) when required.
- Demonstrate only for a named journey risk unproven by tests or a maintainer request. Agree on the scenario; obtain confirmation before publishing.
- For an amendment discovered during implementation, obtain scoped maintainer approval and update affected spec criteria before its tests and code. That approval permits implementation without another spec-only merge unless local policy or the maintainer requires one. Include the amended spec in the implementation PR and perform any required review; reopen only affected decisions and checks.
- For spec-backed work, maintain the spec's Doc Sync delta during implementation; apply it to living documentation in the archive phase. For work without a spec, include directly related documentation updates with implementation.
- Finish when required checks pass and findings are resolved. Further verification needs a new question or changed evidence.

## Complete

- Check the integrated journey when separate deliveries introduce an interaction not already proven by their checks. Use the same demonstration rule as above.
- For spec-backed work, after all implementation merges, reconcile every Doc Sync delta with delivered behavior, update living documentation at its owning section, remove obsolete explanation and archive under `specs/changes/archive/YYYY-MM-DD-<slug>/` in the same documentation PR; archival adds no behavior.
- Record the final candidate, check results, approvals and unresolved work. For independent review, retain the reviewer ID and report path.
