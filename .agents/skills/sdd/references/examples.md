# Concrete quality examples

Read for implementation and review of the corresponding surface under [quality gates](quality-gates.md). Examples show why a check matters; assess actual ownership and behavior, not symbol spelling. They preserve historical names and add concrete bad/good pairs for previously generic instructions.

## Meaningful intermediates and whitespace

`findCreationConflict` returns an optional conflict; `hasCreationConflict` answers a Boolean. `maybeDescription` and `maybeNickname: Option[Nickname]` expose absence. `orderedChapters` and `wateredPlantOperations` expose the established fact; `processed` and `watered` conceal it.

Name arguments whose values hide their role: `maybeNote = None`, `date = Instant.parse(...)`, `share = 100`, `chapters = Nil`. Self-describing `PlantId`/`AlbumId` values may remain positional. This applies equally to a private helper, builder and captured collaborator call.

```scala
val hasCreationConflict = existingNames.contains(requestedName)
val expectedResult = CreateAlbum.Result.Conflict(requestedName)

val actualResult = albums.create(requestedName)

assertEquals(actualResult, expectedResult)
```

The condition explains a business fact and the complete expected outcome explains the assertion. Extracting `makeConflict()` just to shorten a line adds no owned responsibility. Arrange/Act/Assert blank lines separate phases, not effect generators.

```scala
for
  existingAlbum <- albums.find(albumId)
  savedAlbum <- albums.save(updatedAlbum(existingAlbum))
yield savedAlbum
```

Keep dependent generators contiguous. If `updatedAlbum` hides an independently changing lifecycle rule, move that rule to its deciding operation; if it is a cohesive same-level mapping, the named operation keeps the flow legible. A multiline call first prompts API/cohesion inspection, not a wrapper or forced line.

For Typelevel, `albums.find(albumId).map(renderAlbum)` expresses one effect followed by a pure result. Multiple dependent effects use a comprehension. `.pure[F]` legitimately lifts a branch in an effectful workflow; a pure classifier's entire API does not become effectful merely because a caller can lift it.

Name a bulky SQL expression outside sequencing only after checking its owner; name a nested repository dependency because it owns persistence, not an incidental argument to meet 150 columns. Multiline lambdas use braces/colon; scala3future traversal uses `traverse_ { item => ... }`, not `traverse_: item =>`.

## Cohesive APIs and states

An album's first spread must be its start spread: aggregate construction enforces that cross-value rule in the deciding use case. A port supplies domain-valid albums; a consumer does not check the start spread again.

Audiobookshelf item IDs, source collection IDs and import request IDs represent distinct identities, not interchangeable strings. PlantId, OperationId and in-range percentages exclude primitive misuse. A qBittorrent torrent is translated to a source collection; an ABS library item is not a file.

```scala
enum RecordingIdentity:
  case Confirmed(itemId: ItemId)
  case NeedsReview(fieldsInDoubt: NonEmptyList[FieldEvidence])
```

This prevents `confirmed = true` with no ID and preserves evidence for review. A request type groups facts used by one operation; `Request(album, services, flags, settings)` merely hides unrelated inputs and lifetimes. More than five fields prompts the sibling/group/ADT check, not automatic splitting of cohesive facts.

Pass `itemId` and required measurements to an operation that uses them, not `LookupResult` with unrelated failure branches. A persistence-step helper returns its own saved data; only the caller constructs the workflow's public result. A successful no-op has its own state instead of being mislabeled an error to short-circuit Either.

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

The naming is `LiveUserRepository`, never the truncated `LiveUser`. The trait/factory/result belong together; the example's storage contract must actually provide this operation's domain-valid outcomes. Apply the effect wrapper/capability injection of the selected profile rather than copying direct style into Typelevel.

## Library integration

In an http4s/Circe/Iron stack, middleware owns shared HTTP concerns; entity decoding, codecs and refinement integration own representation conversion. Application code supplies business policy and preserves the boundary's error contract.

## Intelligible table scenarios

The historical `s"should return ${WateringAttention.Current} ..."` names the actual variant, preventing stale hard-coded wording after a rename. Tables preserve that property and make each scenario's roles explicit:

```scala
final case class Scenario(attention: WateringAttention, expectsWarning: Boolean)

val scenarios = List(
  Scenario(attention = WateringAttention.Current, expectsWarning = false),
  Scenario(attention = WateringAttention.Overdue, expectsWarning = true)
)

scenarios.foreach { case Scenario(attention, expectsWarning) =>
  test(s"should report watering warning for $attention") {
    val plant = buildPlant(attention)
    val expectedResult = AttentionView(plant.id, attention, expectsWarning)

    val actualResult = attentionView.show(plant)

    assertEquals(actualResult, expectedResult)
  }
}
```

These are illustrative role-labeled rows; use the repository's actual scenario type/builder. An unlabeled `(true, false, true)` matrix cannot tell the reviewer which behavior each flag controls. A suite-local Scenario record makes tuple roles explicit; assert named call observations rather than positional flag sequences.

Use `buildX(refs, scenarioResponse)` only when calls/effects are asserted; otherwise `buildX()` suffices. Refs observes calls, never supplies stub responses. The builder returns only the component, not `(component, fixtures, expectedResults)`; each scenario owns its local domain facts and expected full outcome.

Construct domain-valued inputs and substituted port responses with checked refinements before Act. Reuse those values in a whole-domain expected result. A standalone expected text catalog uses underlying strings and safely widens actual values instead of repeating autoRefine at every literal; rejection scenarios still prove invalid input is rejected.

## Frontend behavior

A disabled Save control displays its reason and links it with aria-describedby from the same validation predicate; duplicating the rule can make the explanation disagree with disabling. Pure validation tables test the predicate directly; component scenarios exercise the accessible Save control and feedback.

A chapter rename renders its authoritative saved name, not a title rebuilt from an ID. A failed dialog submission retains focus on a persistent control/status; collapsing editing does not drop focus to the document. A toolbar keeps thin-stroke related actions grouped and gives each hover text plus an equivalent accessible name.

For keyed chapter identity use For; for positional primitive page-number rows use Index; Show owns conditional rendering. Keyboard deletion belongs to its interaction and must not consume text-entry/native-control keys. A genuinely unreachable guarded callback can use `/* v8 ignore next */` with exact proof; a reachable failure is tested through a real event, not excluded.

## Review replays

For every changed review gate, record the actual candidate, result, rule and consequence. `Discuss` requires design analysis, then a defect-backed `Block` or justified `Pass`; a signal alone cannot determine the verdict.

| Candidate | Required verdict | Rule and concrete consequence |
| --- | --- | --- |
| New endpoint rereads/normalizes data already in a prepared result | Block | Continuity: two acquisition/normalization owners can disagree. |
| Two consumers project required facts from one sufficient prepared snapshot | Pass | Shared knowledge retains one authority; a new caller alone adds no layer. |
| One client centralizes independent book-search and exact-lookup rules | Block | Independent policies evolve together without necessity. |
| Thin book-search/exact-lookup wrappers delegate those policies to that client | Block | Wrappers do not move ownership; the same coupling remains. |
| Separate ports own independently changing external search and exact lookup | Pass | Distinct responsibilities and failure contracts remain local. |
| Extra adapter only coordinates our injected ports and claims a seam suite | Block | Internal collaboration is an application operation, not a real external seam. |
| Existing adapter makes several external calls to fulfill one port | Pass | Fetch/decode/snapshot assembly stays at the supplying boundary. |
| Unrelated flows combined by mode flags or optional aggregate fields | Block | Cohesion/valid states: dependencies and invalid combinations are hidden. |
| Unnecessary batch/scope added to a per-item operation | Block | Caller-shaped API: no consumed fact or decision justifies global work. |
| Single operation wrapped in Either then immediately unwrapped | Block in direct style | No dependent composition; the public ADT already expresses the outcome. |
| Dependent direct-style steps adapted to Either with lazy adjacent compensation | Pass | Sequencing is linear and both primary/compensation failures remain explicit. |
| Formatter passes but nested expected result and assertion span a screen | Discuss | Determine whether construction hides a distinct responsibility or meaningful operands would clarify the complete outcome. |
| Large cohesive unit with same-level statements and recorded reason | Pass | Approximate size signal is inspected, not an automatic exception. |
| Trait exposes primitive operations alongside a workflow calling those operations | Block | API cohesion: orchestration and its building blocks are not sibling operations. |
| Cohesive trait exposes complementary same-level operations, composed by an injected caller | Pass | Composition preserves a small vocabulary without imposing artificial layers or one-method traits. |
| New near-duplicate API handles a variant the existing contract cannot express | Block | Contract evolution: overlapping paths can drift; reassess the original API and migrate its callers. |
| Existing contract evolves with its callers while preserving required guarantees | Pass | Better understanding improves the building block instead of multiplying competing APIs. |
| Shallow method has many parameters or a comprehension statement spans lines | Discuss | Identify the responsibility or abstraction-level defect; a cohesive same-level operation may remain unchanged. |
| Pass-through helper merely hides the same wide call and claims to resolve its design defect | Block | Parameter relocation leaves the identified defect unresolved. |
| Two substantial assertion operands remain inline in a compact multiline assertion | Pass | Readability: extracting both solely to save one line adds no clarity. |
| Mixed generators and pure bindings have a demonstrated same-level cohesive reason | Pass | Same-level data preparation needs neither a new service nor a pure helper lifted into an effect. |
| Home-NAS deployment gains coordination machinery without a supported requirement | Block | Complexity budget: added operating burden has no required guarantee to justify it. |
| Manual JSON/refinement plumbing duplicates supported library integration | Block | Library leverage: redundant mechanics create extra maintenance and error-handling paths. |
| Source-accurate acceptance checklist recast as design prose | Block | Living-doc altitude: feature walkthroughs are not strategic system context. |
| Individual test scenarios/fixtures recast as testing strategy | Block | Testing owns risks and proof boundaries, not a scenario catalog. |
| Copied schema/catalog/configuration inventories recast as context | Block | Authoritative definitions now have a stale duplicate owner. |
| System-level lifecycle/sequence diagram with essential consistency guarantees | Pass | Design explains enduring relationships and decisions. |
| Risk-to-boundary testing strategy explaining why component tests are insufficient | Pass | Testing explains the confidence model without copied scenarios. |
| Source-backed supported startup/recovery and monitoring gaps | Pass | Operations explains current capability and safe intervention. |

## Provenance

Historical owning sources were read at photography `8d2014b` (before `690c8ac`, PR 104), home-automation `6579ce0` (before `c2ae860`, PR 57) and plant-journal `32ec535` (before `829f31b`, PR 206): AGENTS, CONTRIBUTING, DESIGN-PRINCIPLES, SDD router/executor/reviewer/template, all five quality/completeness checklists, existing living-doc templates and photography UX-PATTERNS. Home-automation's domain AGENTS and Audiobookshelf contribution/design sources retain their scoped safety/operational authority.

Historical repeated review rounds, copied full checklists, mandatory spikes for every feature, fixed delivery slicing and verbatim Doc Sync pasting are workflow mechanisms, not restored quality obligations. Current execution retains targeted review, agreed scope, independent repairs, archive-phase Doc Sync and strategic living docs. Current safety, ownership, no-explanatory-comment policy and exact Live naming remain; locally required directives and direct-style coverage explanations are explicit exceptions already owned by their profile.
