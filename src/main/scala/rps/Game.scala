package rps

import scala.io.StdIn.readLine
import scala.util.Random
import rps.models._
import rps.models.Move.{Rock, Paper, Scissors}
import rps.models.GameResult.{CpuWins, Draw, DumbUser, UserWins}

object Game {

  def play(): Unit = {
    val menu = Move.values
      .map(m => s"${MoveEncoder.encode(m)} - ${MoveEncoder.print(m)}")
      .mkString("\n")
    println("Choose your weapon:")
    println(menu)

    val rawUserInput = readLine()
    val userMove = MoveEncoder.decode(rawUserInput)
    val cpuMove = generateComputerMove()

    val printedUserMove =
      userMove.map(MoveEncoder.print).getOrElse("💩 An invalid weapon")
    val printedCpuMove = MoveEncoder.print(cpuMove)
    println(
      s"You chose: ${printedUserMove} | your opponent chose: ${printedCpuMove}"
    )

    val result = checkWinner(userMove, cpuMove)
    println(announceResult(result))
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

  private val r = scala.util.Random

  private def generateComputerMove(): Move =
    r.shuffle(Move.values).head
}
