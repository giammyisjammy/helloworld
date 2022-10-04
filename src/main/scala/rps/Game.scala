package rps

import scala.io.StdIn.readLine
import scala.util.Random
import rps.models._
import rps.models.Move.Paper
import rps.models.Move.Rock
import rps.models.Move.Scissors

object Game {

  def play(): Unit = {
    val menu = Move.moves
      .map(m => s"${Move.encode(m)} - ${Move.print(m)}")
      .mkString("\n")
    println("Choose your weapon:")
    println(menu)

    val rawUserInput = readLine()
    val userMove = Move.decode(rawUserInput)
    val cpuMove = generateComputerMove()

    val printedUserMove =
      userMove.map(Move.print).getOrElse("💩 An invalid weapon")
    val printedCpuMove = Move.print(cpuMove)
    println(
      s"You chose: ${printedUserMove} | your opponent chose: ${printedCpuMove}"
    )

    val result = checkWinner(userMove, cpuMove)
    println(announceResult(result))
  }

  def announceResult(result: GameResult): String = {
    result match {
      case GameResult.Draw     => "It's a draw ✍️"
      case GameResult.UserWins => "You win 🎉"
      case GameResult.CpuWins  => "You lose 🤷"
      case GameResult.DumbUser =>
        "You lose 🤷 (next time choose a valid weapon)"
    }
  }

  def checkWinner(
      userMove: Option[Move],
      computerMove: Move
  ): GameResult = {
    (userMove, computerMove) match {
      case (Some(x), y) =>
        (x, y) match {
          case (x, y) if x == y            => GameResult.Draw
          case (Move.Rock, Move.Scissors)  => GameResult.UserWins
          case (Move.Paper, Move.Rock)     => GameResult.UserWins
          case (Move.Scissors, Move.Paper) => GameResult.UserWins
          case _                           => GameResult.CpuWins
        }
      case (None, _) => GameResult.DumbUser
    }
  }

  private val r = scala.util.Random

  private def generateComputerMove(): Move =
    r.shuffle(Move.moves).head
}
