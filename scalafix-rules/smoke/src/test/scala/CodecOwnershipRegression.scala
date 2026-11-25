package fixture.adapters

import io.circe.{Decoder, Encoder}

object RequestAdapter:
  val requestDecoder: Decoder[String] = Decoder.decodeString
  private[adapters] val responseEncoder: Encoder[String] = Encoder.encodeString
  private[RequestAdapter] val confinedEncoder: Encoder[Int] = Encoder.encodeInt

object PublicSharedWire:
  val decoder: Decoder[String] = Decoder.decodeString
