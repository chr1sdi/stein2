package models

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.ValidBSONType.BasicDBList

case class Question(question:String, id:String = null, tags: List[String] = List.empty[String])

object Questions{
  val connection = MongoConnection()

  def unapproved = connection("stein")("questions").filterNot { o =>
      o.getAsOrElse[Boolean]("approved", false)
    }.map { o =>
      Question(
        o.getAs[String]("question").get,
        o.getAs[ObjectId]("_id").get.toStringMongod,
        o.getAsOrElse[BasicDBList]("tags", new BasicDBList()).toList.map(_.toString))
    }.toList

  def propose(question: String, tags: List[String] = List.empty[String]) = connection("stein")("questions") +=
    DBObject("question" -> question, "approved" -> false, "tags" -> tags)

  def approved = connection("stein")("questions").filter { o =>
      o.getAsOrElse("approved", false)
    }.map(o => {
      Question(
        question = o.getAs[String]("question").get,
        tags = o.getAsOrElse[BasicDBList]("tags", new BasicDBList()).toList.map(_.toString))
    }).toList

  def approve(id: String) {
    connection("stein")("questions").update(
      MongoDBObject("_id" ->  new ObjectId(id)),
      $set("approved" -> true))
  }

}