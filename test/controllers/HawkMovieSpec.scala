package controllers

import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class HawkMovieSpec  extends PlaySpec with GuiceOneAppPerTest with Injecting {


  val messageForTest = "Dog Day Afternoon"
  val welcomeMessage = "Welcome to Hawk homework tabmo API!"



  "HawkMovie GET Movies " should {

    "render the HawkMovie list from a new instance of controller" in {
      val controller = new  HawkMovieController(stubControllerComponents())
      val home = controller.getAllMovies().apply(FakeRequest(GET, "/hawk/services/movies"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include (messageForTest)
    }

    "render the HawkMovie list from the application" in {
      val controller = inject[HawkMovieController]
      val home = controller.getAllMovies().apply(FakeRequest(GET, "/hawk/services/movies"))

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include (messageForTest)
    }

    "render the HawkMovie list from the router" in {
      val request = FakeRequest(GET, "/hawk/services/movies")
      val home = route(app, request).get

      status(home) mustBe OK
      contentType(home) mustBe Some("application/json")
      contentAsString(home) must include (messageForTest)
    }
  }
}
