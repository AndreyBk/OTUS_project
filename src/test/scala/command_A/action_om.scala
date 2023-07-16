package command_A

import io.gatling.core.Predef._
import io.gatling.http.Predef._


object action_om {
  private val headers = Map(
    "Accept" -> "application/json",
    "authorization" -> "Bearer #{TOKEN}"
  )
}
