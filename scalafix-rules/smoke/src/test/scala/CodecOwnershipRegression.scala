package fixture.adapters

import io.circe.{Decoder, Encoder}

object RequestAdapter:
  val requestDecoder: Decoder[String] = Decoder.decodeString
  private[adapters] val responseEncoder: Encoder[String] = Encoder.encodeString
  private[RequestAdapter] val confinedEncoder: Encoder[Int] = Encoder.encodeInt

  def describe(codec: Encoder[Int]): String = codec.toString
  private var internal = Encoder.encodeInt
  private val (numberEncoder, textEncoder) = (Encoder.encodeLong, Encoder.encodeString)
  val (publicEncoder, label) = (Encoder.encodeInt, "number")
  val structured = Encoder.AsObject.instance[String](_ => io.circe.JsonObject.empty)
  val domainEncoder = new DomainEncoder

  final class DomainEncoder extends Encoder[Int]:
    def apply(value: Int): io.circe.Json = io.circe.Json.fromInt(value)

  final class Dependencies(val encoder: Encoder[Int])
  final class HiddenDependencies(private val encoder: Encoder[Int])

  private[adapters] object Shared:
    val number = Encoder.encodeInt

object PublicSharedWire:
  val decoder: Decoder[String] = Decoder.decodeString
