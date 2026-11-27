package agentic

import java.nio.file.{Files, Path}
import scala.jdk.CollectionConverters._
import scala.meta.internal.semanticdb.{ClassSignature, MethodSignature, SingleType, SymbolInformation, TextDocuments, TypeRef, XtensionSemanticdbSymbolInformation}
import scalafix.v1.Symbol

private final class ProjectCaseClassExtractors(val global: Set[String], local: Map[String, Set[String]]) {
  def containsLocal(source: String, symbol: String): Boolean =
    local.exists { case (uri, symbols) => (source == uri || source.endsWith(s"/$uri")) && symbols(symbol) }
}

private object ProjectCaseClassExtractors {
  def read(classpath: List[Path]): ProjectCaseClassExtractors = {
    val global = Set.newBuilder[String]
    val local = Map.newBuilder[String, Set[String]]
    classpath.map(_.resolve("META-INF/semanticdb")).filter(Files.isDirectory(_)).foreach { root =>
      val paths = Files.walk(root)
      try paths.iterator().asScala.filter(_.toString.endsWith(".semanticdb")).foreach { path =>
        val input = Files.newInputStream(path)
        val docs = try TextDocuments.parseFrom(input).documents finally input.close()
        docs.foreach { document =>
          val symbols = document.symbols.iterator.map(info => info.symbol -> info).toMap
          global ++= globalExtractors(symbols)
          val locals = localExtractors(symbols).toSet
          if (locals.nonEmpty) local += java.net.URI.create(document.uri).getPath -> locals
        }
      }
      finally paths.close()
    }
    new ProjectCaseClassExtractors(global.result(), local.result())
  }

  private def globalExtractors(symbols: Map[String, SymbolInformation]): Iterator[String] =
    symbols.valuesIterator.filter(info => info.isClass && info.isCase && !Symbol(info.symbol).isLocal).flatMap { product =>
      val companion = product.symbol.stripSuffix("#") + "."
      symbols.get(companion).filter(info => extractedProduct(info, symbols).contains(product.symbol)).map(_.symbol)
    }

  private def localExtractors(symbols: Map[String, SymbolInformation]): Iterator[String] =
    symbols.valuesIterator.filter(info => Symbol(info.symbol).isLocal && info.isObject).flatMap { companion =>
      companion.signature match {
        case ClassSignature(_, _, SingleType(_, reference), _)
            if extractedProduct(companion, symbols).filter(symbol => Symbol(symbol).isLocal).flatMap(symbols.get)
              .exists(info => info.isClass && info.isCase) =>
          Some(reference)
        case _ => None
      }
    }

  private def extractedProduct(companion: SymbolInformation, symbols: Map[String, SymbolInformation]): Option[String] =
    companion.signature match {
      case ClassSignature(_, _, _, Some(scope)) =>
        val methods = scope.symlinks.flatMap(symbols.get) ++ scope.hardlinks
        methods.filter(info => info.displayName == "unapply" || info.displayName == "unapplySeq") match {
          case Seq(method) => method.signature match {
            case MethodSignature(_, _, TypeRef(_, result, _), _) => Some(result)
            case _ => None
          }
          case _ => None
        }
      case _ => None
    }
}
