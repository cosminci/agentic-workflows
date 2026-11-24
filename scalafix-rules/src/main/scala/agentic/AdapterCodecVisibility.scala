package agentic

import scala.meta._
import metaconfig.Configured
import scalafix.v1._

class AdapterCodecVisibility(owners: Set[String]) extends SemanticRule("AdapterCodecVisibility") {
  def this() = this(Set.empty)

  override def withConfiguration(config: Configuration): Configured[Rule] =
    config.conf.getOrElse("AdapterCodecVisibility", "owners")(List.empty[String]) match {
      case Configured.Ok(Nil) => Configured.error("AdapterCodecVisibility.owners must name the allowed SemanticDB scopes.")
      case Configured.Ok(scopes) => Configured.ok(new AdapterCodecVisibility(scopes.toSet))
      case error: Configured.NotOk => error
    }

  override def fix(implicit doc: SemanticDocument): Patch = {
    val visibility = restrictions
    doc.tree.collect {
      case member: Member if codec(member.symbol) && exposed(member.symbol, visibility) =>
        DesignSignal.error(member, "Exported adapter codec may leak the wire format.")
    }.asPatch
  }

  private def restrictions(implicit doc: SemanticDocument): Map[Symbol, List[Mod]] =
    doc.tree.collect {
      case member: Member with Stat.WithMods => member.symbol -> member.mods
      case value: Defn.Val => value.pats.headOption.map(_.symbol).getOrElse(Symbol.None) -> value.mods
    }.toMap

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
      if (owners(owner.value))
        !modifiers.exists {
          case Mod.Private(_: Name.Anonymous | _: Term.This) => true
          case Mod.Private(scope) => scope.symbol.value.startsWith(owner.value)
          case _ => false
        }
      else exposed(owner, restrictions, modifiers)
    }
  }
}
