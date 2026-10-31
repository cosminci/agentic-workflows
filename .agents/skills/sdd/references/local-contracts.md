# Local quality contracts

Read when implementing or reviewing the selected stack. These preserve the historically validated differences; project entrypoints, executable owners and scoped maintainer decisions determine applicability. The examples below are contract selectors, not a reason to apply one application's runtime settings to another.

## Typelevel

- Both photography and Audiobookshelf use effect-polymorphic algebras and capability context bounds/accessors for Logger, Clock and identity; business ports/settings are explicit factory/private-constructor dependencies.
- Photography trait operations, after unwrapping F/Db, use operation-owned result enums: bare success, Option, Unit and Either fail its business-operation gate. Existing enums expressing the operation completely are preserved, not mechanically replaced. Generic capability/library APIs and plain domain values are outside this gate.
- Audiobookshelf trait business operations use per-operation sum types rather than Either. Do not invent an enum for a mechanical capability or widen a required operation result to a caller's unrelated envelope.
- Preserve the 150-column Scala limit where its formatter owns it; inspect readable density after formatting. Cats value syntax includes `.pure[F]`, `.some`, `.asRight`, `.asLeft`, `.as` and traversal syntax; `.eitherT` uses the existing local transformer syntax where available.
- Composition preserves the current named StructuredLogger binding: photography's historical `[Photography]` and Audiobookshelf's `[Audiobookshelf]` examples identify application loggers, not logger parameters to business methods. Verify the actual composition/configuration rather than copying another application's identity.
- Required coverage/tooling directives carry exact site proof in review evidence under shared comment policy; local permitted glue exclusions do not justify excluding logic-bearing adapters.
- Photography UI work reads current application/workspace styles, ChapterOutline toolbar, AlbumDialog and SpreadConfirmation. Their established content-first layout, icon grouping, focus/pending feedback and shortcut behavior remain obligations; source owns precise values/copy.
- Audiobookshelf replacements preserve deterministic explicit evidence, suggestions rather than fuzzy auto-selection, technical output and authorized live-state verification after media operations. Queue acceptance is not completion; a source collection is not proof of one book. Operational access remains governed locally.

## Direct style

- Plant-journal trait business operations return per-operation ADTs, never Either; Either is internal dependent composition only. A failure subtype is accepted when only failure is possible, rather than a whole result plus unreachable success match.
- Capabilities, including business MetricsApi ports, use `using` and existing TestImplicits; no Cats Effect/ZIO runtime is introduced. Preserve stable ID builders such as `buildUuid(index)`.
- TypeScript named imports fit at most three names on one line; otherwise use namespace imports. Solid reactive/control-flow bindings and Dagger decorators stay named because compiler/reactivity/introspection semantics depend on them.
- Backend behavioral suites own real runtime boundaries; structural/policy suites have plain names. Vitest componentTest, seamIntegrationTest and systemIntegrationTest suffixes reflect the boundary actually proved.
- Existing zero-warning static analysis, compiler errors, 100% coverage and architecture-direction checks are hard gates. Generated OpenAPI/client artifacts remain read-only: use the executable generator and contract-drift check when endpoints/artifacts change.
- HTTP logic remains measured: logic-bearing endpoints use the tapir seam interpreter, static serving a live server. Genuine composition roots use existing exclusions; narrow output-only callbacks/OpenAPI-only mappings/unreachable branches need exact proof, standalone COVERAGE-OFF/ON lines and the explanation immediately above COVERAGE-OFF.
- Tapir's legitimate Any endpoint parameter does not license casts, untyped responses or widening hacks; preserve the configured unsafe-cast bans rather than enabling an incompatible blanket Wart.Any gate.
- Use-case services log mutations/action/ID and meaningful transitions at Info, unexpected operation/cause at Error; successful reads are silent, adapters do not log business outcomes and composition logs startup/top-level failure. One line per record, no raw stack trace.
- Business metrics measure historical trends one increment/gauge set per call after successful work/compensation; current-state counts are read-derived gauges. Editing/deleting recorded history does not replay its metrics. HTTP RED and JVM/process USE share the transport registry.
- Domain vocabulary is English; translate Romanian source records at the adapter. Declarative codecs remain private to the translating adapter, not miscellaneous Helpers/Queries/StoredValues/Payload containers or encode/decode helper families.
- Prose paragraphs have at most two sentences, with each paragraph/list item on one source line; split distinct facts into bullets rather than hard wrapping.

## Other owning domains

Home Assistant and other home-automation domains retain their existing AGENTS, operational runbooks, deployment/reload contracts and concrete platform examples. Scala/Typelevel gates do not flatten their Python/YAML/HA requirements. Before changing an entity reference, deploy mechanism, legacy dashboard action or media output, load the relevant local contract and verify the actual supported boundary; this shared pack grants no production-state authorization.
