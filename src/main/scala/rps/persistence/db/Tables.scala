package rps.db

import java.sql.Timestamp
import java.util.UUID
import slick.jdbc.PostgresProfile.api._

object Tables {

  // instances of Row will each represent one row in the table
  // list all fields with their types in Scala, for example:
  case class Row(
      id: UUID,
      userMove: String,
      computerMove: String,
      result: String,
      occurredAt: Timestamp
  )

  class GameHistory(tag: Tag) extends Table[Row](tag, Some("rps"), "history") {

    // define all columns, for example:
    def id = column[UUID]("id", O.PrimaryKey)
    def userMove = column[String]("user_move")
    def computerMove = column[String]("computer_move")
    def result = column[String]("result")
    def occurredAt = column[Timestamp]("occurred_at")

    // list all columns (but dunno what this syntax is O.o)
    def * = (
      id,
      userMove,
      computerMove,
      result,
      occurredAt
    ) <> (Row.tupled, Row.unapply)

  }
  lazy val Match = TableQuery[GameHistory]
}
