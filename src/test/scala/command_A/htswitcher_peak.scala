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
class htswitcher_peak extends Simulation {
  // точка входа
  //https://xxxxx-1.dev.dp.xxxxx.com/


  val httpProtocol: HttpProtocolBuilder = http
    //    .baseUrl(baseUrl)
    .baseUrl("https://xxxxx-1.dev.dp.xxxxx.com")
    .inferHtmlResources()

  //  для запуска
  setUp(
    Scenario_smoke_hts().inject(atOnceUsers(1)).protocols(httpProtocol)
      .andThen(

        //        // сценарий нагрузки hts
        Scenario_load_hts_analogue().inject(
          rampConcurrentUsers(0).to(125).during(60 seconds),
          constantConcurrentUsers(125) during (3 minute),
          rampConcurrentUsers(125).to(0).during(60 seconds)
        ).protocols(httpProtocol),
        //
        Scenario_load_hts_discrete().inject(
          rampConcurrentUsers(0).to(125).during(60 seconds),
          constantConcurrentUsers(125) during (3 minute),
          rampConcurrentUsers(125).to(0).during(60 seconds)
        ).protocols(httpProtocol),

        //сценарий получения токена
        Scenario_get_token().inject(
          rampConcurrentUsers(0).to(1).during(0 seconds),
          constantConcurrentUsers(1) during (2 minute)
        ).protocols(httpProtocol)

      ),
  ).maxDuration(10000)
    .assertions(global.responseTime.max.lt(10000))
  //        constantConcurrentUsers(1).during(300 second)).protocols(httpProtocol),
  //      WriteTimeMonitoring.writeTimeMonitoringScn.inject((atOnceUsers(1))).protocols(httpProtocol)
}

