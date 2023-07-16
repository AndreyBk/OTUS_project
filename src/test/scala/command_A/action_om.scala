package command_A

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder


object action_om {
  private val headers = Map(
    "Accept" -> "application/json",
    "authorization" -> "Bearer #{TOKEN}"
  )

  private val headers_0 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
    "Accept-Language" -> "ru-RU,ru;q=0.9",
    //    "Connection" -> "keep-alive",
    "Proxy-Connection" -> "keep-alive",
    //    "Host" -> "webtours.load-test.ru:1080",
    "Upgrade-Insecure-Requests" -> "1"
  )
  private val headers_2 = Map(
    "Accept" -> "image/avif,image/webp,image/apng,image/svg+xml,image/*,*/*;q=0.8",
    "Accept-Language" -> "ru-RU,ru;q=0.9",
    "Proxy-Connection" -> "keep-alive"
  )
  private val headers_8 = Map(
    "Accept" -> "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7",
    "Accept-Language" -> "ru-RU,ru;q=0.9",
    "Cache-Control" -> "max-age=0",
    "Origin" -> "http://webtours.load-test.ru:1080",
    //    "Proxy-Connection" -> "keep-alive",
    "Connection" -> "keep-alive",
    "Upgrade-Insecure-Requests" -> "1"
  )

  val get_root_page_webtours: HttpRequestBuilder = http("get_root_page_webtours")
    .get("/webtours/")
    .headers(headers_0)
  //    .resources(
  val get_root_page_header: HttpRequestBuilder = http("get_root_page_header")
    .get("/webtours/header.html")
    .headers(headers_0)

  val get_root_page_welcome: HttpRequestBuilder = http("get_root_page_welcom")
    .get("/cgi-bin/welcome.pl")
    .headers(headers_0)
    .queryParam("signOff", "true")

  val get_root_page_home: HttpRequestBuilder = http("get_root_page_home")
    .get("/WebTours/home.html")
    .headers(headers_0)

  val get_root_page_nav: HttpRequestBuilder = http("get_root_page_nav")
    .get("/cgi-bin/nav.pl?in=home")
    .headers(headers_0)
    //            .check(xpath("//input[@name='userSession']/@value").saveAs("qwert"))
    .check(regex("""name=\"userSession\" value=\"([^\"]+)\"""").saveAs("USERSESSION"))

  // login
  val loginininng: HttpRequestBuilder = http("logining")
    .post("/cgi-bin/login.pl")
    .headers(headers_8)
    .formParam("userSession", "#{USERSESSION}")
    //    .formParam("username", "bkiv")
    //    .formParam("password", "123")
    .formParam("username", "#{nick}")
    .formParam("password", "#{pass}")
    .formParam("login.x", "26")
    .formParam("login.y", "11")
    .formParam("JSFormSubmit", "off")
    .check(substring("User password was incorrect.  Prompt the user to fix password or sign-up...").notExists)
    .resources(
      http("logining_nav")
        .get("/cgi-bin/nav.pl")
        .headers(headers_0)
        .queryParam("page", "menu")
        .queryParam("in", "home"),
      http("logining_login")
        .get("/cgi-bin/login.pl")
        .headers(headers_0)
        .queryParam("intro", "true")
    )

  // flights_button
  val click_flights_button: HttpRequestBuilder = http("click_flights_button")
    .get("/cgi-bin/welcome.pl")
    .queryParam("page", "search")
    .headers(headers_0)
    .resources(
      http("click_flights_button_nav")
        .get("/cgi-bin/nav.pl")
        .queryParam("page", "menu")
        .queryParam("in", "flights")
        .headers(headers_0),
      http("click_flights_button_reservations")
        .get("/cgi-bin/reservations.pl")
        .queryParam("page", "welcome")
        .headers(headers_0)
        .check(regex("""option(?!value=\")[^>]+>([^<]+)<""").findAll.withDefault("not_found").saveAs("AEROPORTS"))
    )

  // Find_Flight_and_continue
  val one_way_find_flight_and_continue: HttpRequestBuilder = http("find_flight_and_continue")
    .post("/cgi-bin/reservations.pl")
    .headers(headers_8)
    .formParam("advanceDiscount", "0")
    .formParam("depart", "#{DEPARTURE_AERO}")
    .formParam("departDate", "05/28/2023")
    .formParam("arrive", "#{ARRIVED_AERO}")
    .formParam("returnDate", "05/29/2023")
    .formParam("numPassengers", "1")
    .formParam("seatPref", "None")
    .formParam("seatType", "Coach")
    .formParam("findFlights.x", "33")
    .formParam("findFlights.y", "5")
    .formParam(".cgifields", "roundtrip")
    .formParam(".cgifields", "seatType")
    .formParam(".cgifields", "seatPref")
    .check(regex("""<input type=\"radio\" name=\"outboundFlight\" value=\"([^\"]+)\"""").findAll.withDefault("not_found").saveAs("FLIGHTS"))
  val round_trip_find_flight_and_continue: HttpRequestBuilder = http("find_flight_and_continue")
    .post("/cgi-bin/reservations.pl")
    .headers(headers_8)
    .formParam("advanceDiscount", "0")
    .formParam("depart", "#{DEPARTURE_AERO}")
    .formParam("departDate", "05/28/2023")
    .formParam("arrive", "#{ARRIVED_AERO}")
    .formParam("returnDate", "05/29/2023")
    .formParam("numPassengers", "1")
    .formParam("seatPref", "None")
    .formParam("seatType", "Coach")
    .formParam("findFlights.x", "33")
    .formParam("findFlights.y", "5")
    .formParam(".cgifields", "roundtrip")
    .formParam(".cgifields", "seatType")
    .formParam(".cgifields", "seatPref")
    .formParam("roundtrip", "on")
    .check(regex("""<input type=\"radio\" name=\"outboundFlight\" value=\"([^\"]+)\"""").findAll.saveAs("FLIGHTS"))
    .check(regex("""<input type=\"radio\" name=\"returnFlight\" value=\"([^\"]+)\"""").findAll.saveAs("RETURN_FLIGHTS"))

  // Find_Flight_raise_and_continue
  val one_way_find_flight_raise_and_continue: HttpRequestBuilder = http("find_flight_raise_and_continue")
    .post("/cgi-bin/reservations.pl")
    .headers(headers_8)
    .formParam("outboundFlight", "#{FLIGHT}") //000;0;05/28/2023")
    .formParam("numPassengers", "1")
    .formParam("advanceDiscount", "0")
    .formParam("seatType", "Coach")
    .formParam("seatPref", "None")
    .formParam("reserveFlights.x", "45")
    .formParam("reserveFlights.y", "7")

  val round_trip_find_flight_raise_and_continue: HttpRequestBuilder = http("find_flight_raise_and_continue")
    .post("/cgi-bin/reservations.pl")
    .headers(headers_8)
    .formParam("outboundFlight", "#{FLIGHT}") //000;0;05/28/2023")
    .formParam("returnFlight", "#{RETURN_FLIGHT}") //000;0;05/28/2023")
    .formParam("numPassengers", "1")
    .formParam("advanceDiscount", "0")
    .formParam("seatType", "Coach")
    .formParam("seatPref", "None")
    .formParam("reserveFlights.x", "45")
    .formParam("reserveFlights.y", "7")

  // Payment_Details_and_continue
  val one_way_payment_details_and_continue: HttpRequestBuilder = http("payment_details_and_continue")
    .post("/cgi-bin/reservations.pl")
    .headers(headers_8)
    .formParam("firstName", "andrey")
    .formParam("lastName", "cher")
    .formParam("address1", "Berlin strasse")
    .formParam("address2", "Gamburg")
    .formParam("pass1", "andrey cher")
    .formParam("creditCard", "")
    .formParam("expDate", "")
    .formParam("oldCCOption", "")
    .formParam("numPassengers", "1")
    .formParam("seatType", "Coach")
    .formParam("seatPref", "None")
    .formParam("outboundFlight", "#{FLIGHT}")
    .formParam("advanceDiscount", "0")
    .formParam("returnFlight", "")
    .formParam("JSFormSubmit", "off")
    .formParam("buyFlights.x", "62")
    .formParam("buyFlights.y", "5")
    .formParam(".cgifields", "saveCC")

  val round_trip_payment_details_and_continue: HttpRequestBuilder = http("payment_details_and_continue")
    .post("/cgi-bin/reservations.pl")
    .headers(headers_8)
    .formParam("firstName", "andrey")
    .formParam("lastName", "cher")
    .formParam("address1", "Berlin strasse")
    .formParam("address2", "Gamburg")
    .formParam("pass1", "andrey cher")
    .formParam("creditCard", "")
    .formParam("expDate", "")
    .formParam("oldCCOption", "")
    .formParam("numPassengers", "1")
    .formParam("seatType", "Coach")
    .formParam("seatPref", "None")
    .formParam("outboundFlight", "#{FLIGHT}")
    .formParam("returnFlight", "#{RETURN_FLIGHT}")
    .formParam("advanceDiscount", "0")
    .formParam("returnFlight", "")
    .formParam("JSFormSubmit", "off")
    .formParam("buyFlights.x", "62")
    .formParam("buyFlights.y", "5")
    .formParam(".cgifields", "saveCC")


  // Invoice_itineary_button
  val invoice_itineary_button: HttpRequestBuilder = http("invoice_itineary_button")
    .get("/cgi-bin/welcome.pl")
    .queryParam("page", "itinerary")
    .headers(headers_0)
    .resources(
      http("invoice_itineary_button_nav")
        .get("/cgi-bin/nav.pl")
        .queryParam("page", "menu")
        .queryParam("in", "itinerary")
        .headers(headers_0),
      http("invoice_itineary_button_itinerary")
        .get("/cgi-bin/itinerary.pl")
        .headers(headers_0)
        .check(regex("""<input type=\"hidden\" name=\"flightID\" value=\"([^\"]+)\"""").findAll.saveAs("TICKETS"))
        .check(regex("""<input type=\"hidden\" name=\".cgifields\" value=\"([^\"]+)\"""").findAll.saveAs("IDS_TICKETS"))
    )

  // cancele_checked_button
  val cancele_checked_button: HttpRequestBuilder = http("cancele_checked_button")
    .post("/cgi-bin/itinerary.pl")
    .headers(headers_8)
    .body(StringBody("#{BODY}"))

  // sign_off
  val sign_off: HttpRequestBuilder = http("sign_off")
    .get("/cgi-bin/welcome.pl")
    .queryParam("signOff", "1")
    .headers(headers_0)
    .resources(
      http("sign_off_nav")
        .get("/cgi-bin/nav.pl")
        .queryParam("in", "home")
        .headers(headers_0)
    )


}
