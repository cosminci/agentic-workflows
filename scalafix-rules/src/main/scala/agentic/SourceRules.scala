package agentic

import scala.meta._
import scalafix.lint.LintSeverity
import scalafix.v1._

private[agentic] object DesignSignal {
  def error(tree: Tree, message: String): Patch =
    Patch.lint(Diagnostic("", s"$message This is a symptom and usually points to a design smell. Investigate the cause; fix the design or justify a rule-specific inline exclusion.", tree.pos, severity = LintSeverity.Error))

  def multiline(tree: Tree): Boolean =
    tree.pos != Position.None && tree.pos.startLine != tree.pos.endLine
}

class RuntimeDefaults extends SyntacticRule("RuntimeDefaults") {
  override def fix(implicit doc: SyntacticDocument): Patch =
    doc.tree.collect {
      case parameter: Term.Param if parameter.default.nonEmpty =>
        DesignSignal.error(parameter, "Default parameter may hide caller policy or mix concerns.")
    }.asPatch
}

class MultilineComprehensionStep extends SyntacticRule("MultilineComprehensionStep") {
  override def fix(implicit doc: SyntacticDocument): Patch =
    doc.tree.collect {
      case step: Enumerator if DesignSignal.multiline(step) =>
        DesignSignal.error(step, "Multiline step may bury implementation detail in orchestration.")
    }.asPatch
}

class SimplifiedForYield extends SyntacticRule("SimplifiedForYield") {
  override def fix(implicit doc: SyntacticDocument): Patch =
    doc.tree.collect {
      case step @ Term.ForYield.After_4_9_9(clause, _) =>
        guidance(clause.enums).fold(Patch.empty)(message => DesignSignal.error(step, message))
    }.asPatch

  private def guidance(steps: List[Enumerator]) =
    steps match {
      case List(generator: Enumerator.Generator) if generator.pat.is[Pat.Var] =>
        Some("This comprehension has one step. Check what the yield adds before choosing map or using the source directly.")
      case List(generator: Enumerator.Generator) if generator.pat.is[Pat.Wildcard] =>
        Some("This comprehension discards each input. Use map with a wildcard parameter if it preserves behavior.")
      case List(generator: Enumerator.Generator, _: Enumerator.Guard) if generator.pat.is[Pat.Var] =>
        Some("This comprehension filters and maps one source. Use collect if the source supports it and it preserves behavior.")
      case _ => None
    }
}

class MultilineParentheses extends SyntacticRule("MultilineParentheses") {
  override def fix(implicit doc: SyntacticDocument): Patch = {
    val tokens = doc.tree.tokens.iterator.filterNot(token => token.is[Token.Trivia]).toVector
    val indexes = tokens.iterator.zipWithIndex.map { case (token, index) => token.pos.start -> index }.toMap
    doc.tree.collect {
      case term: Term if DesignSignal.multiline(term) && surrounded(term, tokens, indexes) =>
        DesignSignal.error(term, "Multiline expression in parentheses may bury work at the call site.")
    }.asPatch
  }

  private def surrounded(term: Term, tokens: Vector[Token], indexes: Map[Int, Int]): Boolean =
    (for {
      first <- indexes.get(term.tokens.head.pos.start)
      last <- indexes.get(term.tokens.last.pos.start)
    } yield tokens.lift(first - 1).exists(_.is[Token.LeftParen]) &&
      tokens.lift(last + 1).exists(_.is[Token.RightParen])).contains(true)
}
