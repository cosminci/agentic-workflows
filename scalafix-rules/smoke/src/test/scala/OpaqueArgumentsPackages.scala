package first:
  object PassingFixture:
    val value = "first"

package second:
  object FailingFixture:
    private def build(name: String, count: Int) = name -> count
    val value = build("", 0)
