scalaVersion := "2.13.18"
organization := "com.cosminci"
name := "scalafix-custom-rules"
version := "0.3.5"
libraryDependencies += "ch.epfl.scala" %% "scalafix-core" % "0.14.9"
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-Werror")
