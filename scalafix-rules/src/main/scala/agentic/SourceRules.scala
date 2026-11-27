package agentic

import scala.meta._
import scalafix.lint.LintSeverity
import scalafix.v1._

private[agentic] object DesignSignal {
  def error(tree: Tree, message: String): Patch =
    Patch.lint(Diagnostic("", s"$message Fix the cause or justify a rule-specific inline exclusion.", tree.pos, severity = LintSeverity.Error))

  def multiline(tree: Tree): Boolean =
    tree.pos != Position.None && tree.pos.startLine != tree.pos.endLine
}

class NoDefaultParameters extends SyntacticRule("NoDefaultParameters") {
  override def fix(implicit doc: SyntacticDocument): Patch =
    doc.tree.collect {
      case parameter: Term.Param if parameter.default.nonEmpty =>
        DesignSignal.error(parameter, "Default parameter: look for silent caller policy or one API doing several jobs.")
    }.asPatch
}

class MultilineComprehensionStep extends SyntacticRule("MultilineComprehensionStep") {
  override def fix(implicit doc: SyntacticDocument): Patch =
    doc.tree.collect {
      case step: Enumerator if DesignSignal.multiline(step) =>
        DesignSignal.error(step, "Multiline step: look for inline parsing, validation, or repeated sequencing. Name the operation, not just the expression.")
    }.asPatch
}

class NoMixedForComprehensionBindings extends SyntacticRule("NoMixedForComprehensionBindings") {
  override def fix(implicit doc: SyntacticDocument): Patch =
    doc.tree.collect {
      case Term.For.After_4_9_9(clause, _) => check(clause.enums)
      case Term.ForYield.After_4_9_9(clause, _) => check(clause.enums)
    }.asPatch

  private def check(steps: List[Enumerator]): Patch =
    if (steps.exists(step => step.is[Enumerator.Generator] || step.is[Enumerator.CaseGenerator]))
      steps.collect {
        case binding: Enumerator.Val =>
          DesignSignal.error(binding, "Look for hidden writes, inline validation, eager error text, or decoding outside the failure path. In tests, keep scenario setup distinct from actions. Preserve effect and failure order; do not mask with .pure.")
      }.asPatch
    else Patch.empty
}

class SimplifiedForYield extends SyntacticRule("SimplifiedForYield") {
  override def fix(implicit doc: SyntacticDocument): Patch =
    doc.tree.collect {
      case step @ Term.ForYield.After_4_9_9(clause, _) if simpleGenerator(clause.enums) =>
        DesignSignal.error(step, "One-step comprehension: use map for a pure transformation. For an identity yield, use the source only if type and behavior stay the same.")
    }.asPatch

  private def simpleGenerator(steps: List[Enumerator]): Boolean =
    steps match {
      case List(generator: Enumerator.Generator) => generator.pat.is[Pat.Var]
      case _ => false
    }
}
