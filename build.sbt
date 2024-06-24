version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "tst-take-home"
  )
  libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.18" % Test
)
