package rps

import scala.concurrent.{Future, ExecutionContext}
import scala.concurrent.duration._
import scala.io.StdIn.readLine
import scala.util.Random
import slick.jdbc.JdbcBackend.{Database, DatabaseDef}
import rps.models.{Move, Play, GameResult}
import rps.models.Move.{Rock, Paper, Scissors}
import rps.models.GameResult.{CpuWins, Draw, DumbUser, UserWins}
import io.buildo.enumero.{CaseEnumIndex, CaseEnumSerialization}

object Game {

  implicit val ec = ExecutionContext.global

  val database = Database.forConfig("db")
  val gameRepository = GameRepository.create(database)

  def play(): Future[Unit] =
    for { // TODO da capire meglio questa sintassi
      _ <- printLastGameMessage
      maybePlay = playRound()
      _ <- maybePlay match {
        case None       => Future.successful(())
        case Some(play) => gameRepository.save(play)
      }
    } yield ()

  def playRound(): Option[Play] = {
    val menu = moves
      .map(m => s"${CaseEnumIndex[Move].caseToIndex(m)} - ${printMove(m)}")
      .mkString("\n")
    println("Choose your weapon:")
    println(menu)

    val rawUserInput = readLine()
    val userMove = CaseEnumIndex[Move].caseFromIndex(rawUserInput)
    val cpuMove = generateComputerMove()

    val printedUserMove =
      userMove.map(printMove).getOrElse("💩 An invalid weapon")
    val printedCpuMove = printMove(cpuMove)
    println(
      s"You chose: ${printedUserMove} | your opponent chose: ${printedCpuMove}"
    )

    val result = checkWinner(userMove, cpuMove)
    println(announceResult(result))

    // type mismatch;(!) why???
    //   found   : Option[rps.models.Move]
    //   required: rps.models.Movebloop
    // result match {
    //   case CpuWins  => Some(Play(userMove, cpuMove, result))
    //   case UserWins => Some(Play(userMove, cpuMove, result))
    //   case Draw     => Some(Play(userMove, cpuMove, result))
    //   case DumbUser => None
    // }
    userMove match {
      case Some(value) => Some(Play(value, cpuMove, result))
      case None        => None
    }
  }

  def announceResult(result: GameResult): String = {
    result match {
      case Draw     => "It's a draw ✍️"
      case UserWins => "You win 🎉"
      case CpuWins  => "You lose 🤷"
      case DumbUser =>
        "You lose 🤷 (next time choose a valid weapon)"
    }
  }

  def printMove(input: Move): String = {
    input match {
      case Move.Rock     => "🪨 Rock"
      case Move.Paper    => "📄 Paper"
      case Move.Scissors => "✂️ Scissors"
    }
  }

  def checkWinner(
      userMove: Option[Move],
      computerMove: Move
  ): GameResult = {
    userMove match {
      case Some(x) =>
        (x, computerMove) match {
          case (Rock, Rock) | (Paper, Paper) | (Scissors, Scissors) => Draw
          case (Rock, Scissors) | (Paper, Rock) | (Scissors, Paper) => UserWins
          case (Rock, Paper) | (Paper, Scissors) | (Scissors, Rock) => CpuWins
        }
      case None => DumbUser
    }
  }

  private val moves = List(Rock, Paper, Scissors)

  private val r = scala.util.Random

  private def generateComputerMove(): Move =
    r.shuffle(moves).head

  private def printLastGameMessage: Future[Unit] = {
    gameRepository
      .readLastMatch()
      .map(x =>
        x match {
          case None =>
            println("No previous results found")
          case Some(lastPlay) =>
            val result = lastPlay.result match {
              case UserWins => "🎉 You won"
              case Draw     => "✍️ Draw"
              case CpuWins  => "🤷 You lost"
              case DumbUser => "You chose a wrong weapon and you lost"
            }
            val userMove = printMove(lastPlay.userMove)
            val computerMove = printMove(lastPlay.computerMove)
            val message =
              s"LAST ROUND: $result (Your move: $userMove. Computer move: $computerMove)"
            println(message)
        }
      )
  }
}
