# Contribution conventions

## Authority

Obey every applicable convention in this file, the selected profile and local contracts in production code and tests.

These are mandatory checks, including checks for design signals. Use [design analysis](principles.md#guardrails) to find the cause and select the repair.

Keep source compliance separate from design conclusions. A cohesive design does not give permission for a convention violation.

Use only exceptions specified by the rule owner or approved through [execution](executor.md#approvals-and-delivery). Record the applicable condition or scoped approval.

## Shared

#### CV-01: Method and value names

- For project-owned methods, including public APIs and private test helpers, name the operation or predicate that the caller gets.
- Compare the name at the call site with the implementation or contract. Distinguish construction, encoding, reading, and effects; a word that can be a verb is not sufficient.
- Use a fact name for a value or a property accessor that exposes an existing fact. Preserve names required by an external interface.
- Give values names for domain facts, not their construction helpers.

#### CV-02: Shared call and source readability

- Show argument roles through call syntax or named values.
- Use imports, not fully qualified paths.
- Examine readability after formatting.
- Give business conditions and expected outcomes names, not shallow helper wrappers.

#### CV-03: Scenario and outcome clarity

- Assert full observable outcomes in tests.
- Keep test setup deterministic and local to its scenario.
- Put blank lines between Arrange, Act and Assert.
- Put test data in semantic scenario order.
- Use table tests for cases with only input differences.
- Give suites names for their production boundaries.
- Start case names with `should`, after the runner prefix when necessary.
- Use each contract's owner for its proof. For Scala fixture sharing, use [suite conventions](#suites-and-assertions).

#### Delivery

- Use imperative commit subjects with at most 72 characters and no issue/ticket identifiers.
- When the diff does not give the reason, give it in the commit body. Keep issue references out.
- Keep commit signing and repository identity.
- Keep secrets and credentials out of commits, embedded text and printed output.
- Read setup and gate commands from their executable owners and local guidance.

## Scala

Use the project's Scala gates for automated conventions. Check the `Compile` and `Test` configurations separately. Report missing gate coverage as a rollout gap. Examine design signals even when the gates pass.

Use each `CV` code as a stable review identifier. Each code covers every obligation under its heading. Keep existing codes when rules move; use a new code for a distinct obligation.

### Syntax

#### CV-10: Scala argument roles

- In multi-argument application calls, name literals, booleans, dates, collections and optional values that `NamedSentinelArguments` does not check.
- Include constructors, private calls, test builders and captured calls.
- When different domain types show their roles, keep positional arguments.
- Keep the library's call syntax.
- Examine call roles that are not clear as a design signal, not only missing labels.

#### CV-11: Scala expression syntax

- Put construction machinery at the end of the expression.
- When Scala 3 and the formatter let you, use significant indentation and optional braces.
- Remove optional parentheses that remain after formatting.
- Keep necessary grouping and library syntax.
- Use braces or colon syntax for block arguments at the call boundary.
- Use braces or colon syntax for multiline lambdas and standalone definitions.

#### CV-12: Scala declaration contracts

- Remove explicit return types from private members.
- Keep Unit return types.
- Give public APIs return types.

#### CV-13: Scala expression layout

- When expressions are short enough for the line limit, keep assignments and chains on one line.
- When one large operand expands a short call, give that value a name.
- Keep the resulting call together.
- Examine the cause in constructors, generators and assertions before a layout change.
- A wrapper or parameter bag does not correct decomposition.
- Obey configured line limits.

#### CV-14: Scala pattern roles

- When a branch uses all fields, destructure case classes and enum cases in pattern matches and callbacks.
- Give used fields in tuple patterns meaningful names and use wildcards for the others.

### Modules and results

#### CV-20: Business module boundary

- Use a caller-defined trait, companion `make`, private final class `Live<CompanionObjectName>` and companion-owned results for business modules.
- For `UserRepository`, use implementation name `LiveUserRepository`.
- Keep module machinery out of domain types.
- Keep public helpers as factories or utilities without business policy.

#### CV-21: Result contract

- Use operation-owned result enums for different business outcomes.
- Keep a result contract that expresses the full operation.
- Add no enum for success or absence without a business-outcome distinction.
- When applicable, obey project contracts that increase these obligations.
- Exclude generic capability and library conversion APIs from this result-enum rule.
- Use `Failure` for companion failure enums.
- Use top-level types for shared domain/config values, not orphan operation results.

### Sequencing

#### CV-30: Sequencing and dependency preparation

- Use comprehensions for more than one dependent step.
- Keep comprehension generators together without gaps.
- Examine multiline statements, nested blocks and mixed `<-`/`=` with [design discussion](principles.md#guardrails).
- Keep mixed sequencing only with a recorded same-level reason and scoped maintainer approval.
- A helper with the same nested operations is not that reason.
- Give constructed dependencies responsibility names, not names only for shorter calls.

### Codecs and refinements

#### CV-40: Representation ownership

- Use declarative codecs for boundary representations.
- Use the same codec only for two or more consumers and one shared representation invariant.
- Keep shared codecs as pure bidirectional mappings.
- Use a DTO only when a direct codec cannot express the format.
- Keep that DTO in its adapter.

#### CV-41: Refinement proof

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

#### CV-42: Scoped suppression

- Give suppressions site-specific safety proof, smallest scope and the full rule identifier.
- Put the reason in review evidence, not an explanatory comment.

### Suites and assertions

#### CV-50: Suite ownership

- Use [trait-owned testing](principles.md#tests) for each trait's operation scenarios.
- Use `<Trait>ComponentTest` for usecase suites and `<Trait>SeamIntegrationTest` for port suites.
- Put architecture rules in one ArchitectureTest with one test.

#### CV-51: Builders and substitutes

- Give suite-local `buildX` helpers scenario-variable responses.
- Use fixed response values as the default, not input-dependent lambdas.
- For a recorded scenario requirement, such as an effectful pause, use a lambda.
- Keep dependency policy out of substitutes.
- Put observation Refs first in builder arguments.
- Use at most one suite-local Refs for call records, not stub responses.
- Remove Refs when no scenario asserts observations.
- Give responses, failures and clocks as builder inputs.
- Create substitutes in builders. Return only the component from a builder.
- Keep fixed response defaults in test code only.

#### CV-52: Scenario setup and table data

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
- Interpolate the actual domain variant in test names.
- Use the examined setup precedent.

#### CV-53: Fixture and resource ownership

- Keep cross-suite fixture-holder objects out.
- Use TestImplicits/Mocks only for nontrivial multi-suite construction with a shared invariant, not constructor aliases.
- Keep integration resources directly in suites, not callback setup frameworks.
- Use the selected profile for runtime composition and [guardrails](principles.md#guardrails) for coverage decisions.

#### CV-54: Assertion layout and clarity

- Name a complex operand when its construction makes the outcome harder to see.
- When both operands are large, compare a short multiline assertion with two named values. For a flagged form, follow the exclusion policy.

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
