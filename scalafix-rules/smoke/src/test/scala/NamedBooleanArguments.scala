package fixture

import java.util.concurrent.atomic.AtomicBoolean

final case class Toggle(name: String, enabled: Boolean)
final class Schedule(name: String, enabled: Boolean, repeated: Boolean)

object NamedBooleanArgumentsFixture:
  private def configure(name: String, enabled: Boolean) = Toggle(name, enabled)
  private def enable(enabled: Boolean) = enabled
  private def curried(name: String)(enabled: Boolean) = Toggle(name, enabled)

  val positional = configure("feature", true)
  val factory = Toggle("feature", false)
  val constructor = new Schedule("feature", true, false)
  val ascribed = configure("feature", (false: Boolean))

  val named = configure("feature", enabled = true)
  val namedConstructor = new Schedule("feature", enabled = true, repeated = false)
  val featureEnabled = true
  val value = configure("feature", featureEnabled)
  val unary = enable(false)
  val separateClauses = curried("feature")(true)
  val tuple = ("feature", true)
  val libraryCall = new AtomicBoolean().compareAndSet(false, true)
  val either = Either.cond(true, "yes", "no")

  def localExample() =
    def local(name: String, enabled: Boolean) = Toggle(name, enabled)
    local("feature", true)
