import Dependencies._

ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "io.buildo"
ThisBuild / organizationName := "buildo"

val V = new {
  val enumero = "1.4.2"
  val slick = "3.3.3"
}

scalacOptions += "-Ymacro-annotations"

lazy val root = (project in file("."))
  .settings(
    name := "rps",
    libraryDependencies ++= List(
      "org.scalameta" %% "munit" % "1.0.0-M6" % Test,
      "io.buildo" %% "enumero" % V.enumero,
      "io.buildo" %% "enumero-circe-support" % V.enumero,
      "com.typesafe" % "config" % "1.4.1", // to parse configuration files
      "com.typesafe.slick" %% "slick" % "3.3.3",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
      "org.slf4j" % "slf4j-nop" % "1.6.4", // mock logging implementation
      "org.flywaydb" % "flyway-core" % "7.5.4", // to run migrations from the code
      "org.postgresql" % "postgresql" % "42.2.5"
    )
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
