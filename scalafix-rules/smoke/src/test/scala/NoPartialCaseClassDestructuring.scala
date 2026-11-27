package fixture

import fixture.other.ExternalPair

object NoPartialCaseClassDestructuringFixture:
  final case class Pair(first: Int, second: Int)
  final case class Box(pair: Pair, label: String)

  object Extractor:
    def unapply(pair: Pair): Option[(Int, Int)] = Some((pair.first, pair.second))

  def wildcard(pair: Pair): Int = pair match
    case Pair(first, _) => first

  def unused(pair: Pair): Int = pair match
    case Pair(first, second) => first

  def shadowed(pair: Pair): Int = pair match
    case Pair(first, second) => List(1).map(second => second + first).sum

  def usedInGuard(pair: Pair): Int = pair match
    case Pair(first, second) if second > 0 => first
    case Pair(first, second) => first + second

  def literal(pair: Pair): Int = pair match
    case Pair(0, second) => second
    case other => other.first

  def nested(box: Box): Int = box match
    case Box(Pair(first, _), "selected") => first
    case other => other.pair.first

  def callback(pairs: List[Pair]): List[Int] =
    pairs.map { case Pair(_, second) => second }

  def alias(pair: Pair): Int =
    import NoPartialCaseClassDestructuringFixture.{Pair as Entry}
    pair match
      case Entry(first, _) => first

  def library(value: Option[Either[String, (Int, Int)]]): Int = value match
    case Some(Right((number, _))) => number
    case Some(Left(_)) | None => 0

  def custom(pair: Pair): Int = pair match
    case Extractor(_, second) => second
    case other => other.second

  def crossFile(pair: ExternalPair): Int = pair match
    case ExternalPair(first, _) => first

  enum Outcome:
    case Found(pair: Pair)
    case Missing

  def outcome(value: Outcome): Boolean = value match
    case Outcome.Found(_) => true
    case Outcome.Missing => false

  def local(): Int =
    case class Local(first: Int, second: Int)
    Local(1, 2) match
      case Local(first, _) => first
