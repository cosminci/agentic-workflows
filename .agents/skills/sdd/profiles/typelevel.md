# Typelevel

- Use `F[_]` for effectful algebras.
- Use context bounds and accessors for generic clock, identity and logging capabilities.
- Use Cats syntax for construction and traversal.
- Use `.pure[F]`, `.some`, `.asRight`, `.asLeft` and `.as`, not equivalent wrapper constructors.
- Put pure result construction at the end of its chain.
- When scala3future rejects colon syntax, use braces for traversal lambdas.
- When available, use the project's transformer syntax.
- Use `.map` for single-generator comprehensions with pure results.
- Use the ecosystem's resource-safe primitive for resource acquisition, use and release.
- Use shared [effect ownership](../principles.md#contracts-and-effects) for logging.
- Capability access is not sufficient for a log at each call site.
- Use named TypeScript imports with clear bindings.
- Keep compiler/reactivity-mandatory bindings.
- Obey the [Typelevel local quality contracts](../references/local-contracts.md#typelevel) for operation results, formatter limits, composition logging and UI precedents.
- Keep project-mandatory outcome distinctions despite shared allowances for utilities without business policy.
