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
      case member: Member if codec(declarationSymbol(member)) && exposed(declarationSymbol(member), visibility) =>
        DesignSignal.error(member, guidance)
    }.asPatch
  }

  private val guidance =
    "Codec visible outside its owner: keep wire translation private, or confine a genuinely shared codec to its consumers' common boundary."

  private def restrictions(implicit doc: SemanticDocument): Map[Symbol, List[Mod]] =
    doc.tree.collect {
      case givenAlias: Defn.GivenAlias => givenAlias.name.symbol -> givenAlias.mods
      case member: Member with Stat.WithMods => declarationSymbol(member) -> member.mods
      case value: Defn.Val => value.pats.headOption.map(_.symbol).getOrElse(Symbol.None) -> value.mods
    }.toMap

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

  private def codecType(result: SemanticType): Boolean =
    result match {
      case TypeRef(_, symbol, _) =>
        Set("io/circe/Encoder#", "io/circe/Decoder#", "io/circe/Codec#",
          "sttp/tapir/Codec#").contains(symbol.value)
      case _ => false
    }

  @annotation.tailrec
  private def exposed(symbol: Symbol, restrictions: Map[Symbol, List[Mod]], visibility: List[Mod] = Nil)(implicit doc: SemanticDocument): Boolean = {
    val owner = symbol.owner
    if (owner.isNone || owner == symbol) false
    else {
      val modifiers = restrictions.getOrElse(symbol, Nil) ::: visibility
      if (owners(owner.value) || inferredBoundary(symbol, owner))
        !confinedTo(owner, modifiers)
      else exposed(owner, restrictions, modifiers)
    }
  }

  private def inferredBoundary(member: Symbol, owner: Symbol): Boolean =
    adapterPackages.exists(prefix => owner.value.startsWith(prefix)) &&
      (if (owner.value.endsWith("/")) true
       else owner.owner.value.endsWith("/") && !sharedOwners(owner.value) && !member.value.endsWith("/"))

  private def confinedTo(owner: Symbol, modifiers: List[Mod])(implicit doc: SemanticDocument): Boolean =
    modifiers.exists {
      case Mod.Private(_: Name.Anonymous | _: Term.This) => true
      case Mod.Private(scope) => scope.symbol.value.startsWith(owner.value)
      case _ => false
    }
}
