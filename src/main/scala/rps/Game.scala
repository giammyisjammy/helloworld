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
    if (result == Winner.Draw) "It's a draw ✍️"
    else if (result == Winner.User) s"You win 🎉"
    else "You lose 🤷"
  }

  def checkWinner(user: String, cpu: String): String = {
    if (user == cpu) {
      Winner.Draw
    } else if (
      (user == Weapon.Rock && cpu == Weapon.Scissors)
      || (user == Weapon.Paper && cpu == Weapon.Rock)
      || (user == Weapon.Scissors && cpu == Weapon.Paper)
    ) {
      Winner.User
    } else {
      // also covers the case for an invalid weapon (e.g. user inputs "4")
      Winner.Cpu
    }
  }

  private val r = scala.util.Random

  private def generateComputerMove(): String =
    r.nextInt(3).toString
}
