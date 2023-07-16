package command_A

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder


object action_htswitcher {
  private val headers = Map(
    "accept" -> "text/plain",
    "Content-Type" -> "application/json-patch+json",
    "authorization" -> "Bearer #{TOKEN}"
  )

  val get_list_chanels_by_objectid: HttpRequestBuilder = http("get_list_chanels_by_objectid")
    .get("/xxxxx-xxxxx-htswitcher/measurementchannels/objects/#{OBJECT_ID}/list")
    .queryParam("modelId", "#{MODEL_ID}")
    .queryParam("objectId", "#{OBJECT_ID}")
    .headers(headers)
    .check(jsonPath("$[*]").findAll.saveAs("ALL_CHANELS"))
    .check(status.is(200))
    .check(responseTimeInMillis.lte(60000 ))

  val get_analog_chanel: HttpRequestBuilder = http("get_analog_chanel")
    .get("https://xxxxx-1.dev.dp.xxxxx.com/xxxxx-xxxxx-htswitcher/measurementchannels/analogue/#{ID_ANALOGUE_CHANNEL}")
    .queryParam("modelId", "#{MODEL_ID}")
    .queryParam("objectId", "#{OBJECT_ID}")
    .headers(headers)
    .check(status.is(200))

  val create_analog_chanel: HttpRequestBuilder =http("create_analog_chanel")
    .post("/xxxxx-xxxxx-htswitcher/measurementchannels/analogue/#{OBJECT_ID}")
    .queryParam("modelId", "#{MODEL_ID}")
    .headers(headers)
    .body(StringBody(session => session("BODY_ANALOG_CHANEL").as[String]))
    .check(status.is(200))
    .check(responseTimeInMillis.lte(70000 ))//    .body(StringBody(session => session("BODY_LIST_ANALOG_CHANELS").as[String]))

  val create_analog_measurement_channel_settings: HttpRequestBuilder =http("create_analogue_channel_settings")
    .post("/xxxxx-xxxxx-htswitcher/measurementchannels/analogue/#{ID_ANALOGUE_CHANNEL}/settings")
    .queryParam("modelId", "#{MODEL_ID}")
    .queryParam("objectId", "#{OBJECT_ID}")
    .headers(headers)
    .body(StringBody(session => session("BODY_ANALOG_SETTING").as[String]))
    .check(status.is(200))
    .check(responseTimeInMillis.lte(70000 ))//    .body(StringBody(session => session("BODY_LIST_ANALOG_CHANELS").as[String]))

  val get_analog_setting: HttpRequestBuilder = http("get_analog_setting")
    .get("/xxxxx-xxxxx-htswitcher/measurementchannels/analogue/#{ID_ANALOGUE_CHANNEL}/settings")
    .queryParam("modelId", "#{MODEL_ID}")
    .queryParam("objectId", "#{OBJECT_ID}")
    .headers(headers)
    .check(status.is(200))

  val create_discret_chanel: HttpRequestBuilder =http("create_discrete_chanel")
    .post("/xxxxx-xxxxx-htswitcher/measurementchannels/discrete/#{OBJECT_ID}")
    .queryParam("modelId", "#{MODEL_ID}")
    .headers(headers)
    .body(StringBody(session => session("BODY_DISCRET_CHANEL").as[String]))
    .check(status.is(200))
    //    .check(bodyString.saveAs("CRETED_ANALOG_CHANEL"))
    .check(responseTimeInMillis.lte(70000 ))//    .body(StringBody(session => session("BODY_LIST_ANALOG_CHANELS").as[String]))

  val create_discret_measurement_channel_settings: HttpRequestBuilder =http("create_discrete_channel_settings")
    .post("/xxxxx-xxxxx-htswitcher/measurementchannels/discrete/#{ID_DISCRETE_CHANNEL}/settings")
    .queryParam("modelId", "#{MODEL_ID}")
    .queryParam("objectId", "#{OBJECT_ID}")
    .headers(headers)
    .body(StringBody(session => session("BODY_DISCRET_SETTING").as[String]))
    .check(status.is(200))
    //    .check(bodyString.saveAs("CRETED_ANALOG_SETTING"))
    .check(responseTimeInMillis.lte(70000 ))//    .body(StringBody(session => session("BODY_LIST_ANALOG_CHANELS").as[String]))

  val get_discrete_setting: HttpRequestBuilder = http("get_discrete_setting")
    .get("/xxxxx-xxxxx-htswitcher/measurementchannels/discrete/#{ID_DISCRETE_CHANNEL}/settings")
    .queryParam("modelId", "#{MODEL_ID}")
    .queryParam("objectId", "#{OBJECT_ID}")
    .headers(headers)
    .check(status.is(200))

  val get_discrete_chanel: HttpRequestBuilder = http("get_discrete_chanel")
    .get("https://xxxxx-1.dev.dp.xxxxx.com/xxxxx-xxxxx-htswitcher/measurementchannels/discrete/#{ID_DISCRETE_CHANNEL}")
    .queryParam("modelId", "#{MODEL_ID}")
    .queryParam("objectId", "#{OBJECT_ID}")
    .headers(headers)
    .check(status.is(200))
}
