package fixtures

object ScalaTestAssertions extends org.scalatest.matchers.should.Matchers:
  def checks(): Unit =
    import org.scalatest.Assertions.{assert as check}

    assertResult(1) { // assertion
      1
    }
    check( // assertion
      true
    )
    assertThrows[IllegalArgumentException] { // exception
      throw new IllegalArgumentException
    }
    List(1) should equal ( // assertion
      List(1)
    )
    List(1).shouldBe( // assertion
      List(1)
    )
    List(1) should not equal ( // assertion
      List(2)
    )
    List(1) should be ( // assertion
      List(1)
    )
    List(1) should equal (List(1))
    check(true)

object ScalaTestMust extends org.scalatest.matchers.must.Matchers:
  def checks(): Unit =
    List(1) mustEqual ( // assertion
      List(1)
    )
    List(1) must equal (List(1))

object ProjectAssertions:
  def check(value: Boolean): Unit = ()
  def expectFailure(operation: => Unit): Unit = ()
  def notRegistered(value: String): Unit = ()

object ProjectAssertionCalls:
  import ProjectAssertions.{check as verify, expectFailure, notRegistered}

  def checks(): Unit =
    verify( // registered assertion
      true
    )
    expectFailure { // registered exception
      ()
    }
    notRegistered(
      "control"
    )
    verify(true)

object UnrelatedAssertions:
  def assertEquals(first: Int, second: Int): Unit = ()
  def intercept[A](body: => Unit): Unit = ()
  extension (value: Int)
    infix def shouldBe(expected: Int): Unit = ()
    infix def must(expected: Int): Unit = ()

  def checks(): Unit =
    assertEquals(
      1,
      1
    )
    intercept[Exception] {
      ()
    }
    1 shouldBe (
      1
    )
    1 must (
      1
    )
    Predef.assert(
      true
    )
