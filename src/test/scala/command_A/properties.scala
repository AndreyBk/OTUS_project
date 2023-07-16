package command_A

import java.lang.System.getProperty

import scala.collection.mutable.ListBuffer

object properties {
  val login = getProperty("login")
  //  val password = getProperty("pass")
  val clientId = getProperty("clientId")
  //  val baseUrl = getProperty("baseUrl")
  // val bootstrapServer = "zif-kafka-cp-kafka-headless.mon01-dev01:9092"
  val bootstrapServer: String = getProperty("bootstrapServer")
  val kafkaTopic = getProperty("kafkaTopic")
  val kafkaRestUrl = getProperty("kafkaRestUrl")
  //  val influxHost = getProperty("influxHost")

  val username = "test-user"
  val password = "b6dikUaxdBbZ"
  val client_id = "test-client"
  val client_secret = "b6dikUaxdBbZ"
  val influxHost = "192.168.101.67"

  val baseUrl = "https://xxxxx-1.dev.dp.xxxxx.com"
  val uri_xxxxxxxxxxControlrulemanager = "/xxxxx-xxxxx-controlrulemanager"
  //  C:\BK\load_introduction\zif-lt-tsds-main\src\test\scala\LoadTest\properties.scala
  var _token = "123"
  var OBJECT_ID = "53b71492-fc32-4a00-9dcc-4a71388f51c4"
  var MODEL_ID = "65265e78-dc21-4d7b-8262-dfff9d39932b"

  var IDS_ANALOG_CHANELS:Vector[String]=Vector()
  var IDS_ANALOG_CHANELS_LIST:ListBuffer[String]=ListBuffer()
  var IDS_DISCRETE_CHANELS_LIST:ListBuffer[String]=ListBuffer()
  var ID_ANALOG_CHANNEL ="default"
  var count_metrics=0F
  var summ_metrics=0L


}
