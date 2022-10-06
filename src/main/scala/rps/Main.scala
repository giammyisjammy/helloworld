package rps

import scala.util.{Success, Failure}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration
import scala.language.postfixOps

object Main extends App {
  implicit val ec: ExecutionContext = ExecutionContext.global

  FlywayMigrations.applyMigrations

  // TODO probabilmente cambia il tipo di ritorno di `play()`, c'è da risolvere questo
  // type mismatch;
  //  found   : Unit
  //  required: scala.concurrent.Awaitable[?]
  Await.result(Game.play(), Duration.Inf)
  
  // Game.play() // OLD
  // ^^^ Suppongo che questo sia inutile anche se `applyMigrations` ritorna
  // uno Unit sincronamente...mah
}
