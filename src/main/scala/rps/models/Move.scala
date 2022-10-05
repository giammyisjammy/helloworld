package rps.models

import io.buildo.enumero.annotations.indexedEnum
import io.buildo.enumero.{CaseEnumIndex, CaseEnumSerialization}

@indexedEnum trait Move {
  type Index = String
  object Rock { "0" }
  object Paper { "1" }
  object Scissors { "2" }
}


object MoveEncoder {
  def print(input: Move): String = {
    input match {
      case Move.Rock    => "🪨 Rock"
      case Move.Paper     => "📄 Paper"
      case Move.Scissors => "✂️ Scissors"
    }
  }
}
