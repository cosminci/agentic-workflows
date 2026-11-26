package fixture

object SingleStepMapFixture:
  def transform(values: List[Int]): List[Int] = for value <- values yield value + 1

  def identity(values: List[Int]): List[Int] =
    for
      value <- values
    yield value

  def yieldUnit(values: List[Unit]): List[Unit] = for value <- values yield ()

  def guarded(values: List[Int]): List[Int] =
    for
      value <- values
      if value > 0
    yield value + 1

  def dependent(values: List[Int]): List[Int] =
    for
      value <- values
      divisor <- List(1, 2)
    yield value / divisor

  def trailingBinding(values: List[Int]): List[Int] =
    for
      value <- values
      doubled = value * 2
    yield doubled + 1

  def leadingBinding(values: List[Int]): List[Int] =
    for
      offset = 1
      value <- values
    yield value + offset

  def tuplePattern(values: List[(Int, Int)]): List[Int] = for (left, right) <- values yield left + right

  def casePattern(values: List[Option[Int]]): List[Int] = for case Some(value) <- values yield value + 1

  def wildcard(values: List[Int]): List[Int] = for _ <- values yield 1

  def foreach(values: List[Int])(consume: Int => Unit): Unit = for value <- values do consume(value)
