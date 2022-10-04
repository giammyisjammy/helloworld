package rps.models

sealed trait GameResult
object GameResult {
  case object Draw extends GameResult
  case object UserWins extends GameResult
  case object CpuWins extends GameResult
  case object DumbUser extends GameResult
}
