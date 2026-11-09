# Design principles

Obey these rules and the [eight engineering rules](foundations.md). Profiles and local contracts cannot decrease these obligations.

## Complexity

- Record necessary invariants before complexity changes.
- Remove machinery without a necessary function.
- Use tests to show that the result keeps those invariants.
- When simplification changes the scope of approval, get approval. A bug hunt does not give permission for intended-behavior changes.
- Use infrastructure for deployment requirements with approval.
- On a home NAS, give an operating requirement for more processes, coordination and configuration.
- Decrease design complexity before scope additions. Get maintainer agreement before a scope decrease.
- Add compatibility machinery only for deployed versions or saved data that supported consumers must read.
- Record the supported version/data and its guarantee.
- Before repair selection, find the defect in the model, contract or responsibility boundary.
- Examine added guards, retries, flags and duplicate state for the same defect.
- Correct the owner and its dependent callers without more workarounds.

## Ownership

- Domain contains types and their validity constraints.
- Put business behavior in usecases, not more domain services.
- Keep capabilities out of domain values.
- Use types that exclude invalid states.
- Put business decisions, aggregate construction and lifecycle transitions in usecases.
- Put decision composition in orchestrating usecases.
- Put factories and operation results in companions.
- Give ports domain names for their caller's necessary work.
- Use adapters for external boundaries.
- Bind concrete interpreters only in composition.
- The supplying adapter gets and assembles source data. It parses, normalizes and validates the representation into domain values.
- Keep business selection, fallback and lifecycle policy in the consuming usecase also when adapter execution costs less.
- Use port domain-valid guarantees without duplicate validation.
- Put representation validity, relational constraints and atomic writes of supplied decisions in persistence.
- Coordinate application decisions across the full read/decision/write sequence.
- A transaction shows atomicity, not the safety of a previous business decision.
- For an event after its occurrence, use the caller's timestamp, not the server clock.
- At the boundary, convert local entry to a validated absolute instant.
- Keep that instant.
- Use project domain vocabulary in code, contracts, UI, docs, reviews and responses.
- Before a new concept name, examine the project glossary. Use those terms.
- Add a new term only for a different concept with maintainer agreement.

## Where rules belong

- Include sorting, precedence, duplicate selection, kept spelling, grouping and display projections in behavior review.
- Examine the rule after a different screen, endpoint or consuming usecase.
- Purity alone does not show domain ownership.
- Use domain ordering only for a consumer-independent rule, such as chronology or book structure.
- Put business selection and reconciliation in the deciding operation.
- Put source-format normalization and source contract order in the adapter.
- Put visual order, labels and formatting in presentation.
- Give global ordering instances a domain rule for selection from duplicates.

## Persistence design

- Use the lifecycle and query requirements for stored identity, relationships, cardinality and constraints.
- Use database constraints for representation integrity and usecases for business decisions.
- Record the guarantee for each mechanism.
- Select indexes from observed filters, joins and ordering.
- Examine query plans and write/storage costs for changed access paths.
- Remove redundant representations and indexes without a necessary guarantee.
- Keep saved data compatible with supported consumers.
- Use tests for changed migration, rollback and concurrent-write behavior.
- More queries or indexes do not correct a decision at the wrong owner or an unsafe read/decision/write boundary.

## Evolve existing responsibilities

- Before API changes, read the full trait, implementations and callers.
- Examine acquisition, transformations, kept results, validity, failure and lifetime.
- First use a sufficient contract. If it is not sufficient, change the contract with its callers.
- Add a complementary operation only for a different decision or guarantee.
- A different input shape for the same work is not sufficient.
- Give each operation a different purpose with sibling names/contracts at one abstraction level.
- Put orchestration in their caller.
- Replace overlapping variants with cohesive building blocks and caller composition.
- Keep more than one API for the same work only for a necessary compatibility contract.
- Divide responsibilities only when their decisions change independently.
- A new caller alone is not sufficient for a new abstraction.
- When different caller results are a bug, use the same knowledge owner.
- Keep independently variable resource rules local also when their code looks almost the same.
- Give each abstraction a necessary decision, invariant or external boundary.
- Thin wrappers and more remote calls are not sufficient for a layer.
- For each new method or argument, record the necessary decision or guarantee that its removal loses.
- A module that coordinates application ports is a usecase, not an external adapter.
- Use mutable records as the state owner, not cached projections.
- Keep identity and necessary evidence in projections.
- If a read fails or identities do not agree, give a failure result.

## Abstractions and composition

- Give each unit one responsibility. Keep method statements at one abstraction level.
- Use cohesive building blocks and injected collaborators for decomposition.
- Divide by independently variable decisions, not mandatory layers or one-method traits.
- Use narrow, deep abstractions with a small API for cohesive decisions and transformations.
- Examine many parameters with a shallow implementation for design defects.
- Keep orchestration clear and in execution order. Give independently variable algorithms different owners.
- When only one component uses a pure mapping, keep it private.
- Put declarations and parameters in order of importance.
- Put a newly necessary responsibility at its owner in the change that makes it necessary.

## Contracts and effects

- Include only consumed inputs in parameters.
- Give reasons for batch size and scope separate from entity identity.
- Accept necessary facts, not a different operation's result envelope.
- Grouped inputs represent cohesive facts. Sum types represent different valid states.
- Keep parameters or dependencies with different responsibilities out of groups.
- Inject fixed collaborators and settings one time at construction.
- Accept only call-variable data in invocation APIs.
- Composition parses static configuration into the types its consumers use.
- Group dependencies only for one shared responsibility with source evidence.
- Return values without effects from pure computation.
- Use effects only for I/O, resource, concurrency or capability work.
- Keep pure APIs without effects also when callers wrap their results in effects.
- Use application contracts in adapters, not peer adapters or infrastructure bypasses.
- Keep typed identity, necessary cardinality and associations in collections.
- Keep duplicate handling and domain-rule precedence through projections.
- Use keyed associations, not parallel positional lists.
- Give configurable business data an authority and a read/update path with an owner.
- Constants or relocated resource files do not show configurability.
- Use the caller-owned repository contract for repository-backed business data.
- Give that lifecycle and static settings different owners.
- Validate state-dependent writes against the state at commit time.
- Keep canonical-encoding semantics for external consumers and stored ordering across kept data.
- Give each effect category one owner at the correct abstraction level.
- Put business logging in orchestrating usecases and external-failure translation in adapters.
- Put startup and top-level diagnostics in composition.
- Put request diagnostics in transport middleware.
- Examine duplicate logs across operations and adapters for orchestration with more than one owner.

## Failures

- Show errors and uncertainty in results.
- Keep the primary failure when compensation also fails.
- When success, expected short-circuit and failure all occur, use different states.
- Reject unsafe casts, partial operations and success-shaped fallbacks.

## Expression

- Give code intent through names and structure. Put decision reasons in owning docs.
- Add no explanatory code comments or documentation comments.
- Use mandatory tooling directives. Record their reasons in review evidence.

## Libraries and tooling

- Before custom plumbing, examine available library capabilities.
- Record the available capability and the reason for a custom mechanism.
- Use middleware for shared HTTP concerns and declarative codecs/refinements for boundary conversion.
- Use available quantity libraries when their models include the necessary units.
- Keep business policy in usecases. A library mechanism does not select application behavior.
- Add production dependencies only for a demonstrated capability.
- Keep versions pinned and included in dependency-update automation.
- Encode important dependency directions in architecture checks.
- Add a documented gate with its code or dependency.
- Use compiler gates for unsafe constructs, unused code and accidental widening.

## Tests

- Give each backend usecase or port trait one owning suite.
- Get maintainer approval for more suite owners.
- Domain types have no suites. Type constraints define valid values.
- Use usecase scenarios for business behavior with valid inputs.
- Include only the owning trait's observable contract in its suite.
- Substitute all business collaborators and other usecases.
- Give scenario names from acceptance criteria. The cases must tell a reader the owner's behavior without implementation knowledge.
- For a different valid usecase or guarantee, keep the regression in its own case.
- Put other regression proof in the baseline owning scenario.
- Give builders fixed dependency responses as values.
- Record calls and effects in Refs, not responses.
- For a recorded scenario requirement, use an input-dependent substitute.
- Keep dependency algorithms out of substitutes.
- Use the enforcing boundary for concurrency, cancellation and resource tests.
- Cross the external system or representation in adapter tests.
- Send incoming HTTP requests through HTTP with the application service substituted.
- For outgoing HTTP tests, send requests to the remote service with isolated state.
- Coverage shows execution, not behavior.
- Add missing guarantees to scenarios and assertions, not execution-only cases.
- Before more fixture machinery, examine tests that are not easy with the maintainer.
- Examine responsibilities, API depth and dependency isolation, not assertion layout alone.

## Presentation

- Keep accessible state and focus continuity with the UI control.
- Use layout for content constraints and narrow widths, not only one viewport.

## Guardrails

- Keep configured compiler, formatter, lint, architecture and coverage gates enabled.
- Get maintainer approval for the scope of gate changes and rule exceptions.
- Examine more than five API inputs or injected business dependencies, without generic capabilities in the count.
- Examine methods longer than approximately 7-10 lines and units larger than a screenful.
- Examine suites approximately three times their production size.
- Also examine setup tuples, response lambdas, large operands, nested sequencing and mixed comprehension bindings.
- A formatter pass does not answer these design questions.
- For each signal, record the suspected responsibility, contract or abstraction-level defect and its caller effect.
- Compare a structural repair with the design before the repair.
- Get a maintainer decision for undecided choices. Record the decision.
- Correct a demonstrated defect at its necessary scope.
- If necessary for that defect, refactor its callers.
- Get execution's mandatory approvals.
- Keep a signal only with source evidence of cohesion.
- A purpose statement or compliant line count is not that evidence.
- Use counts and formatting for design diagnosis, not goals.
- Wrappers, parameter bags, forced lines and trimmed tests do not correct defects.
- A signal alone does not make approval or independent review mandatory.
- Use execution's approval rules for responsibility changes, scope changes and hard-rule exceptions.
- Keep mandatory conventions after design analysis. Only the maintainer can give a scoped rule exception.
- Examine the full design, not only each compliant piece.
- At each coverage gate, find the missing observable guarantee. The 100% threshold does not change this analysis.
- For composition or unreachable code, give evidence for a repository-permitted exclusion.
- Get mandatory approval for that exclusion.
