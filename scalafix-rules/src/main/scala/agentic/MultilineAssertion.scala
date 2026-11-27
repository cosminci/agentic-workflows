package agentic

import metaconfig.{Conf, Configured}
import scala.meta._
import scalafix.v1._

final class MultilineAssertion(additionalAssertions: Set[String], additionalExceptionAssertions: Set[String])
    extends SemanticRule("MultilineAssertion") {
  def this() = this(Set.empty, Set.empty)

  override def withConfiguration(config: Configuration): Configured[Rule] =
    config.conf.getOrElse("MultilineAssertion")(Conf.Obj(Nil): Conf) match {
      case Configured.Ok(settings) =>
        (settings.getOrElse("additionalAssertions")(List.empty[String]),
          settings.getOrElse("additionalExceptionAssertions")(List.empty[String])) match {
          case (Configured.Ok(assertions), Configured.Ok(exceptions)) =>
            Configured.ok(new MultilineAssertion(assertions.toSet, exceptions.toSet))
          case (error: Configured.NotOk, _) => error
          case (_, error: Configured.NotOk) => error
        }
      case error: Configured.NotOk => error
    }

  private val exceptionApis = Map(
    "org/scalatest/Assertions#" -> Set("intercept", "assertThrows")
  )

  private val assertionApis = Map(
    "org/scalatest/Assertions#" -> Set("assert", "assertResult", "assertDoesNotCompile", "assertCompiles", "assertTypeError"),
    "org/scalatest/matchers/should/Matchers#" -> Set("should", "shouldBe", "shouldEqual", "shouldNot"),
    "org/scalatest/matchers/must/Matchers#" -> Set("must", "mustBe", "mustEqual", "mustNot"),
    "org/scalatest/matchers/dsl/ResultOfNotWordForAny#" -> Set("apply", "be", "equal")
  )

  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case call: Term.Apply if DesignSignal.multiline(call) && !appliedAgain(call) =>
        report(call, targetSymbol(call.fun))
      case call: Term.ApplyInfix if DesignSignal.multiline(call) && !appliedAgain(call) =>
        report(call, call.op.symbol.value)
    }.asPatch

  private def report(call: Term, symbol: String): Patch =
    if (additionalExceptionAssertions(symbol) || matches(exceptionApis, symbol) ||
        munit(symbol, Set("intercept", "interceptMessage")))
      DesignSignal.error(call, "Multiline exception assertion: look for setup that can throw first. Prepare inputs outside; check only the operation under test.")
    else if (additionalAssertions(symbol) || matches(assertionApis, symbol) ||
        munit(symbol, Set("assert", "assertEquals", "assertNotEquals", "assertNoDiff")))
      DesignSignal.error(call, "Multiline assertion: look for setup or actions inside the check. Name large operands only when that makes the full outcome clearer.")
    else Patch.empty

  private def matches(apis: Map[String, Set[String]], symbol: String): Boolean =
    apis.exists { case (owner, names) => names.exists(name => symbol.startsWith(s"$owner$name(")) }

  private def munit(symbol: String, names: Set[String]): Boolean =
    symbol.startsWith("munit/") && names.exists(name => symbol.contains(s"#$name("))

  @annotation.tailrec
  private def appliedAgain(term: Term): Boolean =
    term.parent match {
      case Some(parent: Term.ApplyType) if parent.fun eq term => appliedAgain(parent)
      case Some(parent: Term.Apply) => parent.fun eq term
      case _ => false
    }

  @annotation.tailrec
  private def targetSymbol(term: Term)(implicit doc: SemanticDocument): String =
    term match {
      case call: Term.Apply => targetSymbol(call.fun)
      case call: Term.ApplyType => targetSymbol(call.fun)
      case call: Term.ApplyInfix => call.op.symbol.value
      case target => target.symbol.value
    }
}
