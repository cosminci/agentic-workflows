# Application-specific contracts

Load only the applicable application section. Shared architecture and syntax belong to [principles](../principles.md) and [conventions](../conventions.md).

Use the selected runtime profile for stack-wide rules. Project entrypoints, executable owners and scoped decisions establish applicability.

## Typelevel

These are application deltas to the [Typelevel profile](../profiles/typelevel.md), not another definition of it.

Photography and Audiobookshelf retain a formatter-owned 150-column limit. Read executable configuration for changes.

### Photography

- Return operation-owned enums from business operations after F/Db unwrapping, not bare success, Option, Unit or Either.
- Exclude generic capability/library APIs and domain types from that result gate.
- Keep the named StructuredLogger binding in composition.
- The historical `[Photography]` name identifies the application logger, not a method parameter.
- For UI work, examine workspace styles, ChapterOutline toolbar, AlbumDialog and SpreadConfirmation as precedents.
- Read accurate UI values and wording from source.

### Audiobookshelf

- Use per-operation sum types for business operations, not Either.
- Add no enum to capabilities without business policy or a necessary outcome distinction.
- Examine the historical `[Audiobookshelf]` logger identity in composition/configuration, not as a method parameter.
- Keep evidence deterministic and technical output.
- Give suggestions, not fuzzy auto-selection.
- With maintainer approval, perform live-state verification after media work.
- Queue acceptance is not completion. A source collection is not proof of one book.
- Obey local operational-access rules.

## Direct style

Plant-journal adds these constraints to the [direct-style profile](../profiles/direct-style.md).

- Return per-operation ADTs from business operations, not Either.
- When only failure is possible, use a failure subtype without an unreachable success match.
- Use stable ID builders such as `buildUuid(index)`, not literal IDs.
- Keep named TypeScript imports to at most three names on one line. Use namespace imports for larger sets.
- Keep Solid reactive/control-flow bindings and Dagger decorators named for their compiler, reactivity and introspection semantics.
- Give mandatory structural/architecture suites names for their production rule owners.
- Use Vitest componentTest, seamIntegrationTest and systemIntegrationTest suffixes for the boundary under test.
- Use the executable generator and drift check for endpoint/artifact changes under [generated-contract ownership](../context.md#contracts-are-protocols).
- Use the tapir seam interpreter for logic-bearing endpoints and a live server for static serving.
- Keep domain/HTTP logic and reachable failures in coverage. Limit composition exclusions to shells without that logic.
- Give site-level exclusion proof for output-only callbacks, OpenAPI-only mappings and unreachable branches.
- Keep tapir's permitted Any endpoint parameter without casts, untyped responses or widening bypasses.
- Keep unsafe-cast bans without an incompatible blanket Wart.Any gate.
- Use English domain vocabulary. Translate Romanian source records at the adapter.

## Other owning domains

For Home-automation work, read its AGENTS, runbooks, deployment/reload contracts and platform examples.

Read the applicable local owner before entity-reference, deployment, dashboard-action or media-output changes.

Scala rules do not replace Python/YAML/HA requirements. Local production-state permission remains subject to [execution](../executor.md#approvals-and-delivery).
