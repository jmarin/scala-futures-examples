import Dependencies.*

lazy val scala3 = "3.2.0"
lazy val supportedScalaVersions = List(scala3)

ThisBuild / organization := "com.jmarin"
ThisBuild / scalaVersion := scala3

resolvers ++= repos

val sharedSettings = Seq(
  crossScalaVersions := supportedScalaVersions,
  libraryDependencies ++= Seq(
    "org.scalatest" %%% "scalatest" % Version.scalaTest % "test",
    "org.scalacheck" %%% "scalacheck" % Version.scalaCheck % "test"
  )
)

lazy val futures = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Full)
  .in(file("."))
  .enablePlugins(JavaAppPackaging)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings ++ sharedSettings)
  .jvmSettings(
    // Configure JVM settings
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % "2.6.20"
    )
  )
  .jsSettings(
    // Configure JS settings
    scalaJSUseMainModuleInitializer := true,
    libraryDependencies ++= Seq(
      "io.github.cquiroz" %%% "scala-java-time" % "2.3.0",
      "io.github.cquiroz" %%% "scala-java-time-tzdb" % "2.3.0",
      ("org.akka-js" %%% "akkajsactortyped" % "2.2.6.14" exclude ("org.scala-js", "scalajs-java-time"))
        .cross(CrossVersion.for3Use2_13)
    )
  )
