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

`OptionEitherBindingNames` requires `maybe` followed by an uppercase letter for `Option` bindings and `Result` at the end of `Either` bindings. It checks the semantic type, including inferred types. A fully generic extension receiver is exempt; ordinary parameters and receivers with a concrete type argument are not.

Use these names for optional facts and temporary library results. Model distinct business outcomes with named cases.

## NoPartialCaseClassDestructuring

`NoPartialCaseClassDestructuring` reports positional patterns for project-owned Scala 3 case classes when a field is ignored. A field counts as used if the pattern tests it or the guard or body references its binding. Standard-library patterns and custom extractors that do not return the matched case class are outside this check.

Bind the whole value and read the fields you need by name. Preserve any tests the original pattern performed.

## Release and verification

Publish releases with sbt `publish` to a Maven-layout directory, then commit the generated version directory and module metadata to the `scalafix-maven` branch. Do not replace published versions.

To verify a candidate, publish it to a temporary Maven repository and use it in the `smoke` build. Run `Test / scalafix --check` for assertions or set `Test / scalafixConfig` to select another configuration. Expect twelve diagnostics with `named-sentinel-arguments.conf`, five with `codec.conf`, seven with `either-cond.conf`, three with `simplified-for-yield.conf` and nine with `no-predef-require.conf`. With `option-either-binding-names.conf`, expect eight diagnostics in `OptionEitherBindingNames.scala`. Expect two diagnostics by default or with `Test / scalafix NoDefaultParameters --check`.
