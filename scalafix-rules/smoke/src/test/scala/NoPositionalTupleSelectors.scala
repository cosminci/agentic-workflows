package fixture

object NoPositionalTupleSelectorsFixture:
  final case class NamedField(_1: String)

  def label(rows: List[(String, Int)]): List[String] =
    rows.map(_._1)

  def count(rows: List[(String, Int)]): List[Int] =
    rows.map(row => row._2)

  def countThird(rows: List[Option[(String, String, Int)]]): List[Int] =
    rows.collect { case Some(row) if row._3 > 0 => row._3 }

  def named(rows: List[(String, Int)]): List[String] =
    rows.map { case (label, _) => label }

  def notATuple(rows: List[NamedField]): List[String] =
    rows.map(_._1)

  def outsideCallback(row: (String, Int)): String =
    row._1

  def selectorAsArgument(row: (String, Int)): Option[String] =
    Option(row._1)

  def namedArgument(rows: List[(String, Int)]): List[String] =
    rows.map(f = row => row._1)

  def ascribed(rows: List[(String, Int)]): List[String] =
    rows.map((row => row._1): ((String, Int)) => String)

  def contextual(use: (((String, Int)) ?=> String) => String): String =
    use((row: (String, Int)) ?=> row._1)

  def withSetup(rows: List[(String, Int)], prefix: String): List[String] =
    rows.map {
      val label = prefix.trim
      row => label + row._1
    }

  def storedCallback(rows: List[(String, Int)]): List[String] =
    rows.map {
      val render: ((String, Int)) => String = row => row._1
      render
    }
