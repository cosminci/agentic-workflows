package agentic

import metaconfig.Configured
import scala.meta._
import scalafix.v1._

final class NoPartialCaseClassDestructuring private (projectExtractors: ProjectCaseClassExtractors)
    extends SemanticRule("NoPartialCaseClassDestructuring") {
  def this() = this(new ProjectCaseClassExtractors(Set.empty, Map.empty))

  override def withConfiguration(config: Configuration): Configured[Rule] =
    Configured.ok(new NoPartialCaseClassDestructuring(ProjectCaseClassExtractors.read(config.scalacClasspath.map(_.toNIO))))

  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case branch: Case => check(branch.pat, references(branch.body) ++ branch.cond.toList.flatMap(references))
      case value: Defn.Val => value.pats.map(check(_, _ => true)).asPatch
      case variable: Defn.Var => variable.pats.map(check(_, _ => true)).asPatch
      case Term.For.After_4_9_9(clause, body) => checkComprehension(clause.enums, body)
      case Term.ForYield.After_4_9_9(clause, body) => checkComprehension(clause.enums, body)
    }.asPatch

  private def checkComprehension(steps: List[Enumerator], body: Term)(implicit doc: SemanticDocument): Patch = {
    val (_, findings) = steps.foldRight((references(body), List.empty[Patch])) {
      case (step, (later, findings)) =>
        step match {
          case binding: Enumerator.Generator => (later ++ references(binding.rhs), check(binding.pat, later) :: findings)
          case binding: Enumerator.CaseGenerator => (later ++ references(binding.rhs), check(binding.pat, later) :: findings)
          case binding: Enumerator.Val => (later ++ references(binding.rhs), check(binding.pat, later) :: findings)
          case guard: Enumerator.Guard => (later ++ references(guard.cond), findings)
        }
    }
    findings.asPatch
  }

  private def references(tree: Tree)(implicit doc: SemanticDocument): Set[Symbol] =
    tree.collect { case name: Name if !name.symbol.isNone => name.symbol }.toSet

  private def check(pattern: Pat, referenced: Symbol => Boolean)(implicit doc: SemanticDocument): Patch =
    pattern.collect {
      case p: Pat.Extract if projectProduct(p.fun.symbol) => (p, p.argClause.values)
      case p: Pat.ExtractInfix if projectProduct(p.op.symbol) => (p, p.lhs :: p.argClause.values)
    }.collect {
      case (p, fields) if fields.exists(field => !used(field, referenced)) =>
        DesignSignal.error(p, "Partial pattern depends on field order. Bind the value and read fields by name; preserve pattern tests.")
    }.asPatch

  private def used(field: Pat, referenced: Symbol => Boolean)(implicit doc: SemanticDocument): Boolean =
    field match {
      case _: Pat.Wildcard | _: Pat.SeqWildcard => false
      case variable: Pat.Var => referenced(variable.name.symbol)
      case Pat.Bind(variable, pattern) => used(variable, referenced) || used(pattern, referenced)
      case _ => true
    }

  private def projectProduct(companion: Symbol)(implicit doc: SemanticDocument): Boolean = {
    if (companion.isLocal) projectExtractors.containsLocal(doc.input.syntax, companion.value)
    else projectExtractors.global(companion.value)
  }
}
