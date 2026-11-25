package fixture.adapters

import io.circe.Encoder

object WireAdapter:
  given exposed: Encoder[String] = Encoder.encodeString
  private given internal: Encoder[Int] = Encoder.encodeInt
  private[adapters] val packageVisible = Encoder.encodeLong
  private[WireAdapter] val ownerVisible = Encoder.encodeBoolean

private[adapters] object SharedWire:
  val shared = Encoder.encodeString
