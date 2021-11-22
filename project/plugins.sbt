
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.8")
addSbtPlugin("org.foundweekends.giter8" % "sbt-giter8-scaffold" % "0.11.0")

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"       % "3.5.0",
  "postgresql" % "postgresql" % "9.1-901.jdbc4",
  //"io.circe" % "circe-core_2.11" % "0.9.0-M2",
  "io.circe" %% "circe-parser" % "0.9.0-M2"//,
  //"io.circe" %% "circe-generic" % "0.9.0-M2"
)
