
organization := "com.gu.identity"
name := "csv-character-counter"
description := "Scala app to count all characters per column in a csv file"
scalaVersion := "2.13.5"


lazy val root = (project in file(".")).dependsOn(scalaProgressBar)

lazy val scalaProgressBar = ProjectRef(uri("git://github.com/a8m/pb-scala.git#master"), "pb-scala")

resolvers ++= Seq(
    "Scala Tools Repository" at "https://oss.sonatype.org/content/groups/scala-tools/",
    "Asual repository" at "http://www.asual.com/maven/content/repositories/releases",
    "Artima Maven Repository" at "http://repo.artima.com/releases",
    "jitpack" at "https://jitpack.io",
    Resolver.sonatypeRepo("releases")
  )

val AkkaVersion = "2.5.31"

libraryDependencies ++= Seq( 
     "com.github.tototoshi" %% "scala-csv" % "1.3.7",
     "org.scalatest" %% "scalatest-freespec" % "3.2.5" % "test",
     "org.scalactic" %% "scalactic" % "3.2.5",
     "org.scalatest" %% "scalatest" % "3.2.5" % "test",
     "commons-validator" % "commons-validator" % "1.7",
     "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "2.0.2",
     "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
     "com.github.scopt" %% "scopt" % "4.0.1"
)

