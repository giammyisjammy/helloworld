package rps.models

sealed trait Move
object Move {
  case object Rock extends Move // 🪨
  case object Paper extends Move // 📄
  case object Scissors extends Move // ✂️
  val moves = List(Rock, Paper, Scissors)

  /**
    * Parses `String` into `Move`
    *
    * @param input
    * @return
    */
  def decode(input: String): Option[Move] = {
    input match {
      case "0" => Some(Rock)
      case "1" => Some(Paper)
      case "2" => Some(Scissors)
      case _   => None
    }
  }

  /**
    * Serializes a `Move` into a `String`
    *
    * @param input
    * @return
    */
  def encode(input: Move): String = {
    input match {
      case Rock     => "0"
      case Paper    => "1"
      case Scissors => "2"
    }
  }

  /**
    * Generates a user-friendly representation of Move
    *
    * @param input the `Move` to convert
    * @return a human readable string 
    */
  def print(input: Move): String = {
    input match {
      case Paper    => "🪨 Rock"
      case Rock     => "📄 Paper"
      case Scissors => "✂️ Scissors"
    }
  }
}
