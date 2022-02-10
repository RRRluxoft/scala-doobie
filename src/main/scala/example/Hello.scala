package example

import cats.effect.{ExitCode, IO, IOApp}
import sttp.tapir.{endpoint, query, stringBody, Endpoint}

object Hello extends IOApp {

  val helloWorld: Endpoint[String, Unit, String, Any] =
    endpoint
      .get
      .in("hello")
      .in(query[String]("name"))
      .out(stringBody)

  override def run(args: List[String]): IO[ExitCode] = IO.pure(ExitCode.Success)
}
