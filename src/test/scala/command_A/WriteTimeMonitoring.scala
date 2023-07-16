package command_A


import java.time.Instant
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import webt.s.test.properties._

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Keep, RunnableGraph, Sink, Source}
import akka.{Done, NotUsed}
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.scala.{InfluxDBClientScala, InfluxDBClientScalaFactory}
import com.influxdb.client.write.Point
import io.gatling.core.Predef.{Session, StringBody, exec, group, jsonPath, pace, scenario}
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef.{http, status}
import io.gatling.http.request.builder.HttpRequestBuilder

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object WriteTimeMonitoring {
  implicit val system: ActorSystem = ActorSystem("examples")

  //  val client: InfluxDBClientScala = InfluxDBClientScalaFactory.create("http://192.168.0.116:8086", "my-token".toCharArray, "my-org", "gatlingdb")
  val client: InfluxDBClientScala = InfluxDBClientScalaFactory.create("http://192.168.101.67:8086", "my-token".toCharArray, "my-org", "gatlingdb")

  val writeToInflux: ChainBuilder = group("writeToInflux") {
    exec(session => {
      val point: Point = Point
        .measurement("gatlingdb")
        .addTag("group", "custom")
        .addTag("request", "create_cr")
        .addTag("status", session("INFLUXDB_STATUS").as[String])
        .addTag("scenario", "cr_load")
        .addField("percentiles90", session("CRULE_METRIC").as[Long].toFloat)
        .addField("count", 1F)
        .time(Instant.now(), WritePrecision.NS)
//      println("*******************************************")
      val sourcePoint: Source[Point, NotUsed] = Source.single(point)
      val sinkPoint: Sink[Point, Future[Done]] = client.getWriteScalaApi.writePoint()
      val materializedPoint: RunnableGraph[Future[Done]] = sourcePoint.toMat(sinkPoint)(Keep.right)
      Await.result(materializedPoint.run(), Duration.Inf)
      session
    })
  }


  val agregate: ChainBuilder =group("") {
    exec(session => {
      properties.count_metrics = properties.count_metrics + 1
      properties.summ_metrics = properties.summ_metrics + (System.currentTimeMillis() - session("START_TIME").as[Long])
      session
    })
  }

  val writeToInflux_s: ChainBuilder = group("writeToInflux") {
    exec(session => {

      val point: Point = Point
        .measurement("gatlingdb")
        .addTag("group", "custom")
        .addTag("request", "create_cr")
        .addTag("status", session("INFLUXDB_STATUS").as[String])
        .addTag("scenario", "cr_load")
        .addField("percentiles90", properties.summ_metrics/properties.count_metrics)
        .addField("count", properties.count_metrics)
        .time(Instant.now(), WritePrecision.NS)
      //      println("*******************************************")
      val sourcePoint: Source[Point, NotUsed] = Source.single(point)
      val sinkPoint: Sink[Point, Future[Done]] = client.getWriteScalaApi.writePoint()
      val materializedPoint: RunnableGraph[Future[Done]] = sourcePoint.toMat(sinkPoint)(Keep.right)
      Await.result(materializedPoint.run(), Duration.Inf)
      session
    })
  }

  val writeTimeMonitoringScn: ScenarioBuilder = scenario("WriteTimeMonitoring")
    .during(1, "during_name")( //todo точка
      pace(1)
        //      .exec(querySnapshot)
        //      .exec(calc)
        .exec(writeToInflux_s)
    )
}
