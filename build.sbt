ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "scalaapi"
  )

val akkaVersion = "2.7.0"
val akkaHttpVersion = "10.4.0"
val requestScala = "0.8.0"
val logbackVersion = "1.4.5"

libraryDependencies ++= Seq(
  // akka streams
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  // akka http
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.lihaoyi" %% "requests" % requestScala,
  // logback - backend for slf4j
  "ch.qos.logback" % "logback-classic" % logbackVersion % Runtime,
)