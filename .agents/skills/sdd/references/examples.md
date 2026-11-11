# Concrete quality examples

These are illustrative cases, not rule definitions. Read only the changed boundary after its owner from [review evidence](quality-gates.md).

## Meaningful intermediates and whitespace

`findCreationConflict` returns an optional conflict, while `hasCreationConflict` returns a Boolean. `maybeDescription` shows absence.

`orderedChapters` and `wateredPlantOperations` give fact names. `processed` and `watered` conceal those facts.

Use the [named-argument rule](../conventions.md#syntax): `maybeNote = None`, `date = Instant.parse(...)`, `share = 100`, `chapters = Nil`.

Different `PlantId`/`AlbumId` types show roles without labels. A raw string or list cannot show those roles.

### A bulky constructor argument

`AcceptedIntent` expanded across lines because metadata contained a refinement and an additional constructor.

The [syntax owner](../conventions.md#syntax) applies to both the bulky value and absent fields.

A factory alias only conceals the same construction.

```scala
val readyMetadata = ImportReadyMetadata(metadata.refineUnsafe[ImportReadyMetadata.Valid])
val intent = AcceptedIntent(candidate, readyMetadata, maybeRuntimeMinutes = None, maybeCover = None, original = measurements, maybeRecording = None)
```

An assertion with one nested result illustrates [assertion layout](../conventions.md#suites-and-assertions).

```scala
val hasCreationConflict = existingNames.contains(requestedName)
val expectedResult = CreateAlbum.Result.Conflict(requestedName)

val actualResult = albums.create(requestedName)

assertEquals(actualResult, expectedResult)
```

The condition gives a business fact and the expected value gives the full outcome. A `makeConflict()` wrapper adds no responsibility.

This dependent sequence illustrates [sequencing](../conventions.md#sequencing).

```scala
for
  existingAlbum <- albums.find(albumId)
  savedAlbum <- albums.save(updatedAlbum(existingAlbum))
yield savedAlbum
```

If `updatedAlbum` conceals an independent lifecycle rule, the example needs an ownership correction, not whitespace.

For Typelevel, `albums.find(albumId).map(renderAlbum)` gives one effect and a pure result. Use a comprehension for multiple dependent effects.

`.pure[F]` can lift a branch in an effectful workflow. It is not a reason for an effectful API on a pure classifier.

Under the [Typelevel profile](../profiles/typelevel.md), scala3future can require `traverse_ { item => ... }` rather than colon syntax.

## Cohesive APIs and states

The album's deciding usecase puts its start spread first during aggregate construction. A consumer uses this domain-valid port guarantee without duplicate validation.

Audiobookshelf item IDs, source collection IDs and import request IDs have different identities. PlantId, OperationId and in-range percentages exclude primitive misuse.

A qBittorrent torrent converts to a source collection. An ABS library item is not a file.

```scala
enum RecordingIdentity:
  case Confirmed(itemId: ItemId)
  case NeedsReview(fieldsInDoubt: NonEmptyList[FieldEvidence])
```

This type excludes confirmation without an ID and keeps review evidence. A domain test suite is not necessary.

A request type groups facts for one operation. `Request(album, services, flags, settings)` conceals unrelated inputs and lifetimes.

`LookupResult` with unrelated failures gives the caller more than the identity and measurements it needs.

A persistence helper returns saved data. Its caller assembles the workflow result.

The following operation illustrates [module/result conventions](../conventions.md#modules-and-results).

```scala
trait UserRepository:
  def find(userId: UserId): UserRepository.FindResult

object UserRepository:
  def make(storage: Storage): UserRepository = new LiveUserRepository(storage)

  private final class LiveUserRepository(storage: Storage) extends UserRepository:
    def find(userId: UserId): FindResult = storage.find(userId)

  enum FindResult:
    case Found(user: User)
    case Missing
    case Failed(cause: Failure)

  enum Failure:
    case Unavailable
```

This direct-style example keeps the full implementation name, factory and results together. A Typelevel variant uses its profile's effects.

## Library integration

In http4s/Circe/Iron, use middleware for shared HTTP concerns and declarative decoding/refinement integration for representation conversion.

When its quantity model gives the necessary units, use Squants.

The [library policy](../principles.md#libraries-and-tooling) determines whether custom conversion has a necessary role.

## Intelligible table scenarios

`s"should return ${WateringAttention.Current} ..."` uses the domain variant to prevent stale wording after a rename.

This table illustrates [suite setup](../conventions.md#suites-and-assertions):

```scala
final case class Scenario(attention: WateringAttention, expectsWarning: Boolean)

val scenarios = List(
  Scenario(attention = WateringAttention.Current, expectsWarning = false),
  Scenario(attention = WateringAttention.Overdue, expectsWarning = true)
)

scenarios.foreach { case Scenario(attention, expectsWarning) =>
  test(s"should report watering warning for $attention") {
    val plant = buildPlant(attention)
    val expectedResult = AttentionView(plant.id, attention, expectsWarning = expectsWarning)

    val actualResult = attentionView.show(plant)

    assertEquals(actualResult, expectedResult)
  }
}
```

`(true, false, true)` and a named-tuple annotation do not show the construction roles that `Scenario` names here.

Give fixed responses directly: `buildX(response = scenarioResponse)`.

For call/effect assertions, add Refs: `buildX(refs, response = scenarioResponse)`.

The builder returns only the component. Each scenario contains its local facts and full expected outcome.

`buildX(find = id => arrangedAlbums.get(id))` can recreate repository behavior.

Use `buildX(findResult = arrangedResult)`. Record the requested ID in Refs.

A controlled pause or repeated-call scenario can make an effectful substitute necessary.

The pause is an example of a scenario reason for an effectful substitute.

### Named setup controls

`(buildOwner(), Deferred[IO, Unit], expected).tupled` mixes construction, synchronization and assertion data. Its reader must examine machinery for the tuple's meaning.

Named local allocations make these roles visible:

```scala
val expectedResult = ImportResult.Completed(original)
for
  dispatchEntered <- Deferred[IO, Unit]
  releaseDispatch <- Deferred[IO, Unit]
  owner <- buildOwner(dispatchEntered = dispatchEntered, releaseDispatch = releaseDispatch)
  actualResult <- owner.execute(candidate)
yield assertEquals(actualResult, expectedResult)
```

`Map[ReadResult, ImportProblem]` gives one association. It is not a reason for `(owner, input, release, expected)` or a fixture record with unrelated values.

For checked values versus underlying expected catalogs, use [refinement conventions](../conventions.md#codecs-and-refinements).

## Strategic contracts

These cases illustrate [protocol ownership](../context.md#contracts-are-protocols).

The Audiobookshelf OpenAPI defines `GET /api/audiobooks` and its JSON/SSE `Accept` selection. It includes payloads, `subscribed`/`changed` framing and reconnect/read guarantees.

A consumed-protocol table can give endpoints, exchanged data and handoff. Its facts must come from the actual producer and consumer.

An `EventsApi.scala` link gives no protocol contract. A WebSocket contract is incorrect when the service emits SSE.


## Frontend behavior

A disabled Save control gives its reason through `aria-describedby` from the same predicate. Duplicate predicates can give a reason inconsistent with disabling.

These cases illustrate [frontend conventions](../conventions.md#solidjs--typescript) and [presentation guarantees](../principles.md#presentation).

A chapter rename shows its authoritative saved name, not a title rebuilt from an ID.

After a dialog failure or collapse, keep focus on a persistent control/status.

A toolbar groups related thin-stroke actions. Each action has hover text and an equivalent accessible name.

`/* v8 ignore next */` is an example directive, not proof that its branch is unreachable.

## Review replays

Use [the review procedure](../reviewer.md#reported-misses) to run these candidate/control pairs.

`Discuss` means the fixture lacks enough evidence for a verdict. Its next step is source examination, not an automatic block or pass.

| Candidate | Mandatory verdict | Rule and effect |
| --- | --- | --- |
| A new endpoint rereads/normalizes data from a prepared result | Block | Continuity: two acquisition/normalization owners can disagree. |
| Two consumers project necessary facts from one sufficient prepared snapshot | Pass | Shared knowledge keeps one authority. A new caller is not sufficient for a layer. |
| One client contains independently variable book-search and exact-lookup rules | Block | Independent policies change together without necessity. |
| Thin book-search/exact-lookup wrappers delegate those policies to that client | Block | Wrappers do not change ownership. The same coupling stays. |
| Different ports contain independently variable external search and exact lookup | Pass | Different responsibilities and failure contracts stay local. |
| An additional adapter only coordinates injected application ports and claims a seam suite | Block | Internal collaboration is an application operation, not an external seam. |
| An adapter makes multiple external calls for one port | Pass | Fetch/decode/snapshot assembly stays at the supplying boundary. |
| A database adapter selects the user's eligible chapter or business fallback before a write | Block | Usecase ownership: storage mechanics select business policy. |
| A database adapter validates stored representation and atomically writes the supplied decision | Pass | Representation integrity and persistence stay at the boundary. Selection stays in the usecase. |
| Unrelated flows use mode flags or optional aggregate fields | Block | Cohesion/valid states: the model conceals dependencies and invalid combinations. |
| A per-item operation receives unnecessary batch/scope | Block | Caller-shaped API: no consumed fact or decision makes global work necessary. |
| A single operation uses an Either wrap and immediate unwrap | Block in direct style | There is no dependent composition. The public ADT gives the outcome. |
| Dependent direct-style steps use Either with lazy adjacent compensation | Pass | Sequencing is linear with explicit primary and compensation failures. |
| A formatter accepts a nested expected result and assertion across a screen | Discuss | Examine construction for a different responsibility or unclear operands in the full outcome. |
| `AcceptedIntent(candidate, ImportReadyMetadata(metadata.refineUnsafe[Valid]), None, None, measurements, None)` | Block | Named arguments and bulky operands: absent fields are ambiguous and one nested value needlessly expands the constructor. |
| Named `readyMetadata` and named absent fields in a compact `AcceptedIntent` call | Pass | The full refined metadata and intent are clear. Each absent field has a role. |
| Setup returns `(owner, input, release, expected)` or unlabeled multi-field rows | Block | Semantic setup: a reader must read machinery and assertions for fixture roles. |
| Named local inputs/expectations, named controls and a builder that returns only the owner | Pass | Setup roles are clear in the scenario, without a fixture bundle. |
| A large cohesive unit has same-level statements and a recorded reason | Pass | The reviewer examines approximate size without an automatic exception. |
| A trait gives primitive operations and a workflow that calls them | Block | API cohesion: orchestration and its building blocks are not sibling operations. |
| A cohesive trait gives complementary same-level operations to an injected caller | Pass | Composition keeps a small vocabulary without artificial layers or one-method traits. |
| A new near-duplicate API accepts a variant that the previous contract cannot express | Block | Contract evolution: overlapping paths can diverge. Examine the original API with its callers. |
| A contract changes with its callers and keeps necessary guarantees | Pass | The building block changes without additional competing APIs. |
| A shallow method has many parameters or a comprehension statement spans lines | Discuss | Find the responsibility or abstraction-level defect. A cohesive same-level operation can stay unchanged. |
| A 40-line owner has a 160-line suite | Discuss | Examine scenario responsibilities, dependency isolation and fixtures. Size without other evidence does not show a defect. |
| That suite uses the real planner and duplicates its policy cases | Block | Test isolation: the suite exercises owner and planner despite their injectable boundary. |
| The suite substitutes the planner with cases for only the owner's decisions, failures and cancellation | Pass | Full contract proof can give a reason for the kept size without additional responsibility. |
| A pass-through helper conceals the same wide call as a claimed design repair | Block | Parameter relocation keeps the demonstrated defect. |
| Two substantial assertion operands stay inline in a compact multiline assertion | Pass | Two extracted values only for one fewer line add no clarity. |
| Mixed generators and pure bindings have a demonstrated same-level reason | Pass | Same-level data preparation makes no new service or effect-lifted pure helper necessary. |
| Nested comprehensions mix acquisition, business selection and external encoding | Discuss | Examine ownership and conceptual depth. Less indentation does not correct a mixed-responsibility defect. |
| Fixed mock responses use lambdas that rebuild dependency results from recorded input | Block | Test design: response selection and observation are mixed. The caller's suite includes dependency behavior. |
| Fixed mock values use Refs only for requested input/effects | Pass | Dependency responses and explicit observations stay independent. |
| An effectful substitute waits on a scenario release control and returns a fixed value | Pass | The scenario shows the owner's cancellation/order guarantee without dependency policy. |
| A new suite has a domain type or result enum as its owner | Block | Types have no behavior suite. Usecase/port traits have behavior proof. |
| One usecase trait has two suites divided by feature history | Block | Contract proof is fragmented. Merge scenarios into the trait's single suite. |
| One owning trait suite includes all its acceptance behaviors | Pass | A reader gets one coherent contract with substituted collaborators. |
| A home-NAS deployment adds coordination without a supported requirement | Block | The operating cost has no necessary guarantee. |
| Manual JSON/refinement plumbing duplicates available library integration | Block | Redundant mechanics add maintenance and error-handling paths. |
| An index has no access pattern or plan, and redundant stored state stays | Discuss | Examine schema ownership and query cost. Index count does not show optimal design. |
| Indexes agree with observed joins/filters/order, plan evidence and acceptable write costs | Pass | Query mechanics serve the lifecycle without speculative machinery. |
| A repair adds a status flag for two mutable copies of one fact | Block | Competing authorities stay, with more possible invalid states. |
| A repair removes duplicate state at its owner with tests for caller invariants | Pass | The repair removes the cause without a new guard around it. |
| Usecases and adapters log the same business outcome | Discuss | Examine effect ownership and fragmented orchestration. A log-format change is not the design repair. |
| A usecase logs its business result with different owners for transport and startup diagnostics | Pass | Effect categories have owners at the necessary abstraction levels. |
| Source-accurate acceptance checklist recast as design prose | Block | Living-doc altitude: feature walkthroughs are not strategic system context. |
| A lifecycle or recovery change uses `Doc Sync: None` without examination of affected docs | Block | A missing edit can keep incorrect strategic knowledge outside the candidate-file manifest. |
| `Doc Sync: None` gives examined owners and evidence of correct strategic statements | Pass | Source-backed no-change decisions prevent stale docs and unnecessary feature prose. |
| A paragraph or different document duplicates a diagram's dispatch/checkpoint order | Block | One fact has two owners. Clearer wording does not remove the duplicate. |
| A lifecycle diagram has an invariant for the uncertain effect that prevents cleanup | Pass | The invariant answers a safety question that the arrows do not answer. |
| Individual test scenarios/fixtures become testing strategy | Block | Testing contains risks and proof boundaries, not a scenario catalog. |
| Testing strategy contains links to each suite and summaries of its cases | Block | Suite links give no missing strategic knowledge. The inventory can diverge from code. |
| A contracts table gives Scala adapters or traits, not methods, paths or stream exchanges | Block | Consumers cannot find the supported protocol. Implementation discovery adds no strategic value. |
| OpenAPI contains HTTP paths, payloads, JSON/SSE selection, framing and reconnect/read guarantees. Provided-boundary docs give its link | Pass | Clients and proxies get one wire contract without a second inventory. |
| HTTP paths and payloads exist only in prose because OpenAPI is missing | Block | Write the standard contract. A prose table is not its replacement. |
| A contract adds WebSocket transport for notifications, but the service emits SSE | Block | Consumers get a protocol that the application does not give. |
| A consumed-protocol table gives correct endpoints, exchanged data and handoff | Pass | The consumer gets the necessary integration facts. |
| Copied schema/catalog/configuration inventories become context | Block | Authoritative definitions get a stale duplicate owner. |
| A system-level lifecycle/sequence diagram includes essential consistency guarantees | Pass | Design gives long-term relationships and decisions. |
| A risk-to-boundary strategy gives the limits of component proof | Pass | Testing gives the confidence model without copied scenarios. |
| Source evidence gives supported startup/recovery and monitoring gaps | Pass | Operations gives available capability and safe intervention. |
| A project design doc says “Use clean architecture” without a project constraint or decision | Block | A general aspiration does not change a concrete reader decision. |
| A backup procedure stops the sole writer for the database and referenced media | Pass | The instruction gives a recovery constraint that isolated file definitions cannot give. |
| A short spec has no failure outcome or enforcing owner | Block | Signal is not word count. The necessary contract is not full. |

## Provenance

Sources came from photography `8d2014b`, home-automation `6579ce0` and plant-journal `32ec535`.

Sources included AGENTS, contribution/design rules, SDD execution/review/templates, five checklists, living-doc templates and photography UX-PATTERNS.

Home-automation domain AGENTS and Audiobookshelf contribution/design sources keep scoped safety and operational authority.

Photography `ec6a0ba` rebuilt living docs.

`edd7675` isolated test owners and collaborators. `83002ee` corrected Scala sequencing and call shape.

These commits give reported failure classes, not perfect precedents.

Previous rounds, copied checklists, universal spikes, fixed slicing and verbatim Doc Sync were workflow mechanisms, not quality obligations.

Current obligations belong to the owners in [SDD routing](../SKILL.md), not these historical sources.
