package rps

object Hello extends Greeting with App {
  println(greeting)
  Game.play() //
}

trait Greeting {
  lazy val greeting: String = "Wanna play? 🐷"
}
