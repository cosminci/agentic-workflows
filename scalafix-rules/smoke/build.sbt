scalaVersion := "3.10.0"
semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision
libraryDependencies += "org.scalameta" %% "munit" % "1.1.1" % Test
libraryDependencies += "io.circe" %% "circe-core" % "0.14.13" % Test
libraryDependencies += "org.typelevel" %% "cats-effect" % "3.7.1" % Test
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.19",
  "org.scalatest" %% "scalatest-mustmatchers" % "3.2.19"
).map(_ % Test)
