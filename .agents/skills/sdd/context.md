# Living documentation

## Strategic purpose

Living docs give project knowledge that code search cannot give. This knowledge lets a reader change or operate the application safely.

High signal means that a fact changes a reader's decision. A short sentence without necessary information does not have signal.

| Document | Information |
| --- | --- |
| README | Orientation and entrypoints |
| Principles/conventions | Engineering rules and contribution practice |
| Design | Responsibility boundaries, system lifecycles, shared guarantees and decision reasons |
| Contracts | Provided OpenAPI references, consumed protocols, message exchanges and compatibility guarantees |
| Testing | Project risks, proof boundaries and gaps in isolated tests |
| Operations | Supported deployment, state ownership, safe recovery and monitoring gaps |
| Pipeline context | Pipeline design/testing/operations in `ci/specs/` |
| Proposal and linked spec design | Change criteria, reasons and reviewed model evolution |
| Ignored work state | Test mappings, commands, results, approvals and review evidence |
| Schemas/configuration/reference data | Representations, settings and catalog values |

## Admission test

Before a Doc Sync decision, find doc owners whose facts can change with responsibilities, lifecycles, contracts or operations.

Read those docs before a `None` decision.

Record the decision and its source evidence in work state. For `None`, include the examined owners and the evidence that their strategic knowledge is correct.

If the change keeps all doc owners' facts correct, record the source evidence for that conclusion.

A document without edits does not show that its content is correct.

For a proposed change, also record answers to these questions:

| Question | Required answer |
| --- | --- |
| What changed? | The incorrect strategic statement, or the missing project knowledge |
| Which reader decision changes? | A decision about design, integration, testing or operation |
| Why does it belong here? | The effect of missing knowledge and the information that code search or a different authority cannot give |
| What is the evidence? | Delivered behavior, a decision with approval or an applicable operating constraint |
| Where does it belong? | One owning section and the incorrect or duplicate text for removal |

- Use `None` as the Doc Sync default. Feature delivery alone is not sufficient for admission.
- Accept a delta only with delivered behavior and the admission evidence.
- Before a draft, read all docs whose facts can change, their factual sources and a diagram-first precedent.
- Start with responsibility, lifecycle and deployment diagrams. Add prose only for information that the diagrams do not give.
- Give project facts with the actor, condition and result. Reject general aspirations without an applicable project decision.
- Keep feature criteria in proposals and test-case inventories in work state.
- Keep implementation discovery in code search. Class catalogs and links to implementation symbols have no place in living docs.
- Give each fact one location across docs, diagrams and tables.
- For necessary detail, give a link to the owning protocol definition, schema, configuration or runbook.
- Keep safe procedures, recovery constraints and necessary contract interpretation. Strategic scope does not decrease safety obligations.
- Add a specialist doc only for necessary knowledge without an owner. Put its link at the reader's decision point.

## Contracts are protocols

- Show each provided and consumed boundary as a contract between peers. Include the correct protocol, such as HTTP/REST, SSE, WebSocket or filesystem.
- For provided HTTP APIs, use OpenAPI as the one contract for methods, paths, credentials, headers, payloads, errors and streams.
- If OpenAPI is missing, write the contract from its producers and consumers. For generated OpenAPI, use the project's generator.
- Keep generated contracts and clients read-only. After changes to source definitions, use the project's generator.
- In provided-boundary docs, give a link to that contract without a second endpoint or payload inventory.
- For consumed boundaries, show the correct protocol, endpoints, exchanged data and handoff. A protocol table is permitted.
- For streams, include the connection endpoint, direction, message meaning and disconnect/reconnect guarantees in the owning contract.
- Include admission, authentication, failure meaning, ordering, caching and compatibility when they change a consumer's decisions.
- Use JSON Schema for structured file exchanges where applicable. A Scala class or trait link is not a protocol definition.
- Give the exchange and state guarantees for local storage boundaries. A repository implementation name gives no database or filesystem compatibility guarantee.
- Record each protocol guarantee at one owner, without a second payload-field catalog. Put the business lifecycle in design.

## Authoring and closure

- In design, give system boundaries, important lifecycles and constraints. Include a tradeoff only when its reason and cost change a project decision.
- In contracts, show peers, protocol/endpoint, exchange, failure and compatibility semantics. Include exposed and consumed boundaries.
- In testing, give project risks and reasons for component, external-seam or integrated proof. Suite inventories and scenario results are not strategy.
- In operations, give startup, shutdown, isolation, exposure, deployment and recovery instructions.
- Include stored-state ownership, capacity limits and available monitoring.
- Record each available alert's severity, meaning and runbook.
- Give links to metric definitions with their operating meaning.
- Include only sections with different necessary information. Put supported behavior and recommendations in different categories.
- Examine docs together for reader decisions, duplicate content, incorrect facts and missing knowledge.
- For standard changes, record the owner that keeps or increases each previous obligation.
- Correct missing obligations before closure.
- Get maintainer approval before a weaker guardrail. Record its scope.
