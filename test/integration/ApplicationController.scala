package integration

import org.specs2.mutable.Specification
import play.api.test.TestServer
import play.api.libs.ws.WS
import play.api.test._
import play.api.test.Helpers._
import org.specs2.mutable._

class ApplicationController extends Specification {

//  "run in a server" in {
//    running(TestServer(3333)) {
//
//      await(WS.url("http://localhost:3333").get).status must equalTo(OK)
//
//    }
//  }
//
//  "run in a browser" in {
//    running(TestServer(3333), HTMLUNIT) { browser =>
//
//      browser.goTo("http://localhost:3333")
//      browser.$("#main-header").getTexts.get(0) must equalTo("Questions")
//      browser.$("#propose-question").click()
//      browser.$("#main-header").getTexts().get(0) must equalTo("Propose New Question")
//
//    }
//  }
}
