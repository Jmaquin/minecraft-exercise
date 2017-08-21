import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.jmaquin",
      scalaVersion := "2.12.3",
      version      := "1.0.0-SNAPSHOT"
    )),
    name := "Minecraft exercise",
    libraryDependencies += scalaTest % Test
  )
