package rps

import scala.io.StdIn.readLine
import scala.util.Random
import rps.models._

object Game {

  def play(): Unit = {
    val menu = Move.moves
      .map(m => s"${Move.encode(m)} - ${matchMove(Some(m))}")
      .mkString("\n")
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
      case (x, y) if x == y            => GameResult.Draw // BUG? Both user and cpu use invalid weapons = draw
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
