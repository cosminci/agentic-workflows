# Execution

## Start

- Read project entrypoints and applicable sources.
- Use living knowledge as authority, not archived proposals unless explicitly designated.
- On resumption, use recorded decisions and results. Read changed sources and sources necessary for a new question.
- Do the work directly. Use a subagent for mandatory independent review.
- For other delegation, get maintainer approval for a specified correctness blocker before launch.
- Convenience, speed, parallelism and task size are not blockers.
- Keep mandatory independent review when the maintainer requests direct implementation.
- Record scope, baseline, workflow revision, decisions, test mapping and check results in ignored `.agent-work/<slug>/state.md`.
- For a new branch, record classification and matching branch naming.
- For an investigation, find the cause before repair selection.
- Accept no-change and inconclusive reports. These reports do not give repair authority.
- Put observations, hypotheses and conclusions in different categories.
- Record tested hypotheses, evidence for and against them, confidence and unknowns.
- Use a spec for new user-visible capability, changed supported contracts/lifecycles or product behavior without a decision.
- Repairs of behavior with approval, internal refactoring and documentation corrections do not make a spec necessary.
- Without a spec, examine the candidate against baseline behavior, contracts and engineering rules.

## Understand and decide

- For spec discovery, preparation or amendment, use [discovery](discovery.md) instead of the non-spec decision rule below.
- The maintainer has authority over intent, requirements, scope, acceptance and important design decisions.
- Examine facts independently before a recommendation.
- Put requirements with approval, proposals and decisions with approval in different categories.
- Use [contract evolution](principles.md#evolve-existing-responsibilities) before implementation selection.
- Outside spec discovery and preparation, get a maintainer decision only for intent or important choices that evidence cannot answer.
- Stop only the work that depends on the missing decision.
- Keep supplied decisions unless contrary evidence makes a new decision necessary.
- When precedent agrees, continue with additions in the scope of approval that keep responsibilities and guarantees.
- Before responsibility additions/moves, guarantee changes, product tradeoffs without decisions or rule exceptions, get approval.
- Use [the design template](templates/design-map.md) for that decision.
- Examine the specified behavior, ownership or feasibility question. Stop when the evidence answers it.
- Record observed findings or a checkable reason that no experiment was necessary.
- Open a decision again only for contrary evidence.
- For diagnosis, examine the baseline model.
- Select a repair only with source evidence for the cause and known expected behavior.
- For intent or redesign without a decision, get a maintainer decision.
- Use brainstorming, grilling, domain modeling or a prototype/spike only for a specified uncertainty.
- Get agreement on the session before its start.
- If the maintainer declines, get missing decisions or continue when intent is clear.
- Usual investigation and execution choices are in scope.
- Examine UX in the application. If a necessary answer is missing, get agreement on an experiment's question, evidence and budget.
- Use Haiku 5.5 as the experiment default after approval. Keep the 10-minute target and 15-minute active-work limit.
- Get approval before more time or a different model. Tell the maintainer when the selected model is not available.
- Examine the isolated, uncommitted experiment one time. Coverage is optional for the experiment.
- Stop the experiment's services.
- Remove the experiment's worktree unless the maintainer keeps it for a comparison with approval.

## Spec

- Use [discovery](discovery.md) for the pre-draft decision gate and the [change template](templates/change-spec.md) for the artifact.
- Spec approval includes behavior and the linked design decision.
- Start implementation only after the permitted spec merge.
- Use the amendment rule for subsequent model changes.

## Approvals and delivery

- Approval gives permission only for the specified scope. Spec approval, design approval and merge permission are different decisions.
- Keep the implementation in one PR.
- For deliveries with independent value or a maintainer request, use separate PRs.
- Make each implementation PR compile and pass gates without subsequent PRs. Include caller migrations in that PR.
- Get a maintainer decision for delivery-order conflicts.
- For separate deliveries, keep one feature branch. After each permitted merge, fetch main.
- Update that branch without duplicate squash-merged work.
- Keep other agents' edits. If a different agent controls the checkout, use a separate worktree.
- Get maintainer approval for production-state changes, destructive actions and rule exceptions. Record the scope of that approval.
- Before publication, complete the checks and review below. Get maintainer permission before a merge.

## Implement and check

- Examine a pattern's design before use. Use it when applicable to the task.
- Complete [design-signal analysis](principles.md#guardrails) before edits.
- For new behavior, add its tests as one group before production changes.
- Make sure that the tests reject the intended missing behavior.
- Complete a related code-and-test change before targeted validation.
- Use a previous check only for a specified compiler or type uncertainty.
- Use [test ownership and proof boundaries](principles.md#tests) for changed behavior.
- Start with the smallest available checks for the change. Then do the other mandatory publication checks.
- While their code and dependencies have no changes, keep results.
- Complete [review and closure](reviewer.md) after the last edit.
- Use independent review for new/moved responsibilities, changed guarantees, concurrency/resource safety, rule exceptions and local/maintainer requirements.
- Use self-review for other additions in the scope of approval that keep guarantees.
- If mandatory review is unavailable or prohibited, complete permitted work and report the review blocker.
- Give a demonstration only for a specified journey risk without test evidence or a maintainer request.
- Get agreement on the scenario before the demonstration.
- Get confirmation before publication.
- For an amendment, get approval for its scope before tests or code.
- Update affected spec criteria before tests or code.
- After amendment approval, start implementation without a second spec-only merge unless local policy or the maintainer makes that merge mandatory.
- Include the amended spec in the implementation PR.
- For spec-backed work, keep Doc Sync accurate during implementation.
- Put deltas with admission evidence in living docs during archive.
- Without a spec, include directly related doc updates with implementation.
- Use [strategic admission](context.md#admission-test) for Doc Sync and directly related doc edits.

## Complete

- After separate deliveries, examine an integrated journey when its interaction has no previous proof.
- Use the same demonstration rule.
- After all spec implementation merges, compare Doc Sync with delivery and doc owners whose facts can change.
- Include a previous `None` in that comparison.
- Put qualifying knowledge at its owner.
- Remove incorrect text. When strategic knowledge has no changes, use `None`.
- Archive at `specs/changes/archive/YYYY-MM-DD-<slug>/` in the same doc PR. Archival adds no behavior.
- Record the candidate after its last edit, results, approvals and open work.
- For independent review, keep its ID and report path.
