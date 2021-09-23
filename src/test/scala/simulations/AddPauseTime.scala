package simulations

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration.DurationInt

class AddPauseTime extends Simulation {

  //1 Http config

  val httpConfig = http.baseUrl("http://localhost:8080/app/")
    .header("Accept", "application/json")

  //2 Scenario defition
  val scn = scenario("Video Game DB - 3 calls")
    .exec(http("Get all video games - 1st call")
      .get("videogames"))
      .pause(3)

      .exec(http("Get specific game")
      .get("videogames/1"))
      .pause(1, 20)

      .exec(http("Get all video games - 2nd call")
      .get("videogames"))
      .pause(3000.milliseconds)

  //3 Load Scenario
  setUp(
    scn.inject(atOnceUsers(1))
  ).protocols(httpConfig)

}