package utils

object Headers {

  val headers: Map[String,String] = Map("x-api-key" -> Config.CONFIG.getString("apiKey"),
    "Accept" -> "application/json",
    "Content-Type" -> "application/json")
}