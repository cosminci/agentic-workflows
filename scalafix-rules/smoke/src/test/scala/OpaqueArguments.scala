package fixture:
  final case class Item(name: String, quantity: Long)

  object OpaqueArgumentsFixture:
    def measure(duration: Float, distance: Double) = duration -> distance
    def complete(result: Unit, name: String) = name
    def build(name: String, quantity: Long, tags: List[String], maybeOwner: Option[String], note: String): Item =
      Item(name, quantity)

    val failing = build("", 0L, Nil, Option.empty[String], "")
    val passing = build(name = "", quantity = 0L, tags = Nil, maybeOwner = Option.empty[String], note = "")
    val meaningful = build("journal", 12L, List("primary"), Some("owner"), "created")
    val domainValue = Item("journal", 12L)
    val formattedNumbers = measure(1_000.0f, 2_000.0)
    val zeroNumbers = measure(0.0f, 0.0)
    val positionalUnit = complete((), "journal")
    val namedUnit = complete(result = (), name = "journal")
    val rightUnit = Either.cond(true, (), "rejected")
    val leftUnit = Either.cond[Unit, String](true, "accepted", ())
    val namedCondUnit = Either.cond(true, right = (), left = "rejected")
    val nonUnitCond = Either.cond(true, 0, "")
    val libraryUnit = Tuple2((), "journal")
    val unitBinding = for () <- Option(()) yield "journal"

package first:
  object PassingFixture:
    val value = "first"

package second:
  object FailingFixture:
    private def build(name: String, count: Int) = name -> count
    val value = build("", 0)
