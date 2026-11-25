# Scalafix custom rules

Consumers resolve `com.cosminci:scalafix-custom-rules_2.13` through `scalafixDependencies`. Set `scalafixResolvers` to the Maven repository at `https://raw.githubusercontent.com/cosminci/agentic-workflows/<artifact-commit>/`. Pin the full artifact commit and version. Select rules in the project's Scalafix configuration.

## OpaqueArguments

`OpaqueArguments` checks calls owned by the current root package when a call has multiple arguments. It reports an unnamed numeric zero, empty string, `None`, `Nil` or `Option.empty`. It ignores named arguments, nonempty values and domain-typed expressions. These sentinel arguments hide a parameter role and often reveal interchangeable primitive, collection or optional fields.

`DisableSyntax` and the formatter do not enforce named sentinel arguments. Compiler named-argument support provides the repair without another build mechanism.

Publish releases with sbt `publish` to a Maven-layout directory, then commit the generated version directory and module metadata to the `scalafix-maven` branch. Do not replace published versions.

To verify a candidate, publish it to a temporary Maven repository and run the `smoke` build with that repository in `scalafixResolvers` and the candidate in `scalafixDependencies`. Run `Test / scalafix --check` for the assertion fixtures, `Test / scalafix --config=opaque.conf --check` for opaque arguments and `Test / scalafix --config=codec.conf --check` for codec visibility. The intentionally invalid assertion, opaque-argument and codec fixtures must produce two, five and five errors, respectively.
