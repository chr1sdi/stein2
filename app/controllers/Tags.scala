package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import models.Questions
import org.clapper.markwrap._

object Tags extends Controller with Secured{

  def tagList = Action{

    Ok(views.html.tagList(List.empty[String]))
  }

}
