package fixture

object NoDefaultParametersFixture:
  final case class Item(name: String, quantity: Int = 1)

  def format(value: String, separator: String = ":"): String = value + separator
  def twice(value: String): String = value + value
