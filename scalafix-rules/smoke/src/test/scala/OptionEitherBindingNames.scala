package fixture

object OptionEitherBindingNamesFixture:
  extension [E, A](value: Either[E, A])
    def reverse: Either[A, E] = value.swap

  extension [A](value: Option[A])
    def orFail[E](ifEmpty: => E): Either[E, A] = value.toRight(ifEmpty)
    def orDefault(fallback: Option[A]): Option[A] = value.orElse(fallback)

  extension (label: Option[String])
    def render: String = label.getOrElse("")

  extension [A](parsed: Either[String, A])
    def message: Option[String] = parsed.swap.toOption

  def choose[A](value: Option[A], maybeDefault: Option[A]): Option[A] = value.orElse(maybeDefault)

  val label: Option[String] = None
  val maybeLabel: Option[String] = label
  val parsed: Either[String, Int] = Right(1)
  val parseResult: Either[String, Int] = parsed

  def renderAll(values: List[Option[Int]]): List[Int] =
    for entry <- values
    yield entry.getOrElse(0)

  def renderOptional(values: List[Option[Int]]): List[Int] =
    values.map(maybeEntry => maybeEntry.getOrElse(0))

  def collectAll(values: List[Either[String, Int]]): List[Int] =
    values.map(entry => entry.getOrElse(0))

  def collectResults(values: List[Either[String, Int]]): List[Int] =
    values.map(entryResult => entryResult.getOrElse(0))
