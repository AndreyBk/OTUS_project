package command_A

//import LoadTest.kafka_01_Write_data._
import io.gatling.core.Predef.atOnceUsers
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.DurationInt
//import LoadTest.KafkaRequests._
//import LoadTest.RTDBDataRequests._
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._

//import ru.tinkoff.gatling.kafka.Predef._
class controlRules_peak extends Simulation {
  // точка входа
  //https://xxxxx-1.dev.dp.xxxxx.com/


  val httpProtocol: HttpProtocolBuilder = http
    //    .baseUrl(baseUrl)
    .baseUrl("https://xxxxx-1.dev.dp.xxxxx.com")
    .inferHtmlResources()
val start=50
  val step=50
  val stepway=10
  val during=60
  //  для запуска
  setUp(
    Scenario_smoke_cr().inject(atOnceUsers(1)).protocols(httpProtocol)
      .andThen(

              // нагрузочный сценарий правил контроля
            Scenario_load_cr().inject(
            rampConcurrentUsers(0).to(800).during(60 seconds),
            constantConcurrentUsers(800) during (2 minutes),
            constantConcurrentUsers(1) during (60 seconds)
          ).protocols(httpProtocol),

        //
//        Scenario_write_metrics().inject(
//          rampConcurrentUsers(0).to(1).during(0 seconds),
//          constantConcurrentUsers(1) during (2 hour)
//        ).protocols(httpProtocol)
      ),
    //сценарий получения токена
    Scenario_get_token().inject(
      rampConcurrentUsers(0).to(1).during(0 seconds),
      constantConcurrentUsers(1) during (2 minute)
    ).protocols(httpProtocol)

  ).maxDuration(10000)
    .assertions(global.responseTime.max.lt(3000))
  //        constantConcurrentUsers(1).during(300 second)).protocols(httpProtocol),


  //      WriteTimeMonitoring.writeTimeMonitoringScn.inject((atOnceUsers(1))).protocols(httpProtocol)

}

