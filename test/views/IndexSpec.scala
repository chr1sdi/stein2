package views

import org.specs2.mutable._
import play.api.test._
import play.api.test.Helpers._

class IndexSpec extends Specification {
  "Rendering Index with no questions" should {
    "inform the user" in {
      val html = views.html.index(List.empty[models.Question])

      contentType(html) must equalTo("text/html")
      contentAsString(html) must contain("Oh dear, we have no approved questions yet.")
    }
  }
}
