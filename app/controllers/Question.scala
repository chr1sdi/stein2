package controllers

import play.api._
import play.api.mvc._

object Question extends Controller {

  def propose  = Action {
    Ok(views.html.propose())
  }

  def submit = Action {
    Redirect(routes.Application.index)
  }
}
