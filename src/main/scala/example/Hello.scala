package example

import cats.effect._
import cats.syntax.all._
import org.http4s.{HttpApp, HttpRoutes}
import org.http4s.implicits.http4sKleisliResponseSyntaxOptionT
import org.http4s.server.{Router, Server}
import org.http4s.server.blaze.BlazeServerBuilder
import sttp.tapir._
import sttp.tapir.server.http4s.Http4sServerInterpreter

import scala.concurrent.ExecutionContext

object Hello extends IOApp {

  val helloWorld: Endpoint[String, Unit, String, Any] =
    endpoint
      .get
      .in("hello")
      .in(query[String]("name"))
//      .in(query[Option[Int]]("age"))
      .out(stringBody)

  def endpointLogic(name: String): IO[Either[Unit, String]] = IO(s"Hejka, $name".asRight[Unit])

  val helloRoutes: HttpRoutes[IO] =
    Http4sServerInterpreter
      .toRoutes(helloWorld)(logic = endpointLogic _)

  val httpApp: HttpApp[IO] = Router("/" -> helloRoutes).orNotFound

  def serveResource(routes: HttpRoutes[IO])(ec: ExecutionContext): Resource[IO, Server[IO]] =
    BlazeServerBuilder[IO](ec)
      .bindHttp(12021, "localhost")
      .withHttpApp(httpApp)
      .resource

  override def run(args: List[String]): IO[ExitCode] =
//    IO.pure(ExitCode.Success)
    serveResource(helloRoutes)(executionContext)
      .use(_ => IO.never)
      .as(ExitCode.Success)

}
