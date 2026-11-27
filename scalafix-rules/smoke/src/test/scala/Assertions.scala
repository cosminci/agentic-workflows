class Assertions extends munit.FunSuite:
  test("single line"):
    assertEquals(List(1), List(1))

  test("multiline comparison"):
    assertEquals( // assertion
      List(1),
      List(1)
    )

  test("multiline exception assertion"):
    intercept[IllegalArgumentException] { // exception
      throw new IllegalArgumentException("operation failed")
    }

  test("renamed assertions"):
    import munit.Assertions.{assertEquals as check, interceptMessage as expect}
    check( // assertion
      1,
      1
    )
    expect[IllegalArgumentException]("operation failed") { // exception
      throw new IllegalArgumentException("operation failed")
    }

  test("other MUnit assertions"):
    assert( // assertion
      true
    )
    assertNotEquals( // assertion
      1,
      2
    )
    assertNoDiff( // assertion
      "same",
      "same"
    )
