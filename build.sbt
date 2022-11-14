ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0"
ThisBuild / organization := "home.investigation"
ThisBuild / organizationName := "rrr_lands"

lazy val root = (project in file("."))
  .settings(
    name := "scala-doobie",
    libraryDependencies ++= Dependencies.appDependencies
  )
