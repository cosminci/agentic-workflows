# Scalafix custom rules

Consumers resolve `com.cosminci:scalafix-custom-rules_2.13` through `scalafixDependencies`. Set `scalafixResolvers` to the Maven repository at `https://raw.githubusercontent.com/cosminci/agentic-workflows/<artifact-commit>/`. Pin the full artifact commit and version. Select rules in the project's Scalafix configuration.

Publish releases with sbt `publish` to a Maven-layout directory, then commit the generated version directory and module metadata to the `scalafix-maven` branch. Do not replace published versions.

To verify a candidate, publish it to a temporary Maven repository and run the `smoke` build with that repository in `scalafixResolvers` and the candidate in `scalafixDependencies`. Run `Test / scalafix --check` for the assertion fixtures and `Test / scalafix --config=codec.conf --check` for codec visibility. The intentionally invalid assertion and codec fixtures must produce two and five errors, respectively.
