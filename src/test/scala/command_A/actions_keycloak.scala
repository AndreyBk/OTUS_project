package command_A

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import webt.s.test.properties._


object actions_keycloak {

  val auth: HttpRequestBuilder =    http("keycloak")
  .post("/auth/realms/ziiot/protocol/openid-connect/token")
    .header("Connection", "close")
    .formParam("client_id", client_id)
    .formParam("grant_type", "password")
    .formParam("username", username)
    .formParam("password", password)
    .formParam("client_secret", client_secret)
    .formParam("scope", "openid")
    .check(jsonPath("$.access_token").exists.saveAs("TOKEN"))



}
