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
    doc.tree.collect { case branch: Case => check(branch) }.asPatch

  private def check(branch: Case)(implicit doc: SemanticDocument): Patch = {
    val products = branch.pat.collect { case pattern: Pat.Extract if projectProduct(pattern) => pattern }
    if (products.isEmpty) Patch.empty
    else {
      val references = (branch.cond.toList :+ branch.body).flatMap(_.collect {
        case name: Name if !name.symbol.isNone => name.symbol
      }).toSet
      products.collect {
        case pattern if pattern.argClause.values.exists(field => !used(field, references)) =>
          DesignSignal.error(pattern, "This pattern does not use every case class field. Bind the value and access the fields you need by name.")
      }.asPatch
    }
  }

  private def used(field: Pat, references: Set[Symbol])(implicit doc: SemanticDocument): Boolean =
    field match {
      case _: Pat.Wildcard => false
      case variable: Pat.Var => references(variable.name.symbol)
      case Pat.Bind(variable, pattern) => used(variable, references) || used(pattern, references)
      case _ => true
    }

  private def projectProduct(pattern: Pat.Extract)(implicit doc: SemanticDocument): Boolean = {
    val companion = pattern.fun.symbol
    if (companion.isLocal) projectExtractors.containsLocal(doc.input.syntax, companion.value)
    else projectExtractors.global(companion.value)
  }
}
