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

class NoDefaultParameters extends SyntacticRule("NoDefaultParameters") {
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
      case step @ Term.ForYield.After_4_9_9(clause, _) if simpleGenerator(clause.enums) =>
        DesignSignal.error(step, "This comprehension has one step. Check what the yield adds before choosing map or using the source directly.")
    }.asPatch

  private def simpleGenerator(steps: List[Enumerator]): Boolean =
    steps match {
      case List(generator: Enumerator.Generator) => generator.pat.is[Pat.Var]
      case _ => false
    }
}
