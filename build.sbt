enablePlugins(GatlingPlugin)

scalaVersion := "2.13.10"

scalacOptions := Seq(
  "-encoding", "UTF-8", "-target:jvm-1.8", "-deprecation",
  "-feature", "-unchecked", "-language:implicitConversions", "-language:postfixOps")

val gatlingVersion = "3.9.5"
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % "test,it"
libraryDependencies += "io.gatling"            % "gatling-test-framework"    % gatlingVersion % "test,it"
libraryDependencies += "com.influxdb"            % "influxdb-client-scala_2.13"    % "6.9.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.2"


