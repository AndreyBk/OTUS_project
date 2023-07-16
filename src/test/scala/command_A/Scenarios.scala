package command_A

//import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}

import io.gatling.core.Predef._
import io.gatling.core.session
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef.http

import scala.collection.JavaConverters._
import scala.collection.mutable.ListBuffer
//import org.json4s.native.Json
//import org.json4s.DefaultFormats
import scala.concurrent.duration.DurationInt
import scala.util.Random
//import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
//import io.gatling.http.Predef._
import play.api.libs.json._


object Scenario_smoke_hts {
  def apply(): ScenarioBuilder = new Scenarios().hts_smoke
}

object Scenario_smoke_cr {
  def apply(): ScenarioBuilder = new Scenarios().cr_smoke
}

object Scenario_load_cr {
  def apply(): ScenarioBuilder = new Scenarios().cr_load
}

object Scenario_load_hts_analogue {
  def apply(): ScenarioBuilder = new Scenarios().Scenario_load_hts_analogue
}

object Scenario_load_hts_discrete {
  def apply(): ScenarioBuilder = new Scenarios().Scenario_load_hts_discrete
}

object Scenario_get_token {
  def apply(): ScenarioBuilder = new Scenarios().cr_auth
}

object Scenario_write_metrics {
  def apply(): ScenarioBuilder = new Scenarios().write_metrics
}

class Scenarios {

  val create_body_controlerule: ChainBuilder = group("create_body_controlerule") {
    exec(session => {
      val controlerule_id = java.util.UUID.randomUUID.toString
      val body: JsValue = Json.obj(
        "id" -> controlerule_id,
        "controlTypeId" -> "GT",
        "alarmTypeId" -> "Accident",
        "priorityId" -> "Critical",
        "objectId" -> "38523a35-09ec-45e4-b375-0083f320d920",
        "propertyId" -> "71ec2c18-1686-43cd-ae13-061c7f63eeee",
        "groupId" -> "38523a35-09ec-45e4-b375-0083f320d920",
        "sourceId" -> "6bd4b071-31ea-4ab9-a728-cfcb3ce633ba",
        "isActive" -> JsTrue,
        "borderValue" -> 0,
        "eventTypeId" -> "4eb1230b-a616-4539-abfb-123456784444",
      )
      //      val newsession = session.set ("BODY_CRULE", Json.stringify(body))
      val newsession = session.set("BODY_CRULE", body)
      //      print(Json.stringify(body))
      newsession
    })
  }

  val create_body_analog_chanel: ChainBuilder = group("create_body_analog_chanel") {
    exec(session => {
      val _id = java.util.UUID.randomUUID.toString
      val body: JsValue = Json.obj(
        "name" -> ("load_test_" + _id),
        "sourceCode" -> "4.0",
        "technologicalParameterCode" -> "1",
        "isActiveTag" -> "ch_arg_2",
        "sourceTag" -> "ch_arg_3",
        "normAttributeTag" -> "ch_arg_4",
        "installationNodeId" -> session("OBJECT_ID").as[String],
        "hysteresisControlEnabledTag" -> "ch_arg_5",
        "hysteresisRangeTag" -> "ch_arg_6",
        "halfPeriodHysteresisTag" -> "ch_arg_7",
      )
      val newsession = session.set("BODY_ANALOG_CHANEL", body)
      newsession
    })
  }

  val create_body_analogue_measurement_channel_settings: ChainBuilder = group("create_body_analogue_setting") {
    exec(session => {
      val _id = java.util.UUID.randomUUID.toString
      val body: JsValue = Json.obj(
        "bottomBorderTag" -> "ch_arg_2",
        "topBorderTag" -> "ch_arg_3",
        "eventTypeCode" -> "pytest_02",
        "includeTopBorder" -> true,
        "includeBottomBorder" -> true,
        "businessTask" -> "4.0",
        "normAttributeTag" -> "ch_arg_4",
      )
      val newsession = session.set("BODY_ANALOG_SETTING", body)
      newsession
    })
  }

  val create_body_discret_chanel: ChainBuilder = group("create_body_discret_chanel") {
    exec(session => {
      val _id = java.util.UUID.randomUUID.toString
      val body: JsValue = Json.obj(
        "name" -> ("load_test_" + _id),
        "sourceCode" -> "4.0",
        "sourceTag" -> "ch_arg_3",
        "installationNodeId" -> session("OBJECT_ID").as[String],
        "normAttributeTag" -> "ch_arg_5",
      )
      val newsession = session.set("BODY_DISCRET_CHANEL", body)
      newsession
    })
  }

  val create_body_discret_measurement_channel_settings: ChainBuilder = group("create_body_discrete_setting") {
    exec(session => {
      val _id = java.util.UUID.randomUUID.toString
      val body: JsValue = Json.obj(
        "rulesDictionaryCode" -> "TDICT_SYSTEM_SOURCE",
        "businessTask" -> "4.0",
        "normAttributeTag" -> "ch_arg_4",
      )
      val newsession = session.set("BODY_DISCRET_SETTING", body)
      newsession
    })
  }

  val random = new Random

  val get_analog_chanel_group: ChainBuilder = group("get_analog_chanel") {
    exec(action_htswitcher.get_analog_chanel)
  }

  val auth_static: ChainBuilder = group("auth_static") {
    exec(actions_keycloak.auth)
      .exec(session => {
        properties._token = session("TOKEN").as[String]
        println("********************* _token " + session("TOKEN").as[String])
        session
      })
  }


  val Scenario_load_hts_analogue: ScenarioBuilder = scenario("hts_load_analogue")
    .during(100, "during_name1")( //
      pace(60 seconds) //
        .exec(session => {
        val session0 = session.set("TOKEN", properties._token)
        val session2 = session0.set("OBJECT_ID", properties.OBJECT_ID) //38523a35-09ec-45e4-b375-0083f320d920
        val newsession = session2.set("MODEL_ID", properties.MODEL_ID)
        newsession
      })

        .exec(session => {
          //                  val session1 = session.set("ID_ANALOG_CHANNEL", "f9276872-ec4d-4d1c-a869-333bf5e669e4")
          val session2 = session.set("ID_ANALOGUE_CHANNEL", properties.IDS_ANALOG_CHANELS_LIST(random.nextInt(properties.IDS_ANALOG_CHANELS_LIST.length)))
          val session3 = session2.set("ID_DISCRETE_CHANNEL", properties.IDS_DISCRETE_CHANELS_LIST(random.nextInt(properties.IDS_DISCRETE_CHANELS_LIST.length)))
          //          println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + session2("ID_ANALOG_CHANNEL").as[String])
          session3
        })

        .exec(action_htswitcher.get_analog_chanel)
        .exec(action_htswitcher.get_analog_setting)

      //        .exec(action_htswitcher.get_discrete_chanel)
      //        .exec(action_htswitcher.get_discrete_setting)
    )

  val Scenario_load_hts_discrete: ScenarioBuilder = scenario("hts_load_discrete")
    .during(100, "during_name1")( //
      pace(60 seconds) //
        .exec(session => {
        val session0 = session.set("TOKEN", properties._token)
        val session2 = session0.set("OBJECT_ID", properties.OBJECT_ID) //38523a35-09ec-45e4-b375-0083f320d920
        val newsession = session2.set("MODEL_ID", properties.MODEL_ID)
        newsession
      })

        .exec(session => {
          //                  val session1 = session.set("ID_ANALOG_CHANNEL", "f9276872-ec4d-4d1c-a869-333bf5e669e4")
          val session2 = session.set("ID_ANALOGUE_CHANNEL", properties.IDS_ANALOG_CHANELS_LIST(random.nextInt(properties.IDS_ANALOG_CHANELS_LIST.length)))
          val session3 = session2.set("ID_DISCRETE_CHANNEL", properties.IDS_DISCRETE_CHANELS_LIST(random.nextInt(properties.IDS_DISCRETE_CHANELS_LIST.length)))
          //          println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + session2("ID_ANALOG_CHANNEL").as[String])
          session3
        })

        //        .exec(action_htswitcher.get_analog_chanel)
        //        .exec(action_htswitcher.get_analog_setting)

        .exec(action_htswitcher.get_discrete_chanel)
        .exec(action_htswitcher.get_discrete_setting)
    )

  val cr_auth: ScenarioBuilder = scenario("cr_auth_")
    .during(100, "during_name2")( //todo точка
      pace(60 second) //todo точка
        .exec(auth_static)
    ) //todo

  val write_metrics: ScenarioBuilder = scenario("cr_metrics")
    .during(100, "during_name2")( //todo точка
      pace(5 second) //todo точка
        .exec(WriteTimeMonitoring.writeTimeMonitoringScn)
        .exec(session => {
          properties.count_metrics = 0
          properties.summ_metrics = 0
          session
        })
    ) //todo


  val hts_smoke: ScenarioBuilder = scenario("smoke")
    //    .during(1, "during_name1")( //
    .exec(auth_static)
    .exec(session => {
      val session0 = session.set("TOKEN", properties._token)
      val session2 = session0.set("OBJECT_ID", properties.OBJECT_ID)
      val newsession = session2.set("MODEL_ID", properties.MODEL_ID)
      newsession
    })

    .exec(create_body_analog_chanel)
    .exec(action_htswitcher.create_analog_chanel)

    .exec(create_body_discret_chanel)
    .exec(action_htswitcher.create_discret_chanel)

    .exec(action_htswitcher.get_list_chanels_by_objectid)

    .exec(session => {
      session("ALL_CHANELS").as[Vector[String]].foreach(item => {
        val _i: JsValue = Json.parse(item)
        if ((_i \ "channelType").as[String] == "Analogue") {
          println("Analogue")
          properties.IDS_ANALOG_CHANELS_LIST += (_i \ "id").as[String]
        } else {
          println("Discrete")
          properties.IDS_DISCRETE_CHANELS_LIST += (_i \ "id").as[String]
        }
        println(properties.IDS_ANALOG_CHANELS_LIST)
        println(properties.IDS_DISCRETE_CHANELS_LIST)
      })
      //      properties.IDS_ANALOG_CHANELS = session("ALL_CHANELS").as[Vector[String]]
      //      val session1 = session.set("ID_ANALOG_CHANNEL", properties.IDS_ANALOG_CHANELS(random.nextInt(properties.IDS_ANALOG_CHANELS.length)))
      session
    })
    .exec(session => {
      //      properties.IDS_ANALOG_CHANELS = session("IDS_ANALOG_CHANELS").as[Vector[String]]
      val session1 = session.set("ID_ANALOGUE_CHANNEL", properties.IDS_ANALOG_CHANELS_LIST(random.nextInt(properties.IDS_ANALOG_CHANELS_LIST.length)))
      val session2 = session1.set("ID_DISCRETE_CHANNEL", properties.IDS_DISCRETE_CHANELS_LIST(random.nextInt(properties.IDS_DISCRETE_CHANELS_LIST.length)))
      session2
    })

    .exec(create_body_analogue_measurement_channel_settings)
    .pause(5)
    .exec(action_htswitcher.create_analog_measurement_channel_settings)
    .exec(action_htswitcher.create_analog_measurement_channel_settings)
    .exec(action_htswitcher.create_analog_measurement_channel_settings)
    .exec(action_htswitcher.create_analog_measurement_channel_settings)

    .exec(create_body_discret_measurement_channel_settings)
    .pause(5)
    .exec(action_htswitcher.create_discret_measurement_channel_settings)
    .exec(action_htswitcher.create_discret_measurement_channel_settings)
    .exec(action_htswitcher.create_discret_measurement_channel_settings)
    .exec(action_htswitcher.create_discret_measurement_channel_settings)

    .exec(action_htswitcher.get_analog_chanel)
    .exec(action_htswitcher.get_analog_setting)

    .exec(action_htswitcher.get_discrete_chanel)
    .exec(action_htswitcher.get_discrete_setting)

    //    .pause(10 seconds)
    //    .exec(session => {
    //      if (true) java.lang.System.exit(0)
    //      session
    //    })
    .exec(session => {
    if (session.status.toString == "KO") {
      println("Smoke failed, stop test!")
      java.lang.System.exit(0)
    }
    session
  })


  val cr_smoke: ScenarioBuilder = scenario("smoke")
    //    .during(1, "during_name1")( //
    .exec(auth_static)
    .exec(session => {
      val session0 = session.set("TOKEN", properties._token)
      val session2 = session0.set("OBJECT_ID", properties.OBJECT_ID)
      val newsession = session2.set("MODEL_ID", properties.MODEL_ID)
      newsession
    })
    .exec(create_body_controlerule)
    .exec(session => {
      val session1 = session.set("START_TIME", System.currentTimeMillis())
      val session2 = session1.set("FOUND", false)
      session2
    })
    .exec(action_controlerules.create_typeonecontrolrules)
    .exec(session => {
      val newsession = session.set("CRULE_ID", (session("BODY_CRULE").as[JsValue] \ "id").as[String])
      newsession
    })
    .doWhileDuring("${RUN_STATUS}", 30 seconds) {
      exec(action_controlerules.get_status_crule)
        .exec(session => {
          val newsession = session.set("RUN_STATUS", session("STATUS").as[String].equals("\"Unknown\""))
          println("********loop*************  " + newsession("CRULE_ID").as[String] + " " + newsession("STATUS").as[String])
          val newsession2 = newsession.set("FOUND", newsession("RUN_STATUS").as[Boolean])
          newsession2
        })
        .pause(200 milliseconds)
    }
    .doIfOrElse("#{FOUND}") {
      exec(session => {
        println("NOT fOUND")
        val session1 = session.set("CRULE_METRIC", System.currentTimeMillis() - session("START_TIME").as[Long])
        val session2 = session1.set("INFLUXDB_STATUS", "ko")
        println("Smoke failed, stop test!")
        //        java.lang.System.exit(0)
        session2
      })
    } {
      exec(session => {
        val session1 = session.set("CRULE_METRIC", System.currentTimeMillis() - session("START_TIME").as[Long])
        val session2 = session1.set("INFLUXDB_STATUS", "ok")
        session2
      })
    }
    .exec(WriteTimeMonitoring.writeToInflux)
    .exec(action_controlerules.delete_typeonecontrolrules)

    .pause(10 seconds)
    //    .exec(session => {
    //      if (true) java.lang.System.exit(0)
    //      session
    //    })
    .exec(session => {
    if (session.status.toString == "KO" || session {
      "FOUND"
    }.as[Boolean]) {
      println("Smoke failed, stop test!")
      java.lang.System.exit(0)
    }
    session
  })

  val cr_load: ScenarioBuilder = scenario("cr_load")
    .during(100, "during_name1")( //
      pace(20 seconds) //
        .exec(session => {
        val newsession = session.set("TOKEN", properties._token)
        newsession
      })
        .exec(create_body_controlerule)
        .exec(session => {
          val session1 = session.set("START_TIME", System.currentTimeMillis())
          val session2 = session1.set("FOUND", false)
          session2
        })
        .exec(action_controlerules.create_typeonecontrolrules)
        .exec(session => {
          val newsession = session.set("CRULE_ID", (session("BODY_CRULE").as[JsValue] \ "id").as[String])
          newsession
        })
        .doWhileDuring("${RUN_STATUS}", 30 seconds) {
          exec(action_controlerules.get_status_crule)
            .exec(session => {
              val newsession = session.set("RUN_STATUS", session("STATUS").as[String].equals("\"Unknown\""))
              //              println("********loop*************  " + newsession("CRULE_ID").as[String] + " " + newsession("STATUS").as[String])
              val newsession2 = newsession.set("FOUND", newsession("RUN_STATUS").as[Boolean])
              newsession2
            })
            .pause(200 milliseconds)
        }
        .doIfOrElse("#{FOUND}") {
          exec(session => {
            println("NOT fOUND")
            val session1 = session.set("CRULE_METRIC", System.currentTimeMillis() - session("START_TIME").as[Long])
            val session2 = session1.set("INFLUXDB_STATUS", "ko")
            session2
          })
        } {
          exec(session => {
            val session1 = session.set("CRULE_METRIC", System.currentTimeMillis() - session("START_TIME").as[Long])
            val session2 = session1.set("INFLUXDB_STATUS", "ok")
            session2
          })
        }
        //        .exec(session => {
        //          properties.count_metrics = properties.count_metrics + 1
        //          properties.summ_metrics = properties.summ_metrics + (System.currentTimeMillis() - session("START_TIME").as[Long])
        //          session
        //        })
        //                .exec(WriteTimeMonitoring.agregate)

        .exec(WriteTimeMonitoring.writeToInflux)
        .exec(action_controlerules.delete_typeonecontrolrules)
    )

}

