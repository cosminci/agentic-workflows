package fixture

import cats.{Applicative, ApplicativeError, Functor}
import cats.data.EitherT
import cats.effect.{IO, Sync}
import cats.effect.kernel.Temporal
import cats.effect.syntax.temporal.*
import cats.syntax.all.*
import scala.concurrent.duration.*
import scala.util.{Left as Failed}
import scala.util.Try

object PreferCatsSyntaxFixture:
  def factories(error: Throwable, result: Either[Throwable, Int]): Unit =
    val optional: Option[Int] = Some(1) // lint
    val success: Either[String, Int] = Right(1) // lint
    val failure: Either[String, Int] = Failed("failed") // lint
    val lifted = IO.fromEither(result) // lint
    val liftedOptional = IO.fromOption(none[Int])(error) // lint
    val liftedAttempt = IO.fromTry(Try(1)) // lint
    val raised = IO.raiseError[Int](error) // lint
    val pure = IO.pure(1) // lint
    val blocked = IO.blocking(1)
    val delayed = IO.delay(1)
    val scheduled = IO.pure(1).delayBy(1.second) // lint
    val recovered = result match
      case Right(value) => value.some
      case Left(_) => none[Int]
    val concrete: Some[Int] = Some(1) // lint
    val callback: Option[String => Int] = Some(value => value.length) // lint
    val nullable = Option(null: String) // lint
    val absent = Option.apply[String](null) // lint
    val custom = Local.Right(1)

  def generic[F[_]](using F: ApplicativeError[F, Throwable]): F[Int] =
    F.raiseError(new IllegalStateException) // lint

  def deferred[F[_]: Sync](operation: => Int): F[Int] =
    Sync[F].delay(operation)

  def timed(effect: IO[Int]): Unit =
    val infix = IO.sleep(1.second) *> effect // lint
    val dot = IO.sleep(1.second).*>(effect) // lint
    val lazyChain = IO.sleep(1.second) >> effect >> IO.sleep(2.seconds) // lint
    val lazyDot = IO.sleep(1.second).>>[Int](effect) // lint
    val standalone = IO.sleep(1.second)
    val after = effect *> IO.sleep(1.second)
    val lazyAfter = effect >> IO.sleep(1.second)
    val custom = Local.sleep(1.second) *> effect
    val withOtherOperator = IO.sleep(1.second).map(_ => 1)

  def timedGeneric[F[_]: Temporal](effect: F[Int]): Unit =
    val sequenced = Temporal[F].sleep(1.second) *> effect // lint
    val lazySequenced = Temporal[F].sleep(1.second) >> effect // lint
    val scheduled = effect.delayBy(1.second)

  def fromNullable(value: String): Option[String] = Option(value)

  def transformers[F[_]: Applicative](effect: F[Either[String, Int]], plain: F[Int], result: Either[String, Int]): Unit =
    val wrapped = EitherT(effect) // lint
    val lifted = EitherT.liftF[F, String, Int](plain) // lint
    val promoted = EitherT.fromEither[F](result) // lint
    val right = EitherT.rightT[F, String](1) // lint
    val left = EitherT.leftT[F, Int]("failed") // lint

  def valueSyntax[F[_]: Applicative](effect: F[Either[String, Int]], plain: F[Int], result: Either[Throwable, Int], error: Throwable): Unit =
    val lifted = result.liftTo[IO]
    val liftedOptional = none[Int].liftTo[IO](error)
    val liftedAttempt = Try(1).liftTo[IO]
    val raised = error.raiseError[IO, Int]
    val wrapped = effect.eitherT
    val liftedEffect = plain.lift[String]
    val callback: String => Int = _.length
    val optionalCallback = callback.some
    val absent = none[String]
    val scheduled = 1.pure[IO].delayBy(1.second)

  extension [F[_], E, A](value: F[Either[E, A]])
    def eitherT: EitherT[F, E, A] = EitherT(value)

  extension [F[_]: Functor, A](value: F[A])
    def lift[E]: EitherT[F, E, A] = EitherT.liftF(value)

  object Braced:
    extension [F[_], E, A](value: F[Either[E, A]])
      def eitherT: EitherT[F, E, A] = { EitherT(value) }

    extension [F[_]: Functor, A](value: F[A])
      def lift[E]: EitherT[F, E, A] = { EitherT.liftF(value) }

  object NotAnExtension:
    def eitherT[F[_], E, A](value: F[Either[E, A]]): EitherT[F, E, A] = { EitherT(value) } // lint

  object Local:
    def Right[A](value: A): A = value
    def sleep(duration: FiniteDuration): IO[Unit] = IO.sleep(duration)
