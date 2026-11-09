# Living documentation

| Document | What it owns |
| --- | --- |
| README | Orientation and entrypoints |
| Principles/conventions | Engineering rules and contribution practice |
| Design | Domain concepts, responsibilities, lifecycles, interactions and shared guarantees |
| Contracts | Links to authoritative boundary definitions and their necessary interpretation |
| Testing | Risks, the tests that address them and suite naming |
| Operations | Startup, deployment layout, stored state, recovery and available monitoring |
| Pipeline context | Pipeline design/testing/operations in `ci/specs/` |
| Proposal and linked spec design / ignored work state | Feature criteria, reasons and reviewed domain/use-case/port evolution / test mappings, command results and approval evidence |
| Schemas/configuration/reference data | Exact representations, inventories and values |

- Doc Sync defaults to `None`. Propose an edit only when delivery makes an existing strategic statement false or leaves necessary strategic understanding missing: purpose, responsibilities, interactions, lifecycles or shared guarantees and their decision-relevant rationale. Identify that statement or missing understanding before drafting; shipping a feature alone does not qualify.
- Integrate qualifying changes into the owning section and remove obsolete explanation. A Doc Sync entry is a proposal to verify against delivered behavior, not an obligation to add text; discard entries that no longer qualify.
- Before writing, read all affected living documents, the source establishing their facts and a current diagram-first precedent. Draft responsibility, lifecycle and deployment diagrams first. Prose adds only invariants, boundary interpretation or safe procedures that the diagrams and linked sources do not supply.
- When evolving standards, map each replaced obligation to its retained or stronger rule; restore lost scope or evidence requirements before completion. Weakening a guardrail requires explicit scoped maintainer approval.
- Give each fact one location across prose, diagrams and tables. Design owns flows and invariants; contracts links boundary definitions; testing maps concrete risks to proof owners; operations gives startup, recovery and monitoring instructions. Code, schemas and configuration own exact definitions and values.
- Keep a sentence only when it answers a concrete question about this application that its diagram or linked authority cannot answer. Name the responsible actor, condition and consequence; replace general aspirations with current, source-backed facts.
- Reject misplaced detail rather than merely shortening it. Keep accurate operational instructions and other existing document-owned obligations; strategic abstraction does not excuse removing necessary recovery, safety or contract interpretation.
- Contracts links authoritative definitions and explains only boundary semantics necessary to interpret them. Implementation names, parsing algorithms, character-handling rules and field-state catalogs stay in their authoritative source; link that source where needed. Document a boundary guarantee only when its interpretation is necessary and not already explained by the linked definition.
- Keep feature walkthroughs in proposals, individual test results in work state and configuration values in configuration.
- Add a specialist document only for shared knowledge that has no existing owner. Link it from the document where a reader would need it.
- Review the complete documents together after editing. Remove repeated facts, prose that narrates diagrams, obsolete explanations and sections without useful content. Check that a new reader can understand current operation without feature history; distinguish available behavior from recommendations.
- Describe package responsibilities, not class inventories. Operations records each available alert's severity, meaning and runbook location.

## Authoring templates

- Design: purpose, boundary, domain model, important lifecycles and interactions, constraints and tradeoffs.
- Contracts: every exposed or consumed boundary and its authoritative definition. Explain only semantics not already owned by design or schemas.
- Testing: which risks each test boundary addresses, suite categories and any integrated or manual checks needed. Configuration owns thresholds; work state owns individual results.
- Operations: startup and shutdown, isolation, network exposure, stored-state ownership, deployment layout, logs, health, monitoring and recovery. Link configuration and explain important gaps or capacity limits.
- Include only relevant sections. Diagrams explain important transitions or ordering; prose adds guarantees the diagram does not show.
