package controllers

import play.api._
import play.api.mvc._
import models.Questions

object Application extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index(Questions.approved))
  }

}
