# Scalafix custom rules

Consumers resolve `com.cosminci:scalafix-custom-rules_2.13` through `scalafixDependencies`. Set `scalafixResolvers` to the Maven repository at `https://raw.githubusercontent.com/cosminci/agentic-workflows/<artifact-commit>/`. Pin the full artifact commit and version. Select rules in the project's Scalafix configuration.

## NoDefaultParameters

`NoDefaultParameters` reports default parameter values. Enable it in production, not in tests.

## NoPredefRequire

`NoPredefRequire` reports calls to both overloads of `scala.Predef.require`, including infix calls and calls through imports and aliases. Keep invariants in the types or boundaries that own them, and give callers an explicit failure path.

## SimplifiedForYield

`SimplifiedForYield` reports `for ... yield` with one named generator and no guard or binding. Use `map` for a pure transformation. For an identity yield, check that using the source directly preserves its type and behavior; Scala can omit the `map` call.

## MultilineEitherCond

`MultilineEitherCond` reports calls to `scala.util.Either.cond` that span multiple physical lines. It checks the call, not subsequent operations. Review what the condition and branches own before changing the code. Preserve lazy branch evaluation.

## NamedSentinelArguments

`NamedSentinelArguments` checks calls owned by the current root package when a call has multiple arguments. It reports a positional numeric zero, empty string, `None`, `Nil`, `Option.empty` or `()`. For `Either.cond`, it reports only positional `()`: use `right = ()` or `left = ()` to show which branch returns `Unit`. It does not check comprehension bindings or other library calls.

`DisableSyntax` and the formatter do not enforce named sentinel arguments. Compiler named-argument support provides the repair without another build mechanism.

## OptionEitherBindingNames

`OptionEitherBindingNames` requires `maybe` followed by an uppercase letter for `Option` bindings and `Result` at the end of `Either` bindings. It checks by-name parameters, transparent aliases, and inferred `Some`, `None`, `Left`, and `Right` types. A fully generic extension receiver is exempt; ordinary parameters and receivers with a concrete type argument are not.

Use these names for optional facts and temporary library results. Model distinct business outcomes with named cases.

## NoPartialCaseClassDestructuring

`NoPartialCaseClassDestructuring` reports positional patterns for project-owned Scala 3 case classes when a field is ignored. A field counts as used if the pattern tests it or a later expression in the case or comprehension references its binding. In `val` and `var` patterns, the rule reports explicit discards only. Standard-library patterns and custom extractors that do not return the matched case class are outside this check.

Bind the whole value and read the fields you need by name. Preserve any tests the original pattern performed.

## NoPositionalTupleSelectors

`NoPositionalTupleSelectors` reports Scala tuple selectors such as `._1` in inline callbacks, including callbacks returned by an argument block. Use a pattern with meaningful names for the fields you need. Wildcards in tuple patterns are allowed. Selectors outside inline callbacks and members of non-tuple types are outside this check.

## AdapterCodecVisibility

`AdapterCodecVisibility` reports Circe and Tapir codec members exposed outside their configured owner or package boundary. It checks codec subtypes and constructor fields, not ordinary method parameters. Shared owners can be nested when the boundary is inferred from `adapterPackages`.

## NoMultilineExpressionsInParentheses

`NoMultilineExpressionsInParentheses` replaces `MultilineParentheses`. It reports an expression or comprehension step that spans multiple lines inside `()`, including arguments at any position and tuple elements. Lists of single-line arguments, parameters, tuple elements, and destructuring patterns can wrap across lines. Line breaks inside string text do not count unless the string is a `sql` or `fr` interpolation.

Keep nested `Json.obj` fields in place and keep `.handle` calls written with braces or colon syntax in a `serverEndpoints` list defined by a `def` or `val`. The rule still checks expressions inside those entries. It reports other nested multiline constructors; name those values before passing them to the enclosing call.

Treat a finding as a design signal. Prefer Scala 3 colon syntax or braces for a multiline block argument. Extract a private method only when naming the work at the call site makes the code clearer.

## PreferCatsSyntax

`PreferCatsSyntax` reports selected factory calls in favor of value-first Cats syntax such as `.some`, `.asRight`, `.asLeft`, `.pure`, `.raiseError`, and `.liftTo`. Use `Option[A]` and `.some` if absence is valid, or use `A` directly if the value is required. Use `none[A]` for a literal null wrapped in `Option`. Keep `Option(value)` when it checks a value that may be null. Type a callback before wrapping it if the constructor previously supplied its type.

Use the project's `.eitherT` and `.lift` extensions on effects. Keep pure decisions outside `EitherT` and handle their results where effectful work starts.

Prefer `effect.delayBy(duration)` to a sleep followed by `*>` or `>>`, including Cats Effect typeclass syntax. When `>>` must defer construction of the right-hand effect, use `IO.defer(effect).delayBy(duration)` for `IO`. Keep standalone sleeps, `IO.delay`, `Sync[F].delay`, `IO.blocking`, and pattern matches as written.

## Release and verification

Bump the minor version for new rules or features. Reserve patch releases for fixes.

Publish releases with sbt `publish` to a Maven-layout directory, then commit the generated version directory and module metadata to the `scalafix-maven` branch. Do not replace published versions.

To verify a candidate, publish it to a temporary Maven repository and use it in the `smoke` build. Run `Test / scalafix --check` for assertions or set `Test / scalafixConfig` to select another configuration. Expect twelve diagnostics with `named-sentinel-arguments.conf`, nine with `codec.conf`, seven with `either-cond.conf`, three with `simplified-for-yield.conf` and nine with `no-predef-require.conf`. With `option-either-binding-names.conf`, expect fifteen diagnostics in `OptionEitherBindingNames.scala`. Expect two diagnostics by default or with `Test / scalafix NoDefaultParameters --check`.
