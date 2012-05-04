package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import org.clapper.markwrap._
import models.{Packs, Questions}
import com.mongodb.casbah.commons.Imports._

object Pack extends Controller {

  val pack = Form(
    tuple(
      "identifier" -> nonEmptyText,
      "questions" -> nonEmptyText
    )
  )

  def current = Action { implicit request =>
    request.session.get("pack").map { ids =>
      val questions = Questions.many(ids.split(';').filter(_.nonEmpty).distinct.toList)
      Ok(views.html.pack(questions)).withSession(
        "pack" -> questions.foldLeft(";") { (a, q) => a + ";" + q.id.get.toStringMongod }
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
    pack.bindFromRequest.fold(
      errors => Redirect(routes.Pack.current()),
      newPack => {
        Packs.insert(models.Pack(
          questions = newPack._2.split(';').filter(_.nonEmpty).distinct.map(new ObjectId(_)).toList,
          identifier = newPack._1))

        Redirect(routes.Pack.retrieve(newPack._1))
      }
    )
  }

  def retrieve(identifier: String) = Action {
    Ok(views.html.pack(Packs.retrieve(identifier).map { pack =>
      Questions.many(pack.questions.map(_.toStringMongod))
    }.getOrElse {
      List.empty[models.Question]
    }))
  }
}
