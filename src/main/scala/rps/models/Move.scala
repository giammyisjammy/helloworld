package rps.models

import io.buildo.enumero.annotations.enum

@enum trait Move {
  object Rock
  object Paper
  object Scissors
}

object MoveEncoder {
  def decode(input: String): Option[Move] = {
    input match {
      case "0" => Some(Move.Rock)
      case "1" => Some(Move.Paper)
      case "2" => Some(Move.Scissors)
      case _   => None
    }
  }

  def encode(input: Move): String = {
    input match {
      case Move.Rock     => "0"
      case Move.Paper    => "1"
      case Move.Scissors => "2"
    }
  }

  def print(input: Move): String = {
    input match {
      case Move.Rock    => "🪨 Rock"
      case Move.Paper     => "📄 Paper"
      case Move.Scissors => "✂️ Scissors"
    }
  }
}
