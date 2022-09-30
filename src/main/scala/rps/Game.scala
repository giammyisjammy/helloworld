package rps

import scala.io.StdIn.readLine
import scala.util.Random

object Game {

  // TODO this should be an enum (or Scala equivalent of TS Enum)
  object Weapon {
    val Rock: Int = 0 // 🪨
    val Paper: Int = 1 // 📄
    val Scissor: Int = 2 // ✂️
    val Invalid: Int = -1 // Something bad happened
  }

  // TODO this should be an enum (or Scala equivalent of TS Enum)
  object Winner {
    val Draw: Int = 0 // It's a draw
    val User: Int = 1 // User wins
    val Cpu: Int = 2 // Cpu wins
    val Invalid: Int = -1 // Something bad happened
  }

  def play(): Unit = {
    println(s"""Choose your weapon:
    ${Weapon.Rock} - 🪨 Rock
    ${Weapon.Paper} - 📄 Paper
    ${Weapon.Scissor} - ✂️ Scissor
    """)
    val rawUserInput = readLine()
    val userWeapon = parseWeapon(rawUserInput)
    val cpuWeapon = parseWeapon(generateComputerMove())

    if (userWeapon != Weapon.Invalid) {
      println(s"Your opponent chose ${cpuWeapon}")

      val result = checkWinner(userWeapon, cpuWeapon)
      announceWinner(result)
    } else {
      println("Not a valid weapon, Spock!")
    }
  }

  def announceWinner(result: Int): Unit = {
    val winner =
      if (result == Winner.Cpu) "Cpu"
      else if (result == Winner.User) "User"
      else "dafuq"
    val emoji =
      if (result == Winner.Cpu) "🤷"
      else if (result == Winner.User) "🎉"
      else "😱"

    val message =
      if (result == Winner.Draw) "It's a draw ✍️"
      else s"${winner} Wins ${emoji}"
    if (result != Winner.Invalid) println(message)
  }

  def checkWinner(user: Int, cpu: Int): Int = {
    if (user == cpu) {
      Winner.Draw
    } else if (
      (user == Weapon.Paper && cpu == Weapon.Scissor)
      || (user == Weapon.Scissor && cpu == Weapon.Rock)
      || (user == Weapon.Rock && cpu == Weapon.Paper)
    ) {
      Winner.Cpu
    } else {
      Winner.User
    }
  }

  /** TODO should throw if input is not a valid weapon
    *
    * @param input
    * @return
    */
  def parseWeapon(input: String): Int = {
    if (input == s"${Weapon.Rock}") {
      Weapon.Rock
    } else if (input == s"${Weapon.Paper}") {
      Weapon.Paper
    } else if (input == s"${Weapon.Scissor}") {
      Weapon.Scissor
    } else {
      Weapon.Invalid // TODO throw?
    }
  }

  private val r = scala.util.Random

  private def generateComputerMove(): String =
    r.nextInt(3).toString
}
