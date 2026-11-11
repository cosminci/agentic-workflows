# Review evidence

This file defines evidence requests, not engineering policy. Read the linked rule owner for each applicable boundary before assigning a verdict.

Use [the review procedure](../reviewer.md) for scope, statuses and closure, and [the record template](../templates/review.md) for output.

## Architecture

| Rule owner | Evidence to examine |
| --- | --- |
| [Eight foundations](../foundations.md) | Source and enforcement for each rule, including missing automation. |
| [Contract evolution](../principles.md#evolve-existing-responsibilities) | Trigger-to-consumer trace, full changed traits, implementations, callers and owning suites. Compare reuse, evolution and removal. |
| [Ownership](../principles.md#ownership) and [policy placement](../principles.md#where-rules-belong) | Decisions, validity checks, ordering, duplicate selection and projection owners across consumers. |
| [Contracts and effects](../principles.md#contracts-and-effects) | Every input's consumed facts, constructor/invocation lifetimes, effect categories, typed associations and state-dependent write boundary. |
| [Complexity](../principles.md#complexity) and [composition](../principles.md#abstractions-and-composition) | Necessary guarantees for added modules, methods and arguments. Removed workaround and kept invariants for repairs. |
| [Persistence](../principles.md#persistence-design) | Identity/cardinality, constraints, query plans, write/storage costs, compatibility and concurrent-write scenarios. |
| [Libraries and tooling](../principles.md#libraries-and-tooling) | Available capability, custom-mechanism reason, dependency versions and applicable strict gates. |
| [Failures](../principles.md#failures) | Full results for success, short-circuit, failure and compensation. |
| [Test boundaries](../principles.md#tests) | Production owner, public operation, scenarios, substitutes and actual external boundary. |

## Readability and suites

| Rule owner | Evidence to examine |
| --- | --- |
| [Design signals](../principles.md#guardrails) | Baseline/current owner and suite size, wide APIs, bulky operands, sequencing, fixture dependencies, suspected defect and caller effect. |
| [Shared conventions](../conventions.md#shared) | Names, argument roles, scenario order and complete observable assertions after formatting. |
| [Scala syntax](../conventions.md#syntax) | Constructors, private/captured calls, literals/options, return types, imports, block/lambda syntax and tuple patterns. |
| [Modules and results](../conventions.md#modules-and-results) | Trait/factory/implementation/result declarations, public helpers and operation outcome distinctions. |
| [Sequencing](../conventions.md#sequencing) | Changed comprehensions, nested levels, multiline statements and recorded reasons for kept mixed bindings. |
| [Codecs and refinements](../conventions.md#codecs-and-refinements) | Declarations, callers, consumers, explicit/implicit construction sites, safety proof and suppressions. |
| [Suites and assertions](../conventions.md#suites-and-assertions) | Existing scenario changes, setups, builder returns, Refs, resource controls, table rows, full expected results and suite owner. |
| Selected [direct-style](../profiles/direct-style.md) or [Typelevel](../profiles/typelevel.md) profile | Runtime/effect declarations, result composition, imports, logging/metrics and profile exceptions in changed source. |
| Applicable [local contract](local-contracts.md) | Application-specific result, operational, tooling, generated-artifact and UI constraints. |

## Frontend inventory

| Rule owner | Evidence to examine |
| --- | --- |
| [Frontend conventions](../conventions.md#solidjs--typescript) | Logic tables versus component proof, derived/control-flow state, input rejection, authoritative names, disabled explanations, real events and coverage sites. |
| [Presentation](../principles.md#presentation) | Persistent focus and accessible state through transitions, structural layout and narrow widths. |
| Selected profile and application contract | Import semantics, mandatory named bindings, existing styles/dialogs/toolbars and feedback precedent. |

## Spec and completion

| Rule owner | Evidence to examine |
| --- | --- |
| [Discovery](../discovery.md) | Discussion coverage, sources, explicit decisions, applicable prior approvals and unresolved questions, compared with every spec statement. |
| [Change template](../templates/change-spec.md) | Classification, baseline-to-intended behavior, model evolution, criteria, failure/boundary/accessibility cases, flow and deferred owners. |
| [Design template](../templates/design-map.md) | Every required design surface and its confirmed applicability, with concrete design in the linked artifact. |
| [Execution](../executor.md) | Cause/expected behavior, hypothesis evidence, approvals, amendment scope, permitted merge order, completed checks and delivery state. |
| [Strategic admission](../context.md#admission-test) | Affected living-doc owners from final source, including unchanged owners behind `None`, and each admission answer. |
| [Protocols](../context.md#contracts-are-protocols) | Actual producer/consumer exchange, authoritative definitions, generated artifacts and protocol accuracy across prose, tables and diagrams. |
| [Document ownership](../context.md#authoring-and-closure) | Reader decision, missing knowledge, operating constraints, facts changed by delivery and duplicate or misplaced content. |
| [Writing](../writing/SKILL.md) | Signal, clarity, terminology, grammar/dictionary review, prose boundaries and Markdown wrapping for each changed artifact. |

## Replay evidence

Use the [worked cases](examples.md#review-replays) for the applicable failure class, not as the scope limit.

For a reported miss, use [repair verification](../reviewer.md#reported-misses). Added text or a matching token alone does not demonstrate changed behavior.
