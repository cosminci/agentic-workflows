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
| Proposal / ignored work state | Feature criteria and reasons / test mappings, command results and review decisions |
| Schemas/configuration/reference data | Exact representations, inventories and values |

- Doc Sync lists changes to enduring system understanding. Integrate them into the owning section and remove obsolete explanation.
- Before writing, read the whole affected document and the source that establishes its current facts. Explain each concept once; link exact details instead of copying them.
- Keep feature walkthroughs in proposals, individual test results in work state and configuration values in configuration.
- Add a specialist document only for shared knowledge that has no existing owner. Link it from the document where a reader would need it; keep engineering instructions within the guidance budget.
- Check that the resulting document explains the current system without requiring feature history. Distinguish available behavior from recommendations.
- Describe package responsibilities, not class inventories. Operations records each available alert's severity, meaning and runbook location.

## Authoring templates

- Design: purpose, boundary, domain model, important lifecycles and interactions, constraints and tradeoffs.
- Contracts: every exposed or consumed boundary and its authoritative definition. Explain only semantics not already owned by design or schemas.
- Testing: which risks each test boundary addresses, suite categories and any integrated or manual checks needed. Configuration owns thresholds; work state owns individual results.
- Operations: startup and shutdown, isolation, network exposure, stored-state ownership, deployment layout, logs, health, monitoring and recovery. Link configuration and explain important gaps or capacity limits.
- Include only relevant sections. Diagrams explain important transitions or ordering; prose adds guarantees the diagram does not show.
