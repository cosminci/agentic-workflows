package fixture

object NoMixedForComprehensionBindingsFixture:
  def betweenGenerators(values: List[Int]): List[Int] =
    for
      value <- values
      doubled = value * 2 // lint
      increment <- List(1, 2)
      total = doubled + increment // lint
    yield total

  def leadingBinding(values: List[Int]): List[Int] =
    for
      increment = 2 // lint
      value <- values
    yield value + increment

  def foreach(values: List[Int], emit: Int => Unit): Unit =
    for (value <- values; doubled = value * 2) do emit(doubled) // lint

  def guardsAndNested(values: List[Int]): List[Int] =
    for
      value <- values
      if value > 0
      result <- {
        val offset = 1
        for
          delta <- values
          adjusted = delta + offset // lint
        yield adjusted
      }
    yield result

  def generatorPattern(values: List[(Int, Int)]): List[Int] =
    for
      (left, right) <- values
      total <- List(left + right)
    yield total
