# Design principles

## Ownership

- Domain values own strong types, value constraints and rules that hold wherever the value is used.
- Domain values carry no capabilities.
- Use types that exclude invalid states.
- Application operations own business decisions, aggregate construction and lifecycle transitions.
- Application services own orchestration.
- Companions own factories and operation results.
- Ports name the work their caller needs, in domain terms.
- Adapters implement real external boundaries.
- Only composition binds concrete interpreters.
- The supplying adapter fetches and assembles source data, then parses, normalizes and validates it into domain values.
- Consumers trust their port's domain-valid guarantees instead of validating them again.
- Persistence owns representation validity, relational constraints and atomic writes of supplied decisions.
- Application decisions need coordination across the full read/decision/write sequence.
- A transaction proves atomicity, not the safety of an earlier business decision.
- An event recorded after it happened takes its timestamp from the caller, not the server clock.
- The boundary converts local entry to an absolute instant, validates it and preserves it.
- Code, contracts and UI share domain vocabulary.
- New terms require a real distinction and maintainer agreement.

## Where rules belong

- Treat changed sorting, precedence, duplicate selection, retained spelling, grouping and display projections as behavior.
- Ask whether the rule remains true when its screen, endpoint or consuming use case changes.
- Purity alone does not establish domain ownership.
- Domain ordering needs a rule that holds independently of its consumer, such as chronology or book structure.
- Business selection and reconciliation belong to the deciding operation; source-format normalization belongs to its adapter.
- Source contract order belongs to its adapter.
- Visual order, labels and formatting belong to presentation.
- Global ordering instances that select among duplicates must identify the domain rule that justifies that selection.

## Evolve existing responsibilities

- Trace existing reads, transformations, cached results and consumers before adding another data-fetching path.
- Check the validity, failure behavior and lifetime of retained results.
- Reuse sufficient contracts; evolve insufficient contracts at their existing owner.
- Separate a responsibility only when its decisions change independently.
- A new caller alone does not justify a new abstraction.
- Share knowledge whose divergence would be a bug.
- Keep independently changing resource rules local, even when their code looks similar.
- An extraction must own a required decision, invariant or external boundary.
- For each new method or argument, identify the required decision or guarantee lost if it is removed.
- Thin wrappers and several remote calls alone do not justify another layer.
- A module coordinating our ports is a use case, not an external adapter.
- An adapter test crosses its real external system or representation.
- Incoming HTTP tests send real requests and substitute the application service.
- Outgoing HTTP tests use the real remote service with isolated test state.
- Keep mutable authoritative records separate from cached projections.
- Projections preserve identity and required evidence.
- Failed reads and identity mismatches remain explicit failures.

## Contracts and effects

- Parameters contain only inputs the operation uses.
- Justify batch size and scope separately from entity identity.
- Accept needed facts, not another operation's result envelope.
- Grouped inputs represent cohesive facts; sum types represent distinct valid states.
- Groups must not hide unrelated parameters or dependencies.
- Inject fixed collaborators and settings once at construction.
- Invocation APIs accept only data that can change for that call.
- Composition parses static configuration into the types its consumers need.
- Split independently changing responsibilities; grouped dependencies must represent a real shared responsibility.
- Pure computation returns plain values.
- Effects require I/O, resource, concurrency or capability work.
- Keep pure APIs plain even when a caller wraps their result in an effect.
- Adapters use application contracts, not peer adapters or bypassed infrastructure.
- Collections preserve typed identity, required cardinality and associations.
- Preserve duplicate handling and meaningful precedence through projections.
- Use keyed associations instead of parallel positional lists.
- Configurable business data has an authority and an explicit read/update path.
- Constants or relocated resource files do not establish configurability.
- Repository-backed business data uses its caller-owned repository contract; static settings do not replace that lifecycle.
- State-dependent writes check and commit against the same current state.
- Test concurrency, cancellation and resource guarantees where they are enforced.
- Canonical encodings preserve semantics used outside the core, including stored ordering across retained data.

## Complexity and expression

- Each unit owns one responsibility.
- Statements in one method stay at the same abstraction level.
- Keep orchestration visible and linear.
- Independently changing algorithms retain their own owners.
- Keep a pure mapping private when only one component needs it.
- Order declarations and parameters most important first.
- Inspect methods beyond roughly 7-10 lines, units beyond a screenful, and suites beyond roughly three times production size.
- Extract a newly exposed responsibility in the change that exposes it.
- Never trim tests to hide the size ratio.
- Prefer the simplest design that satisfies the agreed behavior.
- Reduce design complexity before adding scope; narrowing approved scope requires maintainer agreement.
- Compatibility requires deployed versions or retained data to preserve.
- Errors and uncertainty remain explicit.
- Preserve the primary failure when compensation also fails.
- Model success, expected short-circuit and failure as distinct states when all three occur.
- Avoid unsafe casts, partial operations and success-shaped fallbacks.
- Names and structure carry code intent; rationale belongs in owning docs.
- Add no explanatory code comments, including documentation comments.
- Required tooling directives are allowed; record their justification in review evidence.
- Production dependencies require a demonstrated capability and pinned versions.
- Encode important dependency directions in architecture checks.
- Add and document a gate alongside the code or dependency it governs.
- Compiler gates reject unsafe constructs, unused code and accidental widening.
- Include new dependencies in the repository's existing update automation.
- Coverage shows which code ran; scenarios and assertions establish behavior. Extend tests for missing guarantees, not merely to execute lines.
- UI controls own their accessible state and focus continuity.
- Layout handles content constraints and narrow widths, not just one viewport.

## Guardrails

- Keep configured compiler, formatter, lint, architecture and coverage gates enabled. Gate changes and rule exceptions require scoped maintainer approval.
- Aim for at most five API inputs and five injected business dependencies, excluding generic capabilities. Comprehension statements normally occupy one physical line.
- Crossing these expectations prompts inspection of input cohesion, valid states, abstraction levels, responsibilities and test seams. Refine or separate when that resolves a concrete concern; otherwise retain the cohesive design and record why. Crossing an approximate signal alone requires neither approval nor independent review; substantive changes and hard-rule exceptions follow execution's approval rules.
- Approximate size signals prompt inspection, not permission. Keep larger cohesive units with a reason; split distinct responsibilities.
- At a coverage gate, including 100%, identify the missing observable guarantee. For composition or unreachable code, prove a repository-permitted exclusion and obtain required authorization.
- Numeric compliance alone does not establish design quality.
