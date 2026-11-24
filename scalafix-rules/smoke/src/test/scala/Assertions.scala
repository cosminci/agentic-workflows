class Assertions extends munit.FunSuite:
  test("single line"):
    assertEquals(List(1), List(1))

  test("multiline comparison"):
    assertEquals(
      List(1),
      List(1)
    )

  test("multiline exception assertion"):
    intercept[IllegalArgumentException] {
      throw new IllegalArgumentException("operation failed")
    }
