package fixture

final case class Item(name: String, quantity: Long)

object OpaqueArgumentsFixture:
  def build(name: String, quantity: Long, tags: List[String], maybeOwner: Option[String], note: String): Item =
    Item(name, quantity)

  val failing = build("", 0L, Nil, Option.empty[String], "")
  val passing = build(name = "", quantity = 0L, tags = Nil, maybeOwner = Option.empty[String], note = "")
  val meaningful = build("journal", 12L, List("primary"), Some("owner"), "created")
  val domainValue = Item("journal", 12L)
