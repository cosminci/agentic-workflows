# Typelevel
- Effectful algebras use `F[_]`.
- Generic clock, identity and logging capabilities use context bounds and accessors.
- Use Cats syntax for construction and traversal.
- Put pure result construction at the end of its chain.
- Use braces for traversal lambdas where scala3future rejects colon syntax.
- Use existing local transformer syntax where available.
- Single-generator comprehensions with pure results use `.map`.
- Resource acquisition, use and release use the ecosystem's resource-safe primitive.
- Use readable named TypeScript imports, preserving compiler/reactivity-required bindings.
