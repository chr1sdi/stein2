package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import org.clapper.markwrap._
import models.{Packs, Questions}

object Pack extends Controller {

  def current = Action { implicit request =>
    request.session.get("pack").map { ids =>
      val questions = Questions.many(ids.split(';').filter(_.nonEmpty).distinct.toList)
      Ok(views.html.pack(questions)).withSession(
        "pack" -> questions.foldLeft(";") { (acc, q) => acc + ";" + q.id.get.toStringMongod }
      )
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

  def save = Action { implicit request =>
    NotImplemented
  }

  def retireve(identifier: String) = Action {
    NotImplemented
  }
}
