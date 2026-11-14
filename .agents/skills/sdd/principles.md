# Design principles

Use these refinements with the [engineering foundations](foundations.md), which own the universal minimums.

## Complexity

- Record necessary invariants before complexity changes.
- Remove machinery without a necessary function.
- Use tests to show that the result keeps those invariants.
- Decrease design complexity before scope additions.
- When simplification changes the scope of approval, get approval. A bug hunt does not give permission for intended-behavior changes.
- Use infrastructure for deployment requirements with approval.
- On a home NAS, give an operating requirement for more processes, coordination and configuration.
- Add compatibility machinery only for deployed versions or saved data that supported consumers must read.
- Record the supported version/data and its guarantee.
- Before repair selection, find the defect in the model, contract or responsibility boundary.
- Examine added guards, retries, flags and duplicate state for the same defect.
- Correct the owner and its dependent callers without more workarounds.

## Ownership

- Use [foundation 2](foundations.md#2-domain-driven-design) for domain and usecase responsibilities.
- Keep capabilities out of domain values.
- Put business decisions, aggregate construction and lifecycle transitions in usecases.
- Put decision composition in orchestrating usecases.
- Use [module conventions](conventions.md#modules-and-results) for factory and result placement.
- The supplying adapter gets and assembles source data. It parses, normalizes and validates the representation into domain values.
- Keep business selection, fallback and lifecycle policy in the consuming usecase also when adapter execution costs less.
- Use port domain-valid guarantees without duplicate validation.
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
- Put stored representation validity, relational constraints and atomic writes of supplied decisions in persistence, not business selection.
- Record the guarantee for each mechanism.
- Select indexes from observed filters, joins and ordering.
- Examine query plans and write/storage costs for changed access paths.
- Remove redundant representations and indexes without a necessary guarantee.
- Keep saved data compatible with supported consumers.
- Use tests for changed migration, rollback and concurrent-write behavior.
- More queries or indexes do not correct a decision at the wrong owner or an unsafe read/decision/write boundary.

## Evolve existing responsibilities

- Before API changes, read the full trait, implementations and callers.
- For domain work, examine baseline concepts, valid states, transitions and usecases through their consumers.
- Follow trigger, authoritative state, acquisition, transformations, kept result and publication through every consumer.
- Examine validity, failure and lifetime during all parts of that trace.
- First use a sufficient contract. If it is not sufficient, change the contract with its callers.
- Add a complementary operation only for a different decision or guarantee.
- A different input shape for the same work is not sufficient.
- Give each operation a different purpose with sibling names/contracts at one abstraction level.
- Put orchestration in their caller.
- Replace overlapping variants with cohesive building blocks and caller composition.
- Keep more than one API for the same work only for a necessary compatibility contract.
- Give each kept API a specified consumer with approval.
- Divide responsibilities only when their decisions change independently.
- A new caller alone is not sufficient for a new abstraction.
- Use [foundation 8](foundations.md#8-code-expresses-intent) for shared caller knowledge.
- Keep independently variable resource rules local also when their code looks almost the same.
- Give each abstraction a necessary decision, invariant or external boundary.
- Thin wrappers and more remote calls are not sufficient for a layer.
- For each new method or argument, record the necessary decision or guarantee that its removal loses.
- Use [test boundaries](#tests) when classifying orchestration versus an external adapter.
- Use mutable records as the state owner, not cached projections.
- Keep identity and necessary evidence in projections.
- If a read fails or identities do not agree, give a failure result.

## Abstractions and composition

- Use [foundation 3](foundations.md#3-fractal-design) for cohesion and abstraction levels.
- Use cohesive building blocks and injected collaborators for decomposition.
- Use [contract evolution](#evolve-existing-responsibilities) for responsibility boundaries, not mandatory layers or one-method traits.
- Use narrow, deep abstractions with a small API for cohesive decisions and transformations.
- Examine many parameters with a shallow implementation for design defects.
- Keep orchestration clear and in execution order. Give independently variable algorithms different owners.
- When only one component uses a pure mapping, keep it private.
- Put declarations and parameters in order of importance.
- Use [foundation 7](foundations.md#7-keeping-complexity-in-check) for the placement and timing of new responsibilities.

## Contracts and effects

- Include only consumed inputs in parameters.
- Give reasons for batch size and scope separate from entity identity.
- Accept necessary facts, not a different operation's result envelope.
- Return necessary helper data, not the caller's public result envelope for a different responsibility.
- Grouped inputs represent cohesive facts. Sum types represent different valid states.
- Keep parameters or dependencies with different responsibilities out of groups.
- Inject fixed collaborators and settings one time at construction.
- Accept only call-variable data in invocation APIs.
- Composition parses static configuration into the types its consumers use.
- Group dependencies only for one shared responsibility with source evidence.
- Return values without effects from pure computation.
- Use effects only for I/O, resource, concurrency or capability work, even when callers wrap pure results in effects.
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
- Keep contractual external names in external representations.
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

- Use [foundation 8](foundations.md#8-code-expresses-intent) for intent and decision reasons.
- Add no explanatory code comments or documentation comments.
- Use mandatory tooling directives. Record their reasons in review evidence.

## Libraries and tooling

- Before custom plumbing, examine available library capabilities.
- Record the available capability and the reason for a custom mechanism.
- Use middleware for shared HTTP concerns and available framework integration for [codecs/refinements](conventions.md#codecs-and-refinements).
- Use available quantity libraries when their models include the necessary units.
- Keep business policy in usecases. A library mechanism does not select application behavior.
- Add production dependencies only for a demonstrated capability.
- Keep versions pinned and included in dependency-update automation.
- Encode important dependency directions in architecture checks.
- Add a documented gate with its code or dependency.

## Tests

- Give each backend usecase or port trait one owning suite.
- Get maintainer approval for more suite owners.
- Use [foundation 2](foundations.md#2-domain-driven-design) for domain-type proof boundaries.
- Use usecase scenarios for business behavior with valid inputs.
- Include only the owning trait's observable contract in its suite.
- Substitute all business collaborators and other usecases.
- Give scenario names from acceptance criteria. The cases must tell a reader the owner's behavior without implementation knowledge.
- For a different valid usecase or guarantee, keep the regression in its own case.
- Put other regression proof in the baseline owning scenario.
- Use [suite conventions](conventions.md#suites-and-assertions) for builders, substitutes and observations.
- Use the enforcing boundary for concurrency, cancellation and resource tests.
- Use concurrent real-adapter scenarios for state-dependent port guarantees.
- Cross the external system or representation in adapter tests.
- Send incoming HTTP requests through HTTP with the application service substituted.
- For outgoing HTTP tests, send requests to the remote service with isolated state.
- A seam crosses HTTP, a database engine, files, a process or configuration representation.
- An operation that coordinates application ports is a usecase, not an external seam.
- Use frontend suites for behavior and accessibility under [frontend conventions](conventions.md#solidjs--typescript).
- Coverage shows execution, not behavior.
- Add missing guarantees to scenarios and assertions, not execution-only cases.
- Before more fixture machinery, examine tests that are not easy with the maintainer.
- Examine responsibilities, API depth and dependency isolation, not assertion layout alone.

## Presentation

- Keep accessible state and focus continuity with the UI control.
- Include expansion, collapse, success, failure and pending transitions in that proof.
- Use layout for content constraints and narrow widths, not only one viewport.
- Use structural layout boxes and fluid bounds rather than decorative layers.

## Guardrails

- Use [foundation 6](foundations.md#6-strict-build-guardrails) for mandatory build standards.
- Keep configured formatter, architecture and other gates enabled during repairs.
- Examine more than five API inputs or injected business dependencies, without generic capabilities in the count.
- Use [foundations 3 and 7](foundations.md) for size signals and mandatory design analysis.
- Also examine setup tuples, response lambdas, large operands, nested sequencing and mixed comprehension bindings.
- A formatter pass does not answer these design questions.
- For each signal, record the suspected responsibility, contract or abstraction-level defect and its caller effect.
- Compare a structural repair with the design before the repair.
- Correct a demonstrated defect at its necessary scope.
- If necessary for that defect, refactor its callers.
- Keep a signal only with source evidence of cohesion.
- A purpose statement or compliant line count is not that evidence.
- Wrappers, parameter bags, forced lines and trimmed tests do not correct defects.
- A signal alone does not make approval or independent review mandatory.
- Use [execution](executor.md#understand-and-decide) for undecided choices and [approval scope](executor.md#approvals-and-delivery) for gate changes and exceptions.
- Keep mandatory conventions after design analysis.
- Examine the full design, not only each compliant piece.
- At each coverage gate, find the missing observable guarantee. The 100% threshold does not change this analysis.
- For composition or unreachable code, give evidence for a repository-permitted exclusion.
- Get execution's scoped approval for that exclusion.
