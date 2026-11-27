# Scalafix custom rules

Consumers resolve `com.cosminci:scalafix-custom-rules_2.13` through `scalafixDependencies`. Set `scalafixResolvers` to the Maven repository at `https://raw.githubusercontent.com/cosminci/agentic-workflows/<artifact-commit>/`. Pin the full artifact commit and version. Select rules in the project's Scalafix configuration.

## Rule guide

Semantic rules require SemanticDB. Scalafix diagnostics explain what to investigate when a rule fires. See the [Scala 3 fixtures](smoke/src/test/scala) for exact triggers and controls.

| Rule | Flags |
| --- | --- |
| `AdapterCodecVisibility` | Circe or Tapir codecs exposed outside their owner |
| `MultilineAssertion` | Multiline MUnit assertions |
| `MultilineComprehensionStep` | Multiline generators, bindings, or guards |
| `MultilineEitherCond` | Multiline `Either.cond` calls |
| `NamedSentinelArguments` | Positional zero, `""`, `None`, `Nil`, `Option.empty`, or `()` in multi-argument project calls; positional `()` in `Either.cond` |
| `NoDefaultParameters` | Defaults on methods and constructors |
| `NoMixedForComprehensionBindings` | `=` in a `for` that also uses `<-` |
| `NoMultilineExpressionsInParentheses` | Multiline expressions or steps inside `()` |
| `NoPartialCaseClassDestructuring` | Ignored fields in project-owned Scala 3 case-class patterns |
| `NoPositionalTupleSelectors` | Numeric tuple selectors in inline callbacks, including callbacks returned from blocks |
| `NoPredefRequire` | Both `Predef.require` overloads, including infix and aliases |
| `OptionEitherBindingNames` | `Option` without `maybeX`; `Either` without a `Result` suffix |
| `PreferCatsSyntax` | Selected factories and sleep-then-effect sequences |
| `SimplifiedForYield` | One named generator with no guards or bindings |

### Boundaries worth keeping

- **Codecs:** Set `owners` or `adapterPackages`; use `sharedOwners` for genuine sharing. Codec subtypes and constructor fields count, not ordinary parameters.

- **Defaults:** Enable `NoDefaultParameters` in production, not tests.

- **Multiline parentheses:** Wrapped single-line items, nested `Json.obj`, and brace or colon `.handle` entries in `serverEndpoints` stay inline. Expressions inside those entries are still checked. String line breaks count only for `sql` and `fr` interpolations.

- **Binding names and patterns:** By-name, aliased, and inferred `Option`/`Either` variants count; fully generic extension receivers do not. Case-class pattern tests and references count as uses; `val`/`var` patterns flag only explicit discards. Standard-library patterns and unrelated custom extractors are out of scope.

- **Cats:** Use `Option[A]` and `.some` for optional values, `A` for required ones, and `none[A]` for known absence. Keep null checks (`Option(value)`), effect suspension (`IO.delay`, `Sync[F].delay`), standalone sleeps, and `IO.blocking`. Type inline callbacks as needed; use `.eitherT` and `.lift` on effects. Preserve right-hand construction timing for `>>` if it is observable.

## Release and verification

Bump the minor version for new rules or features. Reserve patch releases for fixes.

Publish releases with sbt `publish` to a Maven-layout directory, then commit the generated version directory and module metadata to the `scalafix-maven` branch. Do not replace published versions.

To verify a candidate, publish it to a temporary Maven repository and use it in the `smoke` build. Run `Test / scalafix --check` for assertions or set `Test / scalafixConfig` to select another configuration. Expect twelve diagnostics with `named-sentinel-arguments.conf`, nine with `codec.conf`, seven with `either-cond.conf`, three with `simplified-for-yield.conf` and nine with `no-predef-require.conf`. With `option-either-binding-names.conf`, expect fifteen diagnostics in `OptionEitherBindingNames.scala`. Expect two diagnostics by default or with `Test / scalafix NoDefaultParameters --check`.
