package rps

import scala.io.StdIn.readLine
import scala.util.Random

object Game {

  // TODO this should be an enum (or Scala equivalent of TS Enum)
  object Weapon {
    val Rock: String = "0" // 🪨
    val Paper: String = "1" // 📄
    val Scissors: String = "2" // ✂️
  }

  // TODO this should be an enum (or Scala equivalent of TS Enum)
  object Winner {
    val Draw: String = "0" // It's a draw
    val User: String = "1" // User wins
    val Cpu: String = "2" // Cpu wins
  }

  def play(): Unit = {
    println(s"""Choose your weapon:
    ${Weapon.Rock} - 🪨 Rock
    ${Weapon.Paper} - 📄 Paper
    ${Weapon.Scissors} - ✂️ Scissors
    """)
    val rawUserInput = readLine()
    val userWeapon = rawUserInput
    val cpuWeapon = generateComputerMove()

    val result = checkWinner(userWeapon, cpuWeapon)

    println(s"You chose: ${userWeapon} | your opponent chose: ${cpuWeapon}")
    println(announceWinner(result))
  }

  def announceWinner(result: String): String = {
    result match {
      case Winner.Draw => "It's a draw ✍️"
      case Winner.User => "You win 🎉"
      case _           => "You lose 🤷"
    }
  }

  def checkWinner(userMove: String, computerMove: String): String = {
    (userMove, computerMove) match {
      case (x, y) if (x == y)              => Winner.Draw
      case (Weapon.Rock, Weapon.Scissors)  => Winner.User
      case (Weapon.Paper, Weapon.Rock)     => Winner.User
      case (Weapon.Scissors, Weapon.Paper) => Winner.User
      case _                               => Winner.Cpu  // also covers the case for an invalid weapon (e.g. user inputs "4")
    }
  }

  private val r = scala.util.Random

  private def generateComputerMove(): String =
    r.nextInt(3).toString
}
