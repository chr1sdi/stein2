package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import models.Questions
import org.clapper.markwrap._

object Question extends Controller with Secured {

  val proposal = Form(
    tuple(
      "question" -> nonEmptyText,
      "tags" -> text
    )
  )

  val approval = Form(
    single("id", nonEmptyText)
  )

  def propose  = Action {
    Ok(views.html.propose())
  }

  def submit = Action { implicit request =>
    proposal.bindFromRequest.fold(
      errors => Ok(views.html.propose()),
      question => {
        val parser = MarkWrap.parserFor(MarkupType.Markdown)
        val (txt, tags) = question
        Questions.propose(models.Question(
          question = parser.parseToHTML(txt),
          tags = tags.split(" ").toList match {
            case List("") => List.empty[String]
            case list => list
          })
        )
        Redirect(routes.Application.index())
      }
    )
  }

  def unapproved = Action {
    Ok(views.html.unapproved(Questions.unapproved))
  }

  def approve = Secured("admin", "password")  {
    Action { implicit request =>
      approval.bindFromRequest.fold(
        errors => Redirect(routes.Question.unapproved()),
        id => {
          Questions.approve(id)
          Redirect(routes.Application.index())
        }
      )
    }
  }

  def withTag(tag: String) = Action {
    Ok(views.html.index(Questions.withTag(tag)))
  }
}
