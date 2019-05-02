name := "scala-sandbox"

version := "0.1.0"

scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.4" % "test"
  , "org.xerial" % "sqlite-jdbc" % "3.16.1"
)

