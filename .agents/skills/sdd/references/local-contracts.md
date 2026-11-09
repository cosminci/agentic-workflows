# Local quality contracts

Read this file for implementation or review of the selected stack. Project entrypoints, executable owners and scoped maintainer decisions give applicability.

Keep validated stack differences. One application's example does not give a different application's runtime settings.

## Typelevel

- Use effect-polymorphic algebras in Photography and Audiobookshelf.
- Use capability context bounds/accessors for Logger, Clock and identity.
- Give factories and private constructors explicit business ports/settings.
- Return operation-owned enums from Photography business operations after F/Db unwrapping.
- Reject bare success, Option, Unit and Either at this gate.
- Keep an enum that expresses the full operation.
- Exclude generic capability/library APIs and domain types from this result gate.
- Use per-operation sum types in Audiobookshelf business operations, not Either.
- Add no enum to capabilities without business policy or a necessary outcome distinction.
- Return necessary helper data, not the caller's result envelope for a different responsibility.
- Keep the formatter-owned 150-column limit.
- Examine density after formatting.
- Use Cats `.pure[F]`, `.some`, `.asRight`, `.asLeft`, `.as` and traversal syntax.
- When available, use local `.eitherT` syntax.
- Keep the named StructuredLogger binding in composition.
- Historical `[Photography]` and `[Audiobookshelf]` names identify application loggers, not method parameters.
- Examine logger identity in composition/configuration.
- Use shared effect ownership, not logs at each call site.
- Give coverage/tooling directives site-level proof in review evidence.
- Keep logic-bearing adapters in coverage despite permitted composition exclusions.
- For photography UI work, read workspace styles, ChapterOutline toolbar, AlbumDialog and SpreadConfirmation.
- Keep content-first layout, icon grouping, focus/pending feedback and shortcuts.
- Get accurate values and wording from source.
- Keep Audiobookshelf evidence deterministic.
- Give suggestions, not fuzzy auto-selection.
- Keep technical output.
- With maintainer approval, do live-state verification after media work.
- Queue acceptance is not completion. A source collection is not proof of one book.
- Obey local operational-access rules.

## Direct style

- Return per-operation ADTs from Plant-journal business operations, not Either.
- Use Either only for internal dependent composition.
- When only failure is possible, use a failure subtype.
- Add no full result with an unreachable success match.
- Use `using` and the project's TestImplicits for capabilities and business MetricsApi ports.
- Keep Cats Effect/ZIO runtimes out.
- Use stable ID builders such as `buildUuid(index)`.
- Keep named TypeScript imports to at most three names on one line.
- Use namespace imports for larger sets.
- Keep Solid reactive/control-flow bindings and Dagger decorators named.
- Compiler, reactivity and introspection semantics depend on those bindings.
- Use shared trait ownership for backend suites.
- Give mandatory architecture checks names for their rule owners.
- Use Vitest componentTest, seamIntegrationTest and systemIntegrationTest suffixes for the boundary under test.
- Keep zero-warning analysis, compiler errors, 100% coverage and architecture-direction checks as hard gates.
- Keep generated OpenAPI/client artifacts read-only.
- Use the executable generator and drift check for endpoint/artifact changes.
- Keep HTTP logic in coverage measurement.
- Use the tapir seam interpreter for logic-bearing endpoints.
- Use a live server for static serving.
- Use the project's exclusions for composition roots without domain/HTTP logic.
- Give site-level proof for output-only callbacks, OpenAPI-only mappings and unreachable branches.
- Put COVERAGE-OFF/ON markers on different lines.
- Put the explanation immediately before COVERAGE-OFF.
- Keep tapir's permitted Any endpoint parameter without casts, untyped responses or widening bypasses.
- Keep unsafe-cast bans without an incompatible blanket Wart.Any gate.
- Use Info for mutations/action/ID and business transitions in usecases.
- Use Error for unexpected failures with operation/cause.
- Keep reads without errors silent.
- Keep business-outcome logs out of adapters.
- Put startup and top-level failure logs in composition.
- Keep one line per log record, without a raw stack trace.
- After work or compensation without errors, measure historical business trends with one increment/gauge set for each call.
- Derive current-state gauges from reads.
- Keep historical metrics without changes after history edits/deletions.
- Use the same transport registry for HTTP RED and JVM/process USE.
- Use English domain vocabulary.
- Translate Romanian source records at the adapter.
- Keep declarative codecs private to their translating adapter.
- Keep containers without one responsibility and encode/decode helper families out.
- Keep prose paragraphs to at most two sentences.
- Put each paragraph/list item on one source line.

## Other owning domains

Keep Home-automation's AGENTS, runbooks, deployment/reload contracts and platform examples. Scala/Typelevel rules do not replace Python/YAML/HA requirements.

Before entity-reference, deployment, dashboard-action or media-output changes, read the local contract.

Examine the supported boundary.

Get production-state approval locally. This shared pack gives no such permission.
