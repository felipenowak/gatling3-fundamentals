package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class CsvFeeder extends Simulation {

  //1 Http config

  val httpConfig = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  //2 Scenario defition


  val csvFeeder = csv("data/gameCsvFile.csv").circular

  def getSpecificVideoGame() = {
    repeat(10) {
      feed(csvFeeder)
        .exec(http("Get Specific video game")
          .get("videogames/${gameId}")
          .check(jsonPath("$.name").is("${gameName}"))
          .check(status.is(200)))
        .pause(1)
    }
  }

  val scn = scenario("Csv Feeder test")
    .exec(getSpecificVideoGame())

  //3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)

}