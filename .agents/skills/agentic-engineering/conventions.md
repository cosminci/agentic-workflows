# Contribution conventions

## Shared

- Use action or predicate names for functions and helpers.
- Give values names for domain facts, not their construction helpers.
- Use the `maybe` prefix for optional values.
- Show argument roles through call syntax or named values.
- Use imports, not fully qualified paths.
- Examine readability after formatting.
- Give business conditions and expected outcomes names, not shallow helper wrappers.
- Assert full observable outcomes in tests.
- Keep test setup deterministic and local to its scenario.
- Put blank lines between Arrange, Act and Assert.
- Put test data in semantic scenario order.
- Use table tests for cases with only input differences.
- Give suites names for their production boundaries.
- Start case names with `should`, after the runner prefix when necessary.
- Use each contract's owner for its proof. For Scala fixture sharing, use [suite conventions](#suites-and-assertions).
- Use imperative commit subjects with at most 72 characters and no issue/ticket identifiers.
- When the diff does not give the reason, give it in the commit body. Keep issue references out.
- Keep commit signing and repository identity.
- Keep secrets and credentials out of commits, embedded text and printed output.
- Read setup and gate commands from their executable owners and local guidance.

## Scala

### Syntax

- In multi-argument application calls, use named arguments for literals, booleans, dates, collections and optional values.
- Include `None` and `Nil` in this rule.
- Include constructors, private calls, test builders and captured calls.
- When different domain types show their roles, keep positional arguments.
- Keep the library's call syntax.
- Examine call roles that are not clear as a design signal, not only missing labels.
- Use Cats value syntax: `.some`, `.asRight`, `.asLeft` and related operations.
- Put construction machinery at the end of the expression.
- When Scala 3 and the formatter let you, use significant indentation and optional braces.
- When Scala 3 lets you, remove optional parentheses.
- Keep necessary grouping and library syntax.
- Use braces or colon syntax for block arguments at the call boundary.
- Keep braced blocks out of parentheses.
- Use braces or colon syntax for multiline lambdas and standalone definitions.
- Remove explicit return types from private members.
- Keep Unit return types.
- Give public APIs return types.
- Keep default arguments out of runtime code.
- When expressions are short enough for the line limit, keep assignments and chains on one line.
- When one large operand expands a short call, give that value a name.
- Keep the resulting call together.
- Examine the cause in constructors, generators and assertions before a layout change.
- A wrapper or parameter bag does not correct decomposition.
- Obey configured line limits.
- Destructure only when all fields are necessary.
- Use named pattern matching for tuple callbacks, not positional selectors.

### Modules and results

- Use a caller-defined trait, companion `make`, private final class `Live<CompanionObjectName>` and companion-owned results for business modules.
- For `UserRepository`, use implementation name `LiveUserRepository`.
- Keep module machinery out of domain types.
- Use operation-owned result enums for different business outcomes.
- Keep a result contract that expresses the full operation.
- Add no enum for success or absence without a business-outcome distinction.
- When applicable, obey project contracts that increase these obligations.
- Exclude generic capability and library conversion APIs from this result-enum rule.
- Use `Failure` for companion failure enums.
- Use top-level types for shared domain/config values, not orphan operation results.
- Keep public helpers as factories or utilities without business policy.

### Sequencing

- Use comprehensions for more than one dependent step.
- Keep comprehension generators together without gaps.
- When possible, use one physical line for each statement.
- Keep pure bindings out of effect-generator sequences by default.
- Examine multiline statements, nested blocks and mixed `<-`/`=` with [design discussion](principles.md#guardrails).
- Keep mixed sequencing only with a recorded same-level reason.
- A helper with the same nested operations is not that reason.
- Give constructed dependencies responsibility names, not names only for shorter calls.

### Codecs and refinements

- Use declarative codecs for boundary representations.
- Use the same codec only for two or more consumers and one shared representation invariant.
- Keep shared codecs as pure bidirectional mappings.
- Use a DTO only when a direct codec cannot express the format.
- Keep that DTO in its adapter.
- Examine changed refinement declarations, construction sites and autoRefine.
- Keep necessary refinements in production domain/usecases.
- Keep non-core proofs only for necessary guarantees. Include mirrored-package tests in this rule.
- When the consumer's guarantee lets you, use previously checked values.
- Keep constraints in domain-valued test inputs and substituted port responses.
- Use underlying types for standalone expected catalogs.
- Widen actual refined outputs safely for comparison.
- Keep full outcomes, identity and order with checked test values.
- Decrease duplicate proof sites without casts, assumptions or fixture frameworks.
- Count construction/conversion sites, not aliases or runtime records, when measuring duplicate proofs.
- Keep boundary rejection proof, constraints and full assertions when reducing duplicate construction.
- Record changed refinement declarations and explicit/implicit proof sites with `file:symbol` reasons.
- Give suppressions site-specific safety proof, smallest scope and the full rule identifier.
- Put the reason in review evidence, not an explanatory comment.

### Suites and assertions

- Use [trait-owned testing](principles.md#tests) for each trait's operation scenarios.
- Use `<Trait>ComponentTest` for usecase suites and `<Trait>SeamIntegrationTest` for port suites.
- Put architecture rules in one ArchitectureTest with one test.
- Give suite-local `buildX` helpers scenario-variable responses.
- Use fixed response values as the default, not input-dependent lambdas.
- For a recorded scenario requirement, such as an effectful pause, use a lambda.
- Keep dependency policy out of substitutes.
- Put observation Refs first in builder arguments.
- Use at most one suite-local Refs for call records, not stub responses.
- Remove Refs when no scenario asserts observations.
- Give responses, failures and clocks as builder inputs.
- Keep named argument roles in captured call observations.
- Give each control flag's state in its scenario.
- Use named local values for test setup.
- Use named generators for effectful controls.
- Give dependencies/responses directly to builders.
- Keep component, controls, inputs and expected results out of setup tuples.
- Use named table fields for one behavior.
- A key/value pair can express one association. Use named fields for larger tuples.
- Named-tuple annotations do not label positional call values.
- Keep resource controls and fixture data with different responsibilities out of the row.
- Use suite order: shared scenario data, scenarios, Refs and builders.
- Create substitutes in builders. Return only the component from a builder.
- Keep fixed response defaults in test code only.
- Keep cross-suite fixture-holder objects out.
- Use TestImplicits/Mocks only for nontrivial multi-suite construction with a shared invariant, not constructor aliases.
- Keep assertions on one line by default.
- If one operand is complex, give it a name before the single-line assertion.
- If both operands have large constructions, compare a short multiline assertion with two extracted values.
- When the multiline form makes each outcome clearer, use it.
- Interpolate the actual domain variant in test names.
- Use the examined setup precedent.
- Keep integration resources directly in suites, not callback setup frameworks.
- Use the selected profile for runtime composition and [guardrails](principles.md#guardrails) for coverage decisions.

## SolidJS / TypeScript

- When positional types do not show argument roles, use named values or available options fields.
- Assignment expressions are not parameter labels.
- More options bags do not correct responsibilities that are not clear.
- Use the project's style, dialog and feedback precedents.
- Make domain content easier to see than the editing controls.
- Keep presentation values and responsive treatment in source styles.
- Use sentence case for UI headings, controls and hints.
- Keep proper names, saved content and catalog labels.
- Give actions names for their effects.
- Display the saved names, not names rebuilt from IDs.
- Make toolbar icons agree with the project's visual language.
- Keep related actions visibly grouped.
- Give icons hover text and equivalent accessible names.
- Put keyboard shortcuts with their interaction.
- Keep text-entry and native-control behavior.
- Keep navigation, editing and viewing in different modes.
- Obey the selected profile's import convention.
- Keep framework-mandatory compiler/reactivity bindings.
- Use plain-logic tests for frontend validation/derivation/formatting.
- Export that logic as plain functions and use direct branch-table tests.
- Use component tests for rendering and wiring without duplicate branch tables.
- Use accessors/createMemo for derived state.
- Use Show for conditional rendering.
- Use For for keyed identity and Index for positional or primitive rows.
- Use the disabling predicate for disabled-control explanations.
- Connect those explanations with `aria-describedby`.
- Make UI invariants agree with backend contracts and fail-closed behavior.
- Reject invalid input without silent repair.
- Use accessible roles/names and user-event interfaces in tests, not classes, structure or test IDs.
- Use mandatory coverage directives only for guarded unreachable branches with evidence.
- Use user-path tests for reachable branches.
