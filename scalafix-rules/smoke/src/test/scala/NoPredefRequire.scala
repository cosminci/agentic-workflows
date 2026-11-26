package fixture

object NoPredefRequireFixture:
  def direct(valid: Boolean): Unit = require(valid)
  def withMessage(valid: Boolean): Unit = require(valid, "Invalid input")
  def qualified(valid: Boolean): Unit = scala.Predef.require(requirement = valid)
  def qualifiedMessage(valid: Boolean): Unit = _root_.scala.Predef.require(valid, message = "Invalid input")

  object Imported:
    import scala.Predef.require
    def check(valid: Boolean): Unit = require(valid)

  object Aliased:
    import scala.Predef.{require as check}
    def withoutMessage(valid: Boolean): Unit = check(valid)
    def withMessage(valid: Boolean): Unit = check(valid, "Invalid input")

  object Shadowed:
    def require(value: String): String = value.trim
    val local = require(" input ")

  val selected = Shadowed.require(" input ")

  def infix(valid: Boolean): Unit = Predef `require` valid
  def infixWithMessage(valid: Boolean): Unit = scala.Predef `require` (valid, "Invalid input")
  val shadowedInfix = Shadowed `require` " input "
