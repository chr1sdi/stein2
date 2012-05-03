package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import models.Questions
import org.clapper.markwrap._

object Pack extends Controller {

  def current = Action { implicit request =>
    request.session.get("pack").map { ids =>
      Ok(views.html.pack(Questions.many(ids.split(';').tail.toList)))
    }.getOrElse {
      Redirect(routes.Application.index())
    }
  }

  def add(id:String) = Action { implicit request =>
    Redirect(routes.Application.index).withSession(
      "pack" -> (request.session.get("pack").getOrElse("") + ";" + id)
    )
  }

  def renew = Action {
    Redirect(routes.Application.index).withNewSession
  }
}
