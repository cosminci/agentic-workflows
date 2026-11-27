package agentic

import scala.meta._
import scalafix.v1._

final class PreferCatsSyntax extends SemanticRule("PreferCatsSyntax") {
  private val some = SymbolMatcher.normalized("scala/Some.", "scala/Some.apply.")
  private val option = SymbolMatcher.normalized("scala/Option.", "scala/Option.apply.")
  private val right = SymbolMatcher.normalized("scala/package.Right.", "scala/util/Right.", "scala/util/Right.apply.")
  private val left = SymbolMatcher.normalized("scala/package.Left.", "scala/util/Left.", "scala/util/Left.apply.")
  private val raise = SymbolMatcher.normalized("cats/ApplicativeError#raiseError.", "cats/effect/IO.raiseError.")
  private val pure = SymbolMatcher.normalized("cats/Applicative#pure.", "cats/effect/IO.pure.")
  private val sleep = SymbolMatcher.normalized("cats/effect/IO.sleep.", "cats/effect/kernel/GenTemporal#sleep.")
  private val sequenceRight = SymbolMatcher.normalized("cats/effect/IO#`*>`.", "cats/syntax/ApplyOps#`*>`.")
  private val lazySequenceRight = SymbolMatcher.normalized("cats/effect/IO#`>>`.", "cats/syntax/FlatMapOps#`>>`.")
  private val delayByMessage = "Use effect.delayBy(duration) instead of sequencing a sleep before the effect."
  private val fromEither = SymbolMatcher.normalized("cats/effect/IO.fromEither.")
  private val fromOption = SymbolMatcher.normalized("cats/effect/IO.fromOption.")
  private val fromTry = SymbolMatcher.normalized("cats/effect/IO.fromTry.")
  private val eitherT = SymbolMatcher.normalized("cats/data/EitherT.", "cats/data/EitherT.apply.")
  private val eitherTFromPure = SymbolMatcher.normalized("cats/data/EitherT.fromEither.", "cats/data/EitherT.rightT.", "cats/data/EitherT.leftT.")
  private val liftF = SymbolMatcher.normalized("cats/data/EitherT.liftF.")

  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case call: Term.ApplyInfix =>
        timedSequence(call.lhs, call.op.symbol).fold(Patch.empty)(DesignSignal.error(call, _))
      case call: Term.Apply =>
        val symbol = call.fun.symbol
        val message = timedApplication(call.fun).orElse {
          if (some.matches(symbol))
            Some("Use Option[A] and .some if absence is valid, or use A directly if the value is required. Type an inline function if needed.")
          else if (option.matches(symbol) && call.argClause.values.exists(isNullLiteral))
            Some("Use none[A] for a known absent value.")
          else if (right.matches(symbol)) Some("Use .asRight for a successful Either value.")
          else if (left.matches(symbol)) Some("Use .asLeft for a failed Either value.")
          else if (raise.matches(symbol)) Some("Use .raiseError with the error as the receiver.")
          else if (pure.matches(symbol)) Some("Use .pure with the value as the receiver.")
          else if (fromEither.matches(symbol) || fromTry.matches(symbol) ||
              (fromOption.matches(symbol) && !continuedApplication(call)))
            Some("Use .liftTo[IO] with the value as the receiver.")
          else if (eitherT.matches(symbol) && !inHelper(call, "eitherT"))
            Some("Use the project's .eitherT extension on the effect.")
          else if (eitherTFromPure.matches(symbol))
            Some("Keep pure decisions outside EitherT. Handle the result where effectful work starts.")
          else if (liftF.matches(symbol) && !inHelper(call, "lift"))
            Some("Use the project's .lift extension on the effect.")
          else None
        }
        message.fold(Patch.empty)(DesignSignal.error(call, _))
    }.asPatch

  private def isSleep(term: Term)(implicit doc: SemanticDocument): Boolean =
    term match {
      case call: Term.Apply => sleep.matches(call.fun.symbol)
      case _ => false
    }

  private def timedApplication(fun: Term)(implicit doc: SemanticDocument): Option[String] =
    fun match {
      case typed: Term.ApplyType => timedApplication(typed.fun)
      case select: Term.Select => timedSequence(select.qual, select.name.symbol)
      case _ => None
    }

  private def timedSequence(receiver: Term, operator: Symbol)(implicit doc: SemanticDocument): Option[String] =
    if (!isSleep(receiver)) None
    else if (lazySequenceRight.matches(operator))
      Some(s"$delayByMessage Preserve deferred construction of the right-hand effect when it matters.")
    else if (sequenceRight.matches(operator)) Some(delayByMessage)
    else None

  private def continuedApplication(call: Term.Apply): Boolean =
    call.parent.exists {
      case outer: Term.Apply => outer.fun eq call
      case _ => false
    }

  private def isNullLiteral(term: Term): Boolean =
    term match {
      case _: Lit.Null => true
      case ascribed: Term.Ascribe => isNullLiteral(ascribed.expr)
      case named: Term.Assign => isNullLiteral(named.rhs)
      case _ => false
    }

  private def inHelper(body: Term, name: String): Boolean =
    body.parent.exists {
      case block: Term.Block if block.stats.size == 1 && (block.stats.head eq body) =>
        inHelper(block, name)
      case method: Defn.Def if method.name.value == name && (method.body eq body) =>
        method.parent.exists(_.is[Defn.ExtensionGroup])
      case _ => false
    }
}
