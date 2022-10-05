package rps

import scala.io.StdIn.readLine
import scala.util.Random
import rps.models._
import rps.models.Move.{Rock, Paper, Scissors}
import rps.models.GameResult.{CpuWins, Draw, DumbUser, UserWins}
import io.buildo.enumero.{CaseEnumIndex, CaseEnumSerialization}

object Game {
  def play(): Unit = {
    val menu = moves
      .map(m => s"${CaseEnumSerialization[Move].caseToString(m)} - ${printMove(m)}")
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
      case Move.Rock    => "🪨 Rock"
      case Move.Paper     => "📄 Paper"
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
}
