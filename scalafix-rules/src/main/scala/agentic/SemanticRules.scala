package agentic

import scala.meta._
import scalafix.v1._

final class NoPredefRequire extends SemanticRule("NoPredefRequire") {
  private val requireMethod = SymbolMatcher.normalized("scala/Predef.require.")

  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case call: Term.Apply if requireMethod.matches(call.fun.symbol) =>
        report(call)
      case call: Term.ApplyInfix if requireMethod.matches(call.op.symbol) =>
        report(call)
    }.asPatch

  private def report(call: Term) =
    DesignSignal.error(call,
      "Predef.require throws on failure. Check which boundary owns the invariant and how its caller receives a rejection.")
}

final class MultilineEitherCond extends SemanticRule("MultilineEitherCond") {
  private val cond = SymbolMatcher.exact("scala/util/Either.cond().")

  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case call: Term.Apply if DesignSignal.multiline(call) && cond.matches(call.fun.symbol) =>
        DesignSignal.error(call,
          "Either.cond spans multiple lines. Check whether the call combines decisions or hides a branch's work. Preserve lazy branch evaluation.")
    }.asPatch
}

class NamedSentinelArguments extends SemanticRule("NamedSentinelArguments") {
  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case call: Term.Apply if call.argClause.values.size > 1 =>
        opaqueArguments(call).map { argument =>
          DesignSignal.error(argument,
            "Positional empty, zero or Unit value hides its parameter role; this often signals interchangeable primitive or optional fields. Name the argument or model the role with a domain type.")
        }.asPatch
    }.asPatch

  private def opaqueArguments(call: Term.Apply)(implicit doc: SemanticDocument) = {
    val symbol = call.fun.symbol.value
    val arguments = call.argClause.values
    if (symbol.startsWith("scala/util/Either.cond(")) arguments.filter(_.is[Lit.Unit])
    else if (rootPackage(call).exists(prefix => symbol.startsWith(s"$prefix/"))) arguments.filter(opaque)
    else Nil
  }

  private def rootPackage(tree: Tree): Option[String] =
    tree.parent.flatMap(parent => rootPackage(parent).orElse(packageRoot(parent)))

  private def packageRoot(tree: Tree): Option[String] =
    tree match {
      case pkg: Pkg => Some(pkg.ref.syntax.takeWhile(_ != '.'))
      case _ => None
    }

  private def opaque(argument: Term)(implicit doc: SemanticDocument): Boolean =
    argument match {
      case Lit.Int(0) | Lit.Long(0L) | Lit.String("") | _: Lit.Unit => true
      case Lit.Float(value) => value.toFloat == 0.0f
      case Lit.Double(value) => value.toDouble == 0.0d
      case typed: Term.ApplyType => opaque(typed.fun)
      case _ =>
        val symbol = argument.symbol.value
        symbol == "scala/None." || symbol == "scala/package.Nil." ||
          symbol.startsWith("scala/Option.empty(")
    }
}

class OptionEitherBindingNames extends SemanticRule("OptionEitherBindingNames") {
  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case name: Pat.Var => check(name.name)
      case parameter: Term.Param => check(parameter.name)
    }.asPatch

  private def check(name: Name)(implicit doc: SemanticDocument): Patch =
    name.symbol.info.map(_.signature).map {
      case ValueSignature(tpe) => ValueSignature(bindingType(tpe))
      case other => other
    } match {
      case Some(ValueSignature(TypeRef(_, symbol, arguments))) =>
        symbol.value match {
          case "scala/Option#" if !name.value.matches("maybe[A-Z].*") && !genericExtensionReceiver(name, arguments) =>
            DesignSignal.error(name, "Option binding must start with maybe: its name hides possible absence.")
          case "scala/util/Either#" if !name.value.endsWith("Result") && !genericExtensionReceiver(name, arguments) =>
            DesignSignal.error(name, "Either binding must end with Result: its name hides an operation outcome.")
          case "scala/Some#" if !name.value.matches("maybe[A-Z].*") =>
            DesignSignal.error(name, "Option binding must start with maybe: its name hides possible absence.")
          case "scala/util/Left#" | "scala/util/Right#" if !name.value.endsWith("Result") =>
            DesignSignal.error(name, "Either binding must end with Result: its name hides an operation outcome.")
          case _ => Patch.empty
        }
      case Some(ValueSignature(SingleType(_, symbol))) if symbol.value == "scala/None." && !name.value.matches("maybe[A-Z].*") =>
        DesignSignal.error(name, "Option binding must start with maybe: its name hides possible absence.")
      case _ => Patch.empty
    }

  private def bindingType(tpe: SemanticType, visited: Set[Symbol] = Set.empty,
      arguments: Map[Symbol, SemanticType] = Map.empty)(implicit doc: SemanticDocument): SemanticType =
    tpe match {
      case ByNameType(result) => bindingType(result, visited, arguments)
      case TypeRef(prefix, symbol, parameters) if !visited(symbol) =>
        val actual = parameters.map {
          case ref @ TypeRef(_, parameter, Nil) => arguments.getOrElse(parameter, ref)
          case other => other
        }
        arguments.get(symbol).orElse {
          symbol.info.filterNot(_.isOpaque).flatMap(_.signature match {
            case TypeSignature(formals, lower, upper) if lower == upper && formals.size == actual.size =>
              Some(bindingType(upper, visited + symbol, arguments ++ formals.map(_.symbol).zip(actual)))
            case _ => None
          })
        }.getOrElse(TypeRef(prefix, symbol, actual))
      case other => other
    }

  private def genericExtensionReceiver(name: Name, arguments: List[SemanticType])(implicit doc: SemanticDocument): Boolean =
    name.parent.flatMap(_.parent).exists {
      case clause: Term.ParamClause if clause.mod.isEmpty =>
        clause.parent.flatMap(_.parent).exists(_.is[Defn.ExtensionGroup]) && arguments.nonEmpty && arguments.forall {
          case TypeRef(_, symbol, Nil) => symbol.info.exists(_.isTypeParameter)
          case _ => false
        }
      case _ => false
    }
}

class MultilineAssertion extends SemanticRule("MultilineAssertion") {
  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case call: Term.Apply if DesignSignal.multiline(call) && outermost(call) && assertion(call) =>
        DesignSignal.error(call, assertionGuidance(call))
    }.asPatch

  private def outermost(call: Term.Apply): Boolean =
    !call.parent.exists {
      case parent: Term.Apply => parent.fun eq call
      case _ => false
    }

  private def assertionGuidance(call: Term.Apply)(implicit doc: SemanticDocument): String =
    if (assertionSymbol(call).contains("#intercept"))
      "Multiline exception assertion: setup may throw first. Prepare inputs outside; intercept only the operation under test."
    else
      "Multiline assertion: setup or action may be hidden. Separate them from the check and name substantial actual or expected values."

  private def assertion(term: Term)(implicit doc: SemanticDocument): Boolean = {
    val symbol = assertionSymbol(term)
    symbol.startsWith("munit/") &&
      Set("assert", "assertEquals", "assertNotEquals", "assertNoDiff", "intercept", "interceptMessage")
        .exists(name => symbol.contains(s"#$name("))
  }

  @annotation.tailrec
  private def assertionSymbol(term: Term)(implicit doc: SemanticDocument): String =
    term match {
      case call: Term.Apply => assertionSymbol(call.fun)
      case call: Term.ApplyType => assertionSymbol(call.fun)
      case target => target.symbol.value
    }
}
