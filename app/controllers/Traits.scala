package controllers

import com.mongodb.casbah.Imports._
import play.api.mvc.{Controller, Action}
import org.apache.commons.codec.binary.Base64

trait Secured { this : Controller =>
  def Secured[A](username: String, password: String)(action: Action[A]) = Action(action.parser) { request =>
    request.headers.get("Authorization").flatMap { authorization =>
      authorization.split(" ").drop(1).headOption.filter { encoded =>
        new String(Base64.decodeBase64(encoded.getBytes)).split(":").toList match {
          case u :: p :: Nil if u == username && password == p => true
          case _ => false
        }
      }.map(_ => action(request))
    }.getOrElse {
      Unauthorized("Listen son, you better not be trying to hack me!").withHeaders("WWW-Authenticate" -> """Basic realm="Secured"""")
    }
  }
}
