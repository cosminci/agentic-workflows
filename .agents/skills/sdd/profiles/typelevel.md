# Typelevel

- Use `F[_]` for effectful algebras.
- Use context bounds and accessors for generic clock, identity and logging capabilities.
- Use `.pure[F]`, `.as` and Cats traversal syntax in addition to [shared Scala syntax](../conventions.md#syntax).
- When scala3future rejects colon syntax, use braces for traversal lambdas.
- When available, use the project's transformer syntax.
- Use `.map` for single-generator comprehensions with pure results.
- Use the ecosystem's resource-safe primitive for resource acquisition, use and release.
- Use [effect ownership](../principles.md#contracts-and-effects) for logging.
- Use named TypeScript imports with clear bindings.
- Obey the [Typelevel local quality contracts](../references/local-contracts.md#typelevel) for operation results, formatter limits, composition logging and UI precedents.
