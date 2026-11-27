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

  type Optional[A] = Option[A]
  type Outcome[A] = Either[String, A]
  opaque type Marker = Option[Int]

  extension [A](value: Optional[A])
    def isPresent: Boolean = value.isDefined

  extension [A](value: Outcome[A])
    def successful: Boolean = value.isRight

  def fromLazy(value: => Optional[Int]): Int = value.getOrElse(0)
  def fromLazyResult(value: => Either[String, Int]): Int = value.getOrElse(0)
  val inferred = Some(3)
  val missing = None
  val success = Right(3)
  val failure = Left("failed")
  val maybeInferred = Some(3)
  val marker: Marker = None

  def renderAll(values: List[Option[Int]]): List[Int] =
    for entry <- values
    yield entry.getOrElse(0)

  def renderOptional(values: List[Option[Int]]): List[Int] =
    values.map(maybeEntry => maybeEntry.getOrElse(0))

  def collectAll(values: List[Either[String, Int]]): List[Int] =
    values.map(entry => entry.getOrElse(0))

  def collectResults(values: List[Either[String, Int]]): List[Int] =
    values.map(entryResult => entryResult.getOrElse(0))
