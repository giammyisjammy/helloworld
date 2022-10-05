package rps.models
import io.buildo.enumero.annotations.enum

@enum trait GameResult {
  object Draw
  object UserWins
  object CpuWins
  object DumbUser
}
