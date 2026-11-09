# Fundamental engineering rules

These eight rules are mandatory for all work in each consuming repository. SDD, profiles, local guidance and reviews must include these obligations.

More detailed rules can increase these obligations. They cannot decrease them or give exceptions.

## Purpose

Keep cognitive load and maintenance risk low. Use clear domain boundaries for extension and redesign.

Add complexity only for delivered value.

## 1. Ports & Adapters

- Isolate core business logic from infrastructure through ports for all external dependencies: databases, APIs and filesystems.
- Put ports near their consuming business logic.
- Use domain terms for ports, not external-system terms.
- Isolate concrete adapters from the core. Put implementation bindings in composition.
- Use unit tests for all business logic with substituted ports.
- Use integration tests for adapters against their external boundaries.

## 2. Domain Driven Design

- Use strong types and constrained structures for business concepts.
- When necessary, use nonempty collections and positive values.
- Use types and exhaustive control flow that exclude invalid states.
- Keep invalid combinations out of optional or nullable fields.
- Domain contains types and validity constraints. Usecases contain business behavior.
- Use the owning usecase and substituted dependencies for business-rule tests. Domain types have no test suites.
- Use the same business vocabulary in code, documentation and communication.

## 3. Fractal Design

- Use cohesive design at all scales, from the application entrypoint to each method.
- Give each component, class and method one responsibility and one reason for change.
- Keep statements in a method at the same conceptual or business abstraction level.
- Isolate orchestration from implementation detail.
- Keep methods short, typically 7-10 lines.
- Examine larger units for design defects, not format changes for a specified count.
- Give readers a clear high-level flow with clear paths to lower-level detail.

## 4. Anti-Corruption Layers

- Convert external structures, terminology and semantics to domain models before business logic receives them.
- Isolate external communication representations from internal domain representations.
- Keep external-model dependencies out of the core model.
- Put external interaction and representation translation in adapters. Validation gives domain-valid inputs.
- Make these responsibilities clear without different classes for conversion steps.
- Use a declarative codec when translation does not make a DTO wrapper necessary.

## 5. Indirection Layers

- Use interfaces for all interactions between business components.
- Pure, stateless, side-effect-free utilities are the only exception.
- Give each interface a domain contract with only its caller's necessary work.
- Inject business collaborators through these interfaces for isolated tests.
- Keep responsibilities and dependencies clear through interfaces without implementation detail.
- Give each interface one cohesive abstraction.

## 6. Strict Build Guardrails

- Keep 100% test coverage as a mandatory build criterion.
- Before more test machinery, examine tests that are not easy for design defects.
- Use static analysis for all applicable defect classes with strict rules.
- Reject builds with warnings or issues. Keep zero warnings.
- When available, use strict compiler settings for unsafe constructs, unused code/imports and accidental numeric widening.
- Use automated dependency updates for CVEs, deprecations and end-of-life risks.
- Guardrails help modularity, quality and development speed. A metric without errors does not show correct design.

## 7. Keeping Complexity in Check

- Give a delivered-value reason for complexity.
- Compare applicable alternatives with the smallest number of assumptions and components.
- Examine a much smaller design for the same value.
- Get maintainer agreement before a scope decrease.
- Before a spec, record what to keep, change, add or divide, with reasons.
- Examine the decision again when evidence changes.
- Examine implementations larger than a screenful and suites approximately three times their production size.
- Use testing difficulty as a design signal.
- Put new responsibilities at their owners in the change that makes them necessary.
- Record reasons for larger cohesive units.
- Correct design defects in the changed responsibilities and their consumers.

## 8. Code Expresses Intent

- Give intent through domain-fact names, strong types, clear constructs and cohesive modules.
- Before a comment, examine the usecase, API, model, responsibility boundary and package structure for a necessary correction.
- Keep related behavior together.
- When different caller behavior is a bug, use the same knowledge owner.
- Almost the same code is not sufficient for that decision.
- Keep decision reasons concise in the owning docs.
- Use comments only for constraints that code cannot express. Keep all project comment obligations.

## Enforcement

Read these rules before investigation, proposals, implementation or review.

Examine all eight rules against the changed work and its consumers. Record findings with source evidence.

Language and task type change applicability, not authority.

Every quality gate, guardrail and linter is a diagnostic aid. Most detect symptoms, not their design causes.

For each reported symptom, find its cause before repair selection.

Examine responsibility boundaries, contracts, dependencies and valid states. Correct the cause at its owner and dependent callers.

A format change, wrapper, suppression or smaller test suite does not correct a design defect.

If the evidence shows no design defect, record that evidence. Keep mandatory gates and scoped approval rules.

Correct missing enforcement without a weaker standard. Tests without errors, local precedent and feature approval do not show compliance.

Keep all previous safety, architecture, testing and comment obligations. Include project obligations that increase them.
