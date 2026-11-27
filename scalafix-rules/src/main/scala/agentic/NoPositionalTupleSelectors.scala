package agentic

import scala.meta._
import scalafix.v1._

final class NoPositionalTupleSelectors extends SemanticRule("NoPositionalTupleSelectors") {
  private val tupleSelector = """scala/Tuple(?:[1-9]|1[0-9]|2[0-2])#_[1-9][0-9]*(?:\(\))?\.""".r

  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case selection: Term.Select if tupleSelector.matches(selection.name.symbol.value) && insideCallback(selection) =>
        DesignSignal.error(selection.name, "Positional tuple access hides the field's role. Destructure the tuple with meaningful names.")
    }.asPatch

  private def insideCallback(tree: Tree): Boolean =
    tree.parent.exists {
      case callback: Term.Function if isArgument(callback) => true
      case callback: Term.ContextFunction if isArgument(callback) => true
      case callback: Term.PartialFunction if isArgument(callback) => true
      case callback: Term.AnonymousFunction if isArgument(callback) => true
      case parent => insideCallback(parent)
    }

  private def isArgument(tree: Tree): Boolean =
    tree.parent.exists {
      case _: Term.ArgClause => true
      case named: Term.Assign if named.rhs eq tree => named.parent.exists(_.is[Term.ArgClause])
      case ascribed: Term.Ascribe if ascribed.expr eq tree => isArgument(ascribed)
      case _ => false
    }
}
