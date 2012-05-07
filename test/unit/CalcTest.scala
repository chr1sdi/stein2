package unit

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class CalcTest extends Specification{
  "addind two numbers" in {
    "must result in addition of them " must have size(32)
  }

}
