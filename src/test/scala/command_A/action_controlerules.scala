package command_A

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder


object action_controlerules {
  private val headers = Map(
    "accept" -> "text/plain",
    "Content-Type" -> "application/json-patch+json",
    "authorization" -> "Bearer #{TOKEN}"
  )


  val create_typeonecontrolrules: HttpRequestBuilder = http("create_typeonecontrolrules")
    .post("/xxxxx-xxxxx-controlrulemanager/typeonecontrolrules")
    .headers(headers)
    .body(StringBody(session => session("BODY_CRULE").as[String]))
    .check(
      status.is(201)
    )

  val get_status_crule: HttpRequestBuilder = http("get_status_crule")
    .get("/xxxxx-xxxxx-controlrulemanager/singlemanager/#{CRULE_ID}/status")
    .headers(headers)
    .check(status.is(200))
    .check(bodyString.saveAs("STATUS"))

  val delete_typeonecontrolrules: HttpRequestBuilder = http("delete_typeonecontrolrules")
    .delete("/xxxxx-xxxxx-controlrulemanager/typeonecontrolrules/#{CRULE_ID}")
    .headers(headers)
    .check(
      status.is(200)
    )
}
