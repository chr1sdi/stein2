package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import com.mongodb.casbah.Imports._
import models.Question

object Question extends Controller {

  val connection = MongoConnection()

  val proposal = Form(
    single("question", nonEmptyText)
  )

  def propose  = Action {
    Ok(views.html.propose())
  }

  def submit = Action { implicit request =>

    proposal.bindFromRequest.fold(
      errors => Ok(views.html.propose()),
      success => {
        connection("stein")("questions") += DBObject("question" -> success)
        Redirect(routes.Application.index())
      }
    )
  }
}
