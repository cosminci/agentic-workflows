# Quality gates

Use these gates for implementation, self-review, independent review and repairs, with or without a spec.

Read [principles](../principles.md), [conventions](../conventions.md), the selected profile and project contracts first.

Read [examples](examples.md) and [local contracts](local-contracts.md) for each changed boundary.

For docs, read spec/completion and [context policy](../context.md). Use [writing](../writing/SKILL.md) for all prose.

## Evidence before closure

- Record all changed/untracked files, declarations, private helpers, constructors, instances, calls and suites.
- Use maintainer examples as evidence of a failure class, not the review's scope limit.
- Fill the [review record](../templates/review.md) with `pass`, `fail`, `blocked` or `not applicable` for each gate.
- Give examined source and its guarantee or concern.
- Give a scope reason for each inapplicable gate.
- Keep unread sources, missing checks and undecided signals open.
- Give no pass or inapplicability label to those open items.
- Examine changed modules' full traits, callers and suites.
- Include affected defects outside the line diff.
- Put tests, formatter results and precedent in different evidence categories from architecture and code quality.

## Architecture

Examine all [eight engineering rules](../foundations.md) against source, contracts and checks. Missing automation does not remove a universal obligation.

Include business interfaces, external/internal model separation, 100% coverage, zero warnings, strict analysis/compiler settings and automated dependency updates.

| Check | Evidence | Blocking example and effect |
| --- | --- | --- |
| Continuity | Follow trigger → authoritative state → acquisition → transformations → kept result → publication → all consumers. Compare reuse and evolution before separation. | A second reader rebuilds a prepared snapshot for a new endpoint. Selection and normalization now have two owners. |
| Ownership | Give each operation its independently variable responsibility. Read implementation, callers and owning suites in full. | A generic client contains different resources' pagination and selection. Thin resource wrappers keep those decisions centralized. |
| API cohesion | Record the [contract-evolution decision](../principles.md#evolve-existing-responsibilities), operation purposes and migrated callers. Examine sibling names and abstraction levels across the trait. | A trait mixes building blocks with orchestration. A near-duplicate API keeps the inadequate contract. |
| Input cohesion | Follow each input through supplier, consumed facts, decisions and result consumer. Examine more than five fields/inputs for cohesion or different valid states. | An aggregate bag conceals unrelated inputs or includes a different operation's result envelope without a consumer requirement. |
| Dependency lifetime | Record all constructor/method parameters. Put fixed collaborators/settings at construction and variable facts at invocation. | A caller passes static settings for each item or converts a collaborator's uncertainty without its evidence. |
| Associations | Examine typed keys, cardinality, absent entries, duplicates and precedence through projections. Include order-sensitive results in tests. | Parallel chapter/photo lists lose identity after an absent entry. A sort changes first-winner spelling without an owning rule. |
| Effects | Give each effectful API its I/O, resource, concurrency or capability work. Return plain data from pure operations. | A pure classifier returns `F[Result]` only for a computed answer. Callers must do unnecessary sequencing. |
| Effect ownership | Give logging, transport diagnostics and other effect categories their owner and abstraction level. | Business logs in adapters and leaf operations duplicate outcomes without an orchestration owner. |
| Valid states | Record invalid combinations and their excluding types. Give cross-value guarantees an enforcing owner. | Status, optional ID and evidence fields let confirmation occur without identity. A consumer duplicates a domain-valid port's kind check. |
| Policy | Record configurable data's authority, reader, update path and failure semantics. | A normalization catalog becomes a constant or static setting without its repository-backed lifecycle. |
| Complexity | Examine removal of each added module, method and argument through available contracts or loaded data. Record guarantees, invariant proof, removed machinery, batch reasons and deployment costs. | Machinery stays on a purpose statement alone. Home-NAS coordination adds operating cost without a necessary guarantee. |
| Library leverage | Examine available framework/library mechanisms for each cross-cutting or representation concern. Keep application policy at its owner. | Twenty routes duplicate middleware or manual JSON parsing duplicates HTTP decoding, codecs and refinement integration. |
| Persistence | Follow identity, relationships, constraints and query patterns. Examine plans, index costs, saved-data migration and affected concurrency. | A patch adds queries or indexes with duplicate state or an unsafe decision/write boundary unchanged. |
| Structural repair | Record the model/ownership defect, affected callers, removed workaround and kept invariants. | An additional flag, guard or retry conceals the same invalid state. |
| Test boundary | Record production owner, public operation, scenario, substitutes and external boundary. | A service with injected ports claims a seam suite. A real server beside it does not show that the service crosses HTTP. |

Examine shared and independent changes across callers.

Share knowledge when divergent caller behavior is a bug. Keep independently variable resource rules local also when their shapes are similar.

Examine the full result after local checks. Keep coherent owners for acquisition, kept evidence and publication, not unrelated workflows with flags.

A seam crosses HTTP, a database engine, files, a process or configuration representation.

Driving HTTP tests send real requests with the usecase substituted. Driven HTTP tests use the real remote boundary in isolated state.

An operation that coordinates application ports is a usecase with a component suite.

Keep pure single-owner mapping private. Put stateless business policy in its owning usecase.

## Readability and suites

- Get each changed trait's full suite from its observable contract.
- Change, merge, retarget or remove established scenarios before additional cases.
- Record each case's acceptance behavior.
- Keep a regression in its own case only for a different valid usecase or guarantee.
- Use named table fields, variant names and full typed outcomes for input variants.
- Examine each larger method, block and chain for sibling abstraction levels.
- Give callers necessary facts, not low-level detail from anemic results.
- Analyze size, width, sequencing and assertion signals through [guardrails](../principles.md#guardrails).
- Give cohesion evidence for a kept signal.
- Keep hard conventions unless the maintainer gives an exception.
- Diagnose the design signal without threshold-only failure.
- Examine assignments, chains, calls and lambdas after formatting.
- When one line is readable, keep expressions together with their tuple/type arguments.
- For each wrapped call, examine the bulky operand's conceptual work.
- Give one complex value a name when that clarifies the short call.
- Examine the full value after repair.
- A wrapper, parameter bag or forced line does not correct a design defect.
- Keep comprehension generators together without gaps.
- Put blank lines between Arrange, Act and Assert, not inside sequencing.
- Put scenario data in semantic order.
- Use fact names, such as `orderedChapters`, not helper names, such as `processed`.
- Use [assertion conventions](../conventions.md#suites-and-assertions) for each operand.
- Examine result variants, flags, identity, order and associations.
- Use `should` names in domain vocabulary.
- Interpolate actual variants to prevent stale descriptions after a rename.
- Read the reference suite before setup changes.
- Keep scenario roles clear through local data, Refs and meaningful builders.

### Test difficulty is design evidence

- Record baseline/current size for each affected suite and production owner.
- Examine suites around three times production size without automatic test removal.
- Record each scenario and fixture dependency against the trait's responsibility.
- Find duplicate setup, dependency behavior and independently variable decisions.
- Examine short implementations with multiple responsibilities behind shallow operations.
- Compare API evolution and orchestration changes, not smaller fixtures alone.
- Give fixed stub responses as builder values.
- Record input/effects in Refs.
- Give an explicit scenario reason for input-dependent responses.
- Substitute all business collaborators.
- A suite with a different module's policy is not isolated proof of its owner.
- Keep the design concern open despite a difficult or coverage-only test.
- Correct the cause or get a redesign decision before more machinery.

## Backend inventory

- Record pure/effectful APIs and typeclass instances against [ownership](../principles.md#ownership).
- Give spelling, precedence or winner-selection order a domain invariant, such as chronology.
- Put screen-specific selection in its usecase.
- Use trait → companion `make` → `private final class Live<CompanionObjectName>` → companion-owned results for business modules.
- Keep module machinery out of domain types.
- Keep public/package helpers as factories or mechanical utilities with callers and a shared invariant.
- Examine unwrapped return contracts against the selected [local contract](local-contracts.md).
- Use different result states for success, expected short-circuit and failure.
- Return narrow helper data, not the caller's public envelope.
- Use `Failure` for companion failures and top-level types for shared/config values.
- Make business collaborators substitutable at their owner.
- Bind runtimes/logging in composition.
- Parse fixed settings once into consumer types.
- Record each changed comprehension with nested levels and mixed `<-`/`=`.
- Give its design concern and decision against [sequencing](../conventions.md#sequencing).
- Use `.map` for Typelevel single-generator pure results.
- A helper that only conceals nesting is not a repair.
- Use the stack's resource-safe primitive for acquisition/use/release.
- Keep resource ownership directly in integration suites.
- Validate and commit state-dependent writes against current state.
- Use concurrent real-adapter scenarios for the port guarantee.
- An earlier read and atomic write do not show decision safety.
- Use action or predicate names for methods/helpers/builders.
- Use `maybe` for optional names and `Result` for direct-style Either values.
- Examine constructors, private calls, builders and captured calls against [named arguments](../conventions.md#syntax).
- Include application calls, not established library syntax.
- Reject ambiguous unnamed literals, collections or options.
- Give public APIs return types.
- Keep private members without return types, except Unit.
- Keep default arguments out of runtime code.
- Use imports.
- Destructure only when all fields are necessary.
- Use named patterns for tuple callbacks, not `._1`.
- Examine calls/lambdas against [Scala syntax](../conventions.md#syntax).
- Include named roles, optional delimiters, Cats value syntax and block boundaries.
- Reject braced blocks inside parentheses.
- Examine the readability/abstraction cause before repair selection.
- Share codecs only for pure bidirectional mappings, at least two consumers and one shared representation invariant.
- Use direct declarative codecs.
- Use boundary-local DTOs only when direct codecs cannot express the format.
- Record changed refinement declarations, explicit/implicit proof sites and `autoRefine`.
- Keep production guarantees and only consumer-necessary non-core/test proofs.
- Record `file:symbol` reasons.
- Validate domain inputs and substituted port responses before Act.
- Use checked values for full expected outcomes.
- Compare underlying text/numeric catalogs against safely widened actual values.
- Count construction/conversion sites, not aliases or runtime records, for proof reduction.
- Keep rejection proof, constraints, full assertions and strict gates.
- Casts, assumptions and fixture frameworks are not reductions.
- Use suite order: meaningful reusable data → scenarios → Refs → builders.
- Keep at most one Refs for calls/effects.
- Remove it when there are no observations.
- Give responses, failures and clocks as builder inputs, not Refs contents.
- Give each observation role a name.
- Record setup returns, allocations and table rows.
- Make each value's role clear at the call site.
- Reject tuples/rows with mixed component construction, controls, inputs and expectations.
- Examine named locals, builder inputs and row cohesion against [suite conventions](../conventions.md#suites-and-assertions).
- Create substitutes for real collaborators in builders.
- Share only nontrivial multi-suite setup with common knowledge, not constructor aliases or fixture carriers.
- Use established TestImplicits/Mocks for direct-style substitutes.
- Give fixed responses and observed effects different roles.
- Examine [trait ownership](../principles.md#tests) for each affected suite and case.
- Cross the real adapter seam in port suites.
- Record explicit approval for other suite owners.
- Domain types, result ADTs and private traversals have no suite.
- Keep mandatory architecture proof in one plainly named ArchitectureTest with one test.
- Keep real-boundary and coverage obligations mandatory with suite ownership.
- Give suppressions site proof, smallest scope and the full rule ID, such as `minBy` after a nonempty guard.
- Keep unsafe-operation rules and gates mandatory despite a suppression reason.
- Use the selected profile and explicit local contract for comment/directive exceptions.
- Keep configured compiler, formatter, lint, architecture, dependency-update and coverage gates.
- Pin new dependencies for a demonstrated capability.
- Keep zero warnings and 100% coverage as universal foundations.
- Use local contracts for tooling and additional contract-generation obligations, not weaker foundations.

## Frontend inventory

- Export pure validation/derivation/formatting as plain functions.
- Use direct branch-table tests for those functions.
- Use component tests for rendering/wiring without duplicate branch tables.
- Give disabled controls an explanation from the same predicate through `aria-describedby`.
- Keep UI invariants consistent with backend contracts.
- Reject invalid input without silent repair.
- Use accessible roles/names and real events in tests.
- Test IDs, CSS classes, element structure and impossible staged states do not show user behavior.
- Use accessors/createMemo for derived state.
- Use Show for conditional rendering, For for keyed identity and Index for positional/primitive rows.
- Use tests for accessible state and persistent focus through expansion, collapse, success, failure and pending transitions.
- Use structural layout boxes, fluid bounds and explicit narrow-width behavior, not decorative layers.
- Read established styles/dialog/feedback/toolbar precedents.
- Keep content primary, editing chrome quiet, actions grouped and wording in sentence case.
- Keep authoritative saved names and proper names/catalog content.
- Keep thin-stroke toolbar icons, hover text and accessible names.
- Keep navigation/editing/viewing separate.
- Keep interaction shortcuts compatible with text entry and native controls.
- Keep compiler/reactivity-mandatory bindings and the selected import contract.
- Use the repository's coverage directive only for a guarded unreachable branch with site-level proof.
- Use user-path tests for reachable failures.

## Spec and completion

- Read the specified template and full proposal.
- Record classification and matching branch naming for a new branch.
- Use living knowledge as authority, not archived proposals unless explicitly designated.
- Keep what/why and acceptance nonempty.
- Use [strategic admission](../context.md#admission-test) for each Doc Sync decision. Include `None` in this rule.
- Find affected living-doc owners from final source, not only changed files.
- Record evidence for unchanged statements or necessary edits.
- Include each changed owner once and different facts in each section.
- Reject misplaced content without only shorter wording.
- Compare terminology with authoritative project names across code, UI, docs, reviews and responses.
- Add a term only for a different concept with maintainer agreement.
- Use [STE100 and high-signal writing](../writing/SKILL.md).
- Keep mandatory decisions, guarantees, evidence and uncertainty.
- Give observed findings or a checkable reason that no experiment was necessary.
- Reject unsupported claims of previous understanding.
- Keep experiment notes and alternatives consistent with observed findings.
- Put observations, hypotheses and conclusions in different categories.
- Give evidence for rejected hypotheses.
- Include contrary evidence, confidence and unknowns.
- Give facts checkable evidence and intent maintainer authority.
- Put recommendations, open choices and decisions with approval in different categories.
- Get maintainer agreement on acceptance and design before implementation.
- Keep spec PR approval and merge permission as different decisions.
- Accept inconclusive or no-change reports.
- Select repairs only with cause evidence and established expected behavior.
- Examine [model evolution](../templates/change-spec.md#model-evolution) against established owners.
- Account for each affected concept, usecase and port with contracts and migrated consumers.
- Mark each as evolved, reused, retired or unchanged.
- Reject behavior-only specs and design hidden in chat/work state.
- Keep intent and acceptance at behavior level.
- Put concrete types, operations and signatures in linked committed design, not the proposal.
- Keep file layouts, library inventories, diff walkthroughs and exhaustive test cases in implementation work.
- Give changed domain/persistence/boundary guarantees their enforcing owners.
- Include trigger-to-committed-state flow, failure order and UI delivery.
- Give database constraints and usecase decisions different roles.
- Examine their mechanisms against source.
- Start with structure and diagrams/tables for important flow/contract questions.
- Add prose only for missing information.
- Give each criterion one observable behavior with applicable failure, boundary and accessibility cases.
- Keep prose blocks to one topic and at most two sentences.
- Examine signal and clarity independently of line counts.
- Do the owning Markdown wrapping check on each changed Markdown file.
- Reject source-wrapping failures.
- Compare scope and concision with the closest precedent with approval.
- Remove unnecessary prose from smaller changes with equally large explanations.
- Use established guarantees for invariants.
- Give each tradeoff its cost and the person or system that pays it.
- Include scope entries only for likely questions, not a roadmap inventory.
- Remove empty sections.
- Record a named scenario without errors for each criterion and edge case at its boundary.
- Give deferred slice criteria explicit owners.
- Keep contractual external names.
- Use caller requirements for internal design, not incorrect implementation nouns in specs.
- Make each slice compile and pass gates without unpublished subsequent code.
- Include caller migrations with their owner.
- Give each kept API a specified consumer with approval.
- Get execution's amendment approval before tests/code for changed scope/guarantees.
- Keep Doc Sync accurate and deferred during spec implementation.
- After all merges, compare delivery with each delta and the full proposal.
- Put qualifying knowledge at its owner.
- Remove obsolete text before archive without behavior additions.
- Read all affected living docs against [context policy](../context.md). Include unchanged owners behind `None`.
- Candidate-file coverage does not show that docs are correct.
- Give new readers system knowledge, not feature history or general aspirations.
- Record each edit's reader decision, missing-knowledge effect and authority.
- Add signal that code search and other docs cannot give.
- Compare each contract with its producer and consumer.
- Include protocol, method/address, handoff, credentials, headers, data, failures and streams.
- Use validated OpenAPI for provided HTTP APIs.
- Give provided-boundary docs a link without a second endpoint or payload inventory.
- If OpenAPI is missing, write it from source.
- Use the established generator for generated contracts.
- Accept consumed-protocol tables.
- Reject class/port-link contracts, internal state as service APIs and invented transports.
- Keep supported topology, state ownership, safe recovery and monitoring gaps in operations.
- Include alert severity, meaning, runbook and metric interpretation.
- Examine prose, diagrams and tables together.
- Reject duplicate flow narration, copied representations, implementation links and feature/test catalogs.
- Compare all affected docs with the diagram-first precedent.
- Use the present rules when the precedent also has defects.
- Keep mandatory automated checks, independent review and agreed journey-risk demonstrations consistent with the final candidate.
- Coverage shows execution, not completeness, architecture or conventions.

## Review closure

Keep architecture and conventions mandatory without a runtime bug.

Examine design, expression, failures, tests and real seams before closure.

Keep design findings in scope, not only severe runtime findings.

Use evidence tables without predetermined design verdicts.

For a reported miss, open the failed gate again across the affected design and diff.

Independently replay the missed design and a valid control.

Record the rule, verdict and caller result with [replay examples](examples.md#review-replays).

Added prose or token detection alone does not show enforcement.

Examine repairs and subsequent edits against the specified final candidate.

Get maintainer approval for scoped rule exceptions.
