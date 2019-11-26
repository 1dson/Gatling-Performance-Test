package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.Config

object MyScenario {

  val ScenarioDetails = scenario("Get Officers Details")
    .feed(Config.FEEDER_FILE)
    .exec(http("example_scenario")
      .get("${header}/resource")
      .check(status.is(200))
      .check(regex("${header}").exists))
}