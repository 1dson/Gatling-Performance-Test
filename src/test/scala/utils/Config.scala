package utils

import com.typesafe.config.{Config, ConfigFactory}
import io.gatling.core.Predef._

import scala.annotation.switch

object Config {

  val CONFIG: Config = ConfigFactory.defaultApplication()

  val ENV: String = System.getProperty("env", "ps")
  val USERS: Int = Integer.getInteger("users",0).toInt
  val THROUGHPUT: Int = Integer.getInteger("throughput",5).toInt
  val RAMP_DURATION: Int = Integer.getInteger("duration",10).toInt

  val BASE_URL: String =
    (ENV.toUpperCase: @switch) match {
      case "ENV" => CONFIG.getString("env")
    }

  val FEEDER_FILE = csv("example.csv").circular
  val SECURITY_TOKEN_PATTERN = """id="security" value="([^"]*)&*"""
}