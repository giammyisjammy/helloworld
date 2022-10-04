package rps.test

import rps.Game.checkWinner
import rps.models.Move._
import rps.models.GameResult._


class GameSuite extends munit.FunSuite {
  test("user wins") {
    assertEquals(checkWinner(Some(Rock), Scissors), UserWins)
    assertEquals(checkWinner(Some(Paper), Rock), UserWins)
    assertEquals(checkWinner(Some(Scissors), Paper), UserWins)
  }

  test("cpu wins") {
    assertEquals(checkWinner(Some(Rock), Paper), CpuWins)
    assertEquals(checkWinner(Some(Paper), Scissors), CpuWins)
    assertEquals(checkWinner(Some(Scissors), Rock), CpuWins)
  }

  test("draw") {
    assertEquals(checkWinner(Some(Rock), Rock), Draw)
    assertEquals(checkWinner(Some(Paper), Paper), Draw)
    assertEquals(checkWinner(Some(Scissors), Scissors), Draw)
  }
}
