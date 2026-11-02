# Design principles

## Complexity

- Keep complexity at bay. For the affected design, identify required invariants, remove machinery that serves none and prove those invariants still hold; obtain approval when simplification crosses authorized scope.
- Match infrastructure to actual deployment needs. On a home NAS, additional processes, coordination and configuration require a concrete operating need.
- Reduce design complexity before adding scope; narrowing approved scope requires maintainer agreement.
- Compatibility requires deployed versions or retained data to preserve.

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

- Before adding or changing an API, read its whole trait, implementations and callers; trace existing acquisition, transformations and retained results, including validity, failure behavior and lifetime.
- Choose in order: reuse a sufficient contract; evolve an insufficient contract and migrate its callers; add a complementary operation only for a distinct decision or guarantee, not another input shape for the same work.
- State the distinct purpose of each operation in the resulting trait. Names and contracts must describe siblings at one abstraction level; orchestration of those operations belongs at their caller.
- Replace overlapping variants with coherent building blocks and caller composition. Retain multiple APIs for the same work only when a required compatibility contract justifies them.
- Separate a responsibility only when its decisions change independently.
- A new caller alone does not justify a new abstraction.
- Share knowledge whose divergence would be a bug.
- Keep independently changing resource rules local, even when their code looks similar.
- Each abstraction must own a required decision, invariant or external boundary; thin wrappers and extra remote calls do not justify a layer.
- For each new method or argument, identify the required decision or guarantee lost if it is removed.
- A module coordinating our ports is a use case, not an external adapter.
- Keep mutable authoritative records separate from cached projections.
- Projections preserve identity and required evidence.
- Failed reads and identity mismatches remain explicit failures.

## Abstractions and composition

- Each unit owns one responsibility; statements within a method stay at one abstraction level.
- Decompose through cohesive building blocks and injected collaborators. Split by independently changing decisions, not prescribed layers, verticals or one-method traits.
- Prefer narrow, deep abstractions: a small API hides substantial cohesive work. Many parameters over a shallow implementation signal poor decomposition.
- Keep orchestration visible and linear; independently changing algorithms retain their own owners.
- Keep a pure mapping private when only one component needs it.
- Order declarations and parameters most important first.
- Extract a newly exposed responsibility in the change that exposes it.

## Contracts and effects

- Parameters contain only inputs the operation uses.
- Justify batch size and scope separately from entity identity.
- Accept needed facts, not another operation's result envelope.
- Grouped inputs represent cohesive facts; sum types represent distinct valid states.
- Groups must not hide unrelated parameters or dependencies.
- Inject fixed collaborators and settings once at construction.
- Invocation APIs accept only data that can change for that call.
- Composition parses static configuration into the types its consumers need.
- Grouped dependencies must represent a real shared responsibility.
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
- Canonical encodings preserve semantics used outside the core, including stored ordering across retained data.

## Failures

- Errors and uncertainty remain explicit.
- Preserve the primary failure when compensation also fails.
- Model success, expected short-circuit and failure as distinct states when all three occur.
- Avoid unsafe casts, partial operations and success-shaped fallbacks.

## Expression

- Names and structure carry code intent; rationale belongs in owning docs.
- Add no explanatory code comments, including documentation comments.
- Required tooling directives are allowed; record their justification in review evidence.

## Libraries and tooling

- Inspect supported library capabilities before writing plumbing. Use framework middleware for shared HTTP concerns and declarative codecs/refinements for boundary conversion; application code owns policy.
- Production dependencies require a demonstrated capability, pinned versions and inclusion in existing update automation.
- Encode important dependency directions in architecture checks.
- Add and document a gate alongside the code or dependency it governs.
- Compiler gates reject unsafe constructs, unused code and accidental widening.

## Tests

- Test concurrency, cancellation and resource guarantees where they are enforced.
- An adapter test crosses its real external system or representation.
- Incoming HTTP tests send real requests and substitute the application service.
- Outgoing HTTP tests use the real remote service with isolated test state.
- Coverage shows which code ran; scenarios and assertions establish behavior. Extend tests for missing guarantees, not merely to execute lines.

## Presentation

- UI controls own their accessible state and focus continuity.
- Layout handles content constraints and narrow widths, not just one viewport.

## Guardrails

- Keep configured compiler, formatter, lint, architecture and coverage gates enabled. Gate changes and rule exceptions require scoped maintainer approval.
- Design signals: more than five API inputs or injected business dependencies (excluding generic capabilities), methods beyond roughly 7-10 lines, units beyond a screenful and suites beyond roughly three times production size.
- For each signal, state the suspected design defect and its caller or maintenance consequence. Discuss whether to evolve the contract, change composition or retain the cohesive design; record the chosen resolution.
- Fix a demonstrated defect at its required scope, including wider caller refactoring; obtain execution's required approvals. Retain a signal when analysis shows no defect, with the concrete reason.
- Counts and formatting diagnose design; they are not goals. Pass-through helpers, parameter bags and trimmed tests do not resolve a design concern.
- Crossing a signal alone requires neither approval nor independent review; responsibility changes, scope changes and hard-rule exceptions follow execution's approval rules.
- At a coverage gate, including 100%, identify the missing observable guarantee. For composition or unreachable code, prove a repository-permitted exclusion and obtain required authorization.
