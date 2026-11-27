package agentic

import scala.meta._
import metaconfig.Configured
import scalafix.v1._

class AdapterCodecVisibility(owners: Set[String], adapterPackages: Set[String], sharedOwners: Set[String])
    extends SemanticRule("AdapterCodecVisibility") {
  def this() = this(Set.empty, Set.empty, Set.empty)

  override def withConfiguration(config: Configuration): Configured[Rule] =
    config.conf.get[metaconfig.Conf]("AdapterCodecVisibility") match {
      case Configured.Ok(settings) =>
        configuration(settings.getOrElse("owners")(List.empty[String]),
          settings.getOrElse("adapterPackages")(List.empty[String]), settings.getOrElse("sharedOwners")(List.empty[String]))
      case error: Configured.NotOk => error
    }

  private def configuration(explicit: Configured[List[String]], packages: Configured[List[String]],
      shared: Configured[List[String]]): Configured[Rule] =
    (explicit, packages, shared) match {
      case (Configured.Ok(Nil), Configured.Ok(Nil), Configured.Ok(_)) =>
        Configured.error("AdapterCodecVisibility requires owners or adapterPackages.")
      case (Configured.Ok(scopes), Configured.Ok(roots), Configured.Ok(sharedScopes)) =>
        Configured.ok(new AdapterCodecVisibility(scopes.toSet, roots.toSet, sharedScopes.toSet))
      case (error: Configured.NotOk, _, _) => error
      case (_, error: Configured.NotOk, _) => error
      case (_, _, error: Configured.NotOk) => error
    }

  override def fix(implicit doc: SemanticDocument): Patch = {
    val visibility = restrictions
    doc.tree.collect {
      case givenAlias: Defn.GivenAlias if codec(givenAlias.name.symbol) && exposed(givenAlias.name.symbol, visibility) =>
        DesignSignal.error(givenAlias.name, guidance)
      case parameter: Term.Param if codecField(parameter) && reportable(parameter.name.symbol, visibility) =>
        DesignSignal.error(parameter.name, guidance)
      case member: Member if !member.is[Term.Param] && reportable(declarationSymbol(member), visibility) =>
        DesignSignal.error(member, guidance)
    }.asPatch
  }

  private val guidance =
    "Look for wire formats leaking outside the adapter. Keep codecs private or share them within their consumers' common boundary."

  private def reportable(symbol: Symbol, visibility: Map[Symbol, List[Mod]])(implicit doc: SemanticDocument): Boolean =
    !symbol.isLocal && codec(symbol) && exposed(symbol, visibility)

  private def codecField(parameter: Term.Param): Boolean =
    parameter.parent.exists { clause =>
      clause.parent.exists {
        case constructor: Ctor.Primary =>
          parameter.mods.exists(mod => mod.is[Mod.ValParam] || mod.is[Mod.VarParam]) ||
            (constructor.paramClauses.headOption.exists(_ eq clause) && constructor.parent.exists {
              case cls: Defn.Class => cls.mods.exists(_.is[Mod.Case])
              case _ => false
            })
        case _ => false
      }
    }

  private def restrictions(implicit doc: SemanticDocument): Map[Symbol, List[Mod]] =
    doc.tree.collect {
      case givenAlias: Defn.GivenAlias => List(givenAlias.name.symbol -> givenAlias.mods)
      case parameter: Term.Param if codecField(parameter) => List(parameter.name.symbol -> parameter.mods)
      case member: Member with Stat.WithMods => List(declarationSymbol(member) -> member.mods)
      case value: Defn.Val => bindings(value.pats, value.mods)
      case variable: Defn.Var => bindings(variable.pats, variable.mods)
    }.flatten.filterNot(_._1.isNone).toMap

  private def bindings(patterns: List[Pat], mods: List[Mod])(implicit doc: SemanticDocument): List[(Symbol, List[Mod])] =
    patterns.flatMap(_.collect { case binding: Pat.Var => binding.name.symbol -> mods })

  private def declarationSymbol(member: Member)(implicit doc: SemanticDocument): Symbol = {
    val symbol = member.name.symbol
    if (symbol.isNone) member.symbol else symbol
  }

  private def codec(symbol: Symbol)(implicit doc: SemanticDocument): Boolean =
    symbol.info.exists(_.signature match {
      case ValueSignature(result) => codecType(result)
      case MethodSignature(_, _, result) => codecType(result)
      case _ => false
    })

  private val codecRoots = Set("io/circe/Encoder#", "io/circe/Decoder#", "io/circe/Codec#", "sttp/tapir/Codec#")
  private val circeVariants =
    for {
      base <- Set("io/circe/Encoder.", "io/circe/Codec.")
      name <- Set("AsRoot#", "AsArray#", "AsObject#")
    } yield base + name

  private def codecType(result: SemanticType, seen: Set[Symbol] = Set.empty)(implicit doc: SemanticDocument): Boolean =
    result match {
      case TypeRef(_, symbol, _) => inheritsCodec(symbol, seen)
      case SingleType(_, symbol) => inheritsCodec(symbol, seen)
      case IntersectionType(types) => types.exists(codecType(_, seen))
      case _ => false
    }

  private def inheritsCodec(symbol: Symbol, seen: Set[Symbol])(implicit doc: SemanticDocument): Boolean = {
    val visited = seen + symbol
    codecRoots(symbol.value) || circeVariants(symbol.value) ||
      (!seen(symbol) && symbol.info.filterNot(_.isOpaque).exists(_.signature match {
        case ClassSignature(_, parents, _, _) => parents.exists(codecType(_, visited))
        case TypeSignature(_, lower, upper) if lower == upper => codecType(upper, visited)
        case ValueSignature(tpe) => codecType(tpe, visited)
        case _ => false
      }))
  }

  @annotation.tailrec
  private def exposed(symbol: Symbol, restrictions: Map[Symbol, List[Mod]], visibility: List[Mod] = Nil,
      packageBoundary: Boolean = false)(implicit doc: SemanticDocument): Boolean = {
    val owner = symbol.owner
    if (owner.isNone || owner == symbol) false
    else {
      val modifiers = restrictions.getOrElse(symbol, Nil) ::: visibility
      val shared = packageBoundary || sharedOwners(owner.value)
      if (owners(owner.value) || inferredBoundary(symbol, owner, shared))
        !confinedTo(owner, modifiers)
      else exposed(owner, restrictions, modifiers, shared)
    }
  }

  private def inferredBoundary(member: Symbol, owner: Symbol, shared: Boolean): Boolean =
    adapterPackages.exists(prefix => owner.value.startsWith(prefix)) &&
      (if (owner.value.endsWith("/")) true
       else owner.owner.value.endsWith("/") && !shared && !member.value.endsWith("/"))

  private def confinedTo(owner: Symbol, modifiers: List[Mod])(implicit doc: SemanticDocument): Boolean =
    modifiers.exists {
      case Mod.Private(_: Name.Anonymous | _: Term.This) => true
      case Mod.Private(scope) => scope.symbol.value.startsWith(owner.value)
      case _ => false
    }
}
