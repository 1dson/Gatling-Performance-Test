package simulations

import io.gatling.core.Predef._
import io.gatling.core.scenario.Scenario
import io.gatling.http.Predef._

import scala.concurrent.duration._

class MySimulation extends Simulation {

  val httpConfiguration = http
    .baseUrl(Config.BASE_URL)
    .headers(Headers.headers)
    .disableCaching
    .disableWarmUp
    .silentResources
    .perUserNameResolution
    .maxConnectionsPerHostLikeChrome

  val fetchOfficersDetails =
    Scenario.getOfficersDetailsScenario.inject(atOnceUsers(Config.USERS),
      rampUsers(Config.THROUGHPUT) during (Config.RAMP_DURATION minutes))
  setUp(fetchOfficersDetails)
    .protocols(httpConfiguration)
    .assertions(global.failedRequests.count.is(0))
}
