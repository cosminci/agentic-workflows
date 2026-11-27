package agentic

import scala.meta._
import scalafix.v1._

private[agentic] object ProjectCall {
  def inRootPackage(tree: Tree, symbol: Symbol): Boolean =
    rootPackage(tree).exists(prefix => symbol.value.startsWith(s"$prefix/"))

  private def rootPackage(tree: Tree): Option[String] =
    tree.parent.flatMap { parent =>
      rootPackage(parent).orElse(parent match {
        case pkg: Pkg => Some(pkg.ref.syntax.takeWhile(_ != '.'))
        case _ => None
      })
    }
}

final class NamedBooleanArguments extends SemanticRule("NamedBooleanArguments") {
  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case call: Term.Apply if projectOwned(call, call.fun.symbol) =>
        check(call.argClause.values)
      case Term.New(init) if projectOwned(init, init.tpe.symbol) =>
        init.argClauses.map(clause => check(clause.values)).asPatch
    }.asPatch

  private def projectOwned(tree: Tree, symbol: Symbol): Boolean =
    symbol.isLocal || ProjectCall.inRootPackage(tree, symbol)

  private def check(arguments: List[Term]): Patch =
    if (arguments.size < 2) Patch.empty
    else arguments.filter(booleanLiteral).map { argument =>
      DesignSignal.error(argument,
        "Positional Boolean hides its parameter role. Name the argument. Look for flags that combine separate operations.")
    }.asPatch

  @annotation.tailrec
  private def booleanLiteral(argument: Term): Boolean =
    argument match {
      case _: Lit.Boolean => true
      case ascribed: Term.Ascribe => booleanLiteral(ascribed.expr)
      case _ => false
    }
}
