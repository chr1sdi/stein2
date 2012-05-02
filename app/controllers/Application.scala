package controllers

import play.api._
import play.api.mvc._
import com.mongodb.casbah.Imports._

object Application extends Controller {

  val connection = MongoConnection()

  def index = Action {
    val questions = connection("stein")("questions").map(o => {
      models.Question(o.getAs[String]("question").get)
    }).toList

    Ok(views.html.index(questions))
  }

}
