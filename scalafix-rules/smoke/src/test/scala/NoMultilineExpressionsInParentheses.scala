object NoMultilineExpressionsInParentheses {
  case class Book(title: String)
  case class Edition(year: Int)
  case class ManualInput(book: Book, edition: Edition, recording: Option[String])

  def make[F[_]](
      client: F[String],
      settings: Int
  ): (F[String], Int) = (client, settings)

  val wrappedArguments = ManualInput(
    Book(title = "Frankenstein"),
    /* A comment, not an expression:
       unmatched delimiters ) } ] */
    Edition(year = 1818),
    recording = None
  )

  val wrappedTuple = (
    Book(title = "Frankenstein"),
    Edition(year = 1818),
    Option.empty[String]
  )

  val (
    book,
    edition,
    recording
  ) = wrappedTuple

  val shortLambda = List(1).map(n => n + 1)
  val condition = if (edition.year > 1800) true else false
  val bracedLambda = List(1).map { n =>
    val next = n + 1
    next * 2
  }
  val colonLambda = List(1).map: n =>
    val next = n + 1
    next * 2
  val interpolation = Book(title = s"${List(1).map(n => n + 1).head}")
  val literalArgument = Book(title = """Frankenstein;
    or, The Modern Prometheus""")
  val interpolatedArgument = Book(title = s"""${book.title}
    or, The Modern Prometheus""")
  extension (context: StringContext)
    def sql(values: Any*): String = context.s(values*)

  val multilineSql = Book(title = sql"""SELECT title // lint
    FROM books WHERE title = '${book.title}'""")
  val sqlQuery = sql"""SELECT title
    FROM books WHERE title = '${book.title}'"""
  val extractedSql = Book(title = sqlQuery)

  val nestedJson = io.circe.Json.obj(
    "interior" -> io.circe.Json.obj(
      "title" -> io.circe.Json.fromString(book.title),
      "year" -> io.circe.Json.fromInt(edition.year)
    )
  )

  val nestedJsonWithLogic = io.circe.Json.obj(
    "interior" -> io.circe.Json.obj(
      "year" -> io.circe.Json.fromInt(if (condition) edition.year // lint
        else 1818)
    )
  )

  object endpoint {
    def handle(run: Int => Int): Int = run(1)
  }

  def serverEndpoints: List[Int] =
    List(
      endpoint.handle: n =>
        val next = n + 1
        next * 2,
      endpoint.handle { n =>
        val next = n + 2
        next * 2
      },
      endpoint.handle: n =>
        List(n).map(value => // lint
          value + 1
        ).head
    )

  def otherEndpoints: List[Int] =
    List(
      endpoint.handle: n => // lint
        n + 1
    )

  object OtherApi {
    def serverEndpoints: List[Int] =
      List(
        endpoint.handle(n => // lint
          n + 1
        )
      )
  }
  val literalWithMultilineLogic = Book(title = s"""${if (condition) book.title // lint
    else "Dracula"}
    A novel""")
  val bracelessComprehension =
    for
      n <-
        List(1)
      if n > 0
    yield n

  val lambdaArgument = List(1).map(n => // lint
    n + 1
  )

  def foldArgument(savedResult: Either[String, Int], report: String => Unit): Int =
    savedResult.fold(
      _ => 0,
      periods => // lint
        report("dormancy edited")
        periods
    )

  val bracedArgument = List(1).map({ n => // lint
    val next = n + 1
    next * 2
  })

  val nestedBraceCall = Option(List(1)).map(_.map { n => // lint
    val next = n + 1
    next * 2
  })

  val nestedColonCall = Option(List(1)).map(_.map: n => // lint
    n + 1)

  val nestedConstructor = ManualInput(
    book = Book( // lint
      title = "Frankenstein"
    ),
    edition,
    recording
  )

  val matchArgument = Book(title = edition.year match // lint
    case 1818 => "Frankenstein"
    case _    => "Dracula")

  val firstArgument = math.max(
    if (condition) 10 // lint
    else 20,
    30
  )

  val middleArgument = ManualInput(
    book,
    if (condition) edition // lint
    else Edition(year = 1831),
    recording
  )

  val lastArgument = ManualInput(
    book,
    edition,
    recording = if (condition) None // lint
    else Some("recording")
  )

  val tupleElement = (
    book,
    if (condition) edition // lint
    else Edition(year = 1831)
  )

  val grouped = (
    if (condition) book // lint
    else Book(title = "Dracula")
  )

  val nestedGrouping = math.max(1, ((
    if (condition) 2 // lint
    else 3
  )))

  val multilineCondition = if (
    condition && // lint
    recording.isEmpty
  ) book else Book(title = "Dracula")

  val comprehension = for (
    n <- // lint
      List(1);
    if n > 0
  ) yield n
}
