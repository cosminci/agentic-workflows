# Scalafix custom rules

Consumers resolve `com.cosminci:scalafix-custom-rules_2.13` through `scalafixDependencies`. Set `scalafixResolvers` to the Maven repository at `https://raw.githubusercontent.com/cosminci/agentic-workflows/<artifact-commit>/`. Pin the full artifact commit and version. Select rules in the project's Scalafix configuration.

## SingleStepMap

`SingleStepMap` reports `for ... yield` with one named generator and at most one guard, or one wildcard generator without a guard. Consider `map` for an unguarded transformation, the source itself for an identity yield, or `collect` for a guarded transformation. Check that the replacement preserves type and behavior.

## MultilineEitherCond

`MultilineEitherCond` reports calls to `scala.util.Either.cond` that span multiple physical lines. It checks the call, not subsequent operations. Review what the condition and branches own before changing the code. Preserve lazy branch evaluation.

## OpaqueArguments

`OpaqueArguments` checks calls owned by the current root package when a call has multiple arguments. It reports a positional numeric zero, empty string, `None`, `Nil`, `Option.empty` or `()`. For `Either.cond`, it reports only positional `()`: use `right = ()` or `left = ()` to show which branch returns `Unit`. It does not check comprehension bindings or other library calls.

`DisableSyntax` and the formatter do not enforce named sentinel arguments. Compiler named-argument support provides the repair without another build mechanism.

Publish releases with sbt `publish` to a Maven-layout directory, then commit the generated version directory and module metadata to the `scalafix-maven` branch. Do not replace published versions.

To verify a candidate, publish it to a temporary Maven repository and use it in the `smoke` build. Run `Test / scalafix --check` for assertions or select another configuration with `--config` before `--check`. Expect two diagnostics by default, twelve with `opaque.conf`, five with `codec.conf`, seven with `either-cond.conf` and five with `single-step-map.conf`.
