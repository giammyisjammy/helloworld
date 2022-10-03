package rps

import scala.io.StdIn.readLine
import scala.util.Random

object Game {

  sealed trait Move
  object Move {
    case object Rock extends Move // 🪨
    case object Paper extends Move // 📄
    case object Scissors extends Move // ✂️

    def decode(input: String): Option[Move] = {
      input match {
        case "0" => Some(Move.Rock)
        case "1" => Some(Move.Paper)
        case "2" => Some(Move.Scissors)
        case _   => None // Is this right??
      }
    }
    def encode(input: Move): String = {
      input match {
        case Move.Rock     => "0"
        case Move.Paper    => "1"
        case Move.Scissors => "2"
        case _             => "Invalid weapon" // Is this right??
      }
    }
  }

  sealed trait GameResult
  object GameResult {
    case object Draw extends GameResult
    case object UserWins extends GameResult
    case object CpuWins extends GameResult
  }

  def play(): Unit = {
    println(s"""Choose your weapon:
    ${Move.encode(Move.Rock)} - ${matchMove(Some(Move.Rock))}
    ${Move.encode(Move.Paper)} - ${matchMove(Some(Move.Paper))}
    ${Move.encode(Move.Scissors)} - ${matchMove(Some(Move.Scissors))}
    """)
    val rawUserInput = readLine()
    val userMove = Move.decode(rawUserInput)
    val cpuMove = generateComputerMove()

    val result = checkWinner(userMove, cpuMove)

    println(
      s"You chose: ${matchMove(userMove)} | your opponent chose: ${matchMove(cpuMove)}"
    )
    println(announceWinner(result))
  }

  def matchMove(move: Option[Move]): String = {
    move.orNull match {
      case Move.Rock     => "🪨 Rock"
      case Move.Paper    => "📄 Paper"
      case Move.Scissors => "✂️ Scissors"
      case _             => "💩 An invalid weapon"
    }
  }

  def announceWinner(result: GameResult): String = {
    result match {
      case GameResult.Draw     => "It's a draw ✍️"
      case GameResult.UserWins => "You win 🎉"
      case _                   => "You lose 🤷"
    }
  }

  def checkWinner(
      userMove: Option[Move],
      computerMove: Option[Move]
  ): GameResult = {
    (userMove.get, computerMove.get) match { // Is this right?? Using .get
      case (x, y) if (x == y)          => GameResult.Draw
      case (Move.Rock, Move.Scissors)  => GameResult.UserWins
      case (Move.Paper, Move.Rock)     => GameResult.UserWins
      case (Move.Scissors, Move.Paper) => GameResult.UserWins
      case _                           => GameResult.CpuWins
      // default case also handles an invalid weapon (e.g. user inputs "4")
    }
  }

  private val r = scala.util.Random

  private def generateComputerMove(): Option[Move] =
    Move.decode(r.nextInt(3).toString)
}
