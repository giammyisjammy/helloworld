package rps

import scala.io.StdIn.readLine
import scala.util.Random

object Game {

  sealed trait Move
  object Move {
    case object Rock extends Move // 🪨
    case object Paper extends Move // 📄
    case object Scissors extends Move // ✂️
    val moves = List(Rock, Paper, Scissors)

    // Idea is similar to a fp-ts Encoder, I dunno if it's the right place here
    def decode(input: String): Option[Move] = {
      input match {
        case "0" => Some(Move.Rock)
        case "1" => Some(Move.Paper)
        case "2" => Some(Move.Scissors)
        case _   => None
      }
    }
    // Idea is similar to a fp-ts Decoder, I dunno if it's the right place here
    def encode(input: Move): String = { // TODO should it also accept an Option[Move]?
      input match {
        case Move.Rock     => "0"
        case Move.Paper    => "1"
        case Move.Scissors => "2"
        case _ => "Invalid weapon" // Is this right?? Will it be ever used?
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
    val menu = Move.moves
      .map(m => s"${Move.encode(m)} - ${matchMove(Some(m))}")
      .reduce((acc, curr) => s"${acc}\n${curr}")
    println("Choose your weapon:")
    println(menu)

    val rawUserInput = readLine()
    val userMove = Move.decode(rawUserInput)
    val cpuMove = generateComputerMove()

    val result = checkWinner(userMove, cpuMove)

    println(
      s"You chose: ${matchMove(userMove)} | your opponent chose: ${matchMove(cpuMove)}"
    )
    println(announceResult(result))
  }

  def matchMove(move: Option[Move]): String = {
    move.orNull match {
      case Move.Rock     => "🪨 Rock"
      case Move.Paper    => "📄 Paper"
      case Move.Scissors => "✂️ Scissors"
      case _             => "💩 An invalid weapon"
    }
  }

  def announceResult(result: GameResult): String = {
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
    (userMove.orNull, computerMove.orNull) match {
      case (x, y) if (x == y)          => GameResult.Draw // BUG? Both user and cpu use invalid weapons = draw
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
