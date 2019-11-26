package scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import utils.Config
import io.gatling.core.Predef.scenario

import scala.concurrent.duration._

object SubmitForm {

  val newPassword = "SomePass"
  val feeder = csv("./src/test/resources/example.csv")
  val header_ = Map("Accept" -> "*/*")

  val selfServiceApplicationRegistration = scenario("Create and submit application")
    .feed(feeder)
    .exec(http("get login page")
      .get("auth/login/")
      .disableFollowRedirect
      .headers(header_)
      .check(
        regex(Config.SECURITY_TOKEN_PATTERN).
          find.saveAs("securityToken")))
    .pause(300 milliseconds)
    .exec(http("login")
      .post("auth/login/")
      .check(regex(Config.LOCATION).find.optional.saveAs("Location"))
      .formParam("username", "${Username}")
      .formParam("password", "${Password}")
      .formParam("submit", "Sign in")
      .formParam("security", "${securityToken}"))
    .exec(session => session.set("expired-password", "${Location}"))
    .pause(450 milliseconds)
    .doIf(session => session("expired-password").toString.isEmpty.equals(false)) {
      exec(http("change password")
        .post("auth/expired-password/${Location}")
        .formParam("oldPassword", "${Password}")
        .formParam("newPassword", "Password1")
        .formParam("confirmPassword", "Password1")
        .formParam("submit", "Save")
        .formParam("security", "${securityToken}")
        .check(bodyString.saveAs("login_response")))
    }
    .pause(700 milliseconds)
    .exec(session => {
      println(session("login_response").toString)
      session
    })
    .pause(850 milliseconds)
    .exec(http("submit address")
      .post("application/${applicationId}")
      .formParam("data[isExternal]", "Y")
      .formParam("contactDetails[fao]", "Inspector Gadget")
      .formParam("address[searchPostcode][postcode]", "ABC EFG")
      .formParam("address[addressLine1]", "APARTMENT 18")
      .formParam("address[addressLine2]", "ABC DEF")
      .formParam("address[town]", "MADEUP")
      .formParam("address[postcode]", "ABC DEF")
      .formParam("address[countryCode]", "XY")
      .formParam("form-actions[submit]", "")
      .formParam("security", "${securityToken}"))
    .pause(500 milliseconds)
    .exec(flushSessionCookies)
}