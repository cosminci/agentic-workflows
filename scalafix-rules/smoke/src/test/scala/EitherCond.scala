package fixture

object EitherCondFixture:
  val positional = Either.cond(
    true,
    1,
    "failed"
  )

  val named = Either.cond[String, Int](
    test = true,
    right = 1,
    left = "failed"
  )

  import scala.util.Either.{cond as choose}
  val renamed = choose(
    true,
    1,
    "failed"
  )

  val nested = Option(Either.cond(
    true,
    1,
    "failed"
  ))

  val closingDelimiter = Either.cond(true, 1, "failed"
  )

  val singleLine = Either.cond(true, 1, "failed")
  val chained = Either.cond(true, 1, "failed").map: value =>
    value + 1

  object Shadowed:
    object Either:
      def cond[A, B](test: Boolean, right: => B, left: => A): scala.util.Either[A, B] =
        if test then Right(right) else Left(left)

    val custom = Either.cond(
      true,
      1,
      "failed"
    )

  object DirectImport:
    import scala.util.Either.cond

    val direct = cond(
      true,
      1,
      "failed"
    )

  val innerBlock = Either.cond(true, {
    val value = 1
    value + 1
  }, "failed")

  val outerBlock = Option(
    Either.cond(true, 1, "failed")
  )

  val followedByFlatMap = Either.cond(true, 1, "failed")
    .flatMap(value => Right(value + 1))
