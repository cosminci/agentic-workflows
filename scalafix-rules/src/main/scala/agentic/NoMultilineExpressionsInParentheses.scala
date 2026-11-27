package agentic

import scala.collection.mutable
import scala.meta._
import scalafix.v1._

class NoMultilineExpressionsInParentheses extends SyntacticRule("NoMultilineExpressionsInParentheses") {
  override def fix(implicit doc: SyntacticDocument): Patch = {
    val parentheses = new Parentheses(doc.tree.tokens)
    val handlers = doc.tree.collect { case call: Term.Apply if endpointHandler(call) => call }
    val candidates = doc.tree.collect {
      case term: Term if parentheses.containsMultiline(term) && !assemblyEntry(term) &&
          !handlers.exists(handler => parentheses.inSameParentheses(term, handler)) => term
      case step: Enumerator if parentheses.containsMultiline(step) => step
    }.sortBy(tree => (tree.pos.start, -tree.pos.end))

    val outermost = candidates.foldLeft(List.empty[Tree]) { (reported, tree) =>
      if (reported.headOption.exists(_.pos.end >= tree.pos.end)) reported
      else tree :: reported
    }

    outermost.reverse.map { tree =>
      DesignSignal.error(tree, "Multiline logic inside parentheses. Use Scala 3 colon syntax or braces where they fit, or name the work if it belongs at another level.")
    }.asPatch
  }

  private def assemblyEntry(term: Term): Boolean =
    term match {
      case field: Term.ApplyInfix => nestedJsonField(field)
      case call: Term.Apply if jsonObject(call) =>
        call.parent.flatMap(_.parent).exists {
          case field: Term.ApplyInfix => nestedJsonField(field)
          case _                      => false
        }
      case _ => false
    }

  private def nestedJsonField(field: Term.ApplyInfix): Boolean =
    field.op.value == "->" && field.lhs.is[Lit.String] &&
      field.argClause.values.exists {
        case call: Term.Apply => jsonObject(call)
        case _                => false
      } && field.parent.flatMap(_.parent).exists {
        case call: Term.Apply => jsonObject(call)
        case _                => false
      }

  private def jsonObject(call: Term.Apply): Boolean =
    call.fun match {
      case Term.Select(Term.Name("Json") | Term.Select(_, Term.Name("Json")), Term.Name("obj")) => true
      case _                                                                                   => false
    }

  private def endpointHandler(call: Term.Apply): Boolean =
    call.fun match {
      case Term.Select(_, Term.Name("handle")) =>
        call.parent.flatMap(_.parent).exists {
          case list: Term.Apply if list.fun.is[Term.Name] && list.fun.syntax == "List" =>
            list.parent.exists {
              case definition: Defn.Def => definition.name.value == "serverEndpoints" && (definition.body eq list)
              case value: Defn.Val =>
                (value.rhs eq list) && (value.pats match {
                  case List(Pat.Var(Term.Name("serverEndpoints"))) => true
                  case _ => false
                })
              case _                    => false
            } && call.tokens.iterator.find(token => token.pos.start >= call.fun.pos.end && !token.is[Token.Trivia]).exists {
              case _: Token.Colon | _: Token.LeftBrace => true
              case _                                    => false
            }
          case _ => false
        }
      case _ => false
    }

  private final class Parentheses(tokens: Tokens) {
    private val starts = mutable.Map.empty[Int, Int]
    private val ends = mutable.Map.empty[Int, Int]
    index()

    def inSameParentheses(tree: Tree, handler: Term.Apply): Boolean =
      handler.pos.start <= tree.pos.start && tree.pos.end <= handler.pos.end &&
        starts.get(handler.pos.start) == starts.get(tree.pos.start)

    def containsMultiline(tree: Tree): Boolean =
      DesignSignal.multiline(tree) &&
        starts.get(tree.pos.start).exists(open => ends.get(tree.pos.end).contains(open)) &&
        (significant(tree.tokens).sliding(2).exists {
          case Seq(left, right) => left.pos.endLine < right.pos.startLine
          case _               => false
        } || tree.collect {
          case sql: Term.Interpolate if sql.prefix.value == "sql" || sql.prefix.value == "fr" => sql
        }.exists(DesignSignal.multiline))

    private def significant(tokens: Tokens): Iterator[Token] =
      tokens.iterator.filter(t => t.pos.start < t.pos.end && !t.is[Token.Trivia])

    private def index(): Unit = {
      val delimiters = mutable.ArrayDeque.empty[Token]
      significant(tokens).foreach { token =>
        token match {
          case _: Token.RightParen | _: Token.RightBrace | _: Token.RightBracket => delimiters.removeLast()
          case _ =>
        }
        delimiters.lastOption.collect { case open: Token.LeftParen => open.pos.start }.foreach { open =>
          starts(token.pos.start) = open
          ends(token.pos.end) = open
        }
        token match {
          case _: Token.LeftParen | _: Token.LeftBrace | _: Token.LeftBracket => delimiters.append(token)
          case _ =>
        }
      }
    }
  }
}
