package models

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.ValidBSONType.BasicDBList
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._
import com.novus.salat.annotations._

case class Question(@Key("_id") id: Option[ObjectId] = None,
                    question:String,
                    tags: List[String] = List.empty[String],
                    approved: Boolean = false)

object Questions{
  val connection = MongoConnection()
  
  def withTag(tag: String) = connection("stein")("questions").find(MongoDBObject("tags" -> tag)).map(grater[Question].asObject(_)).toList
  def unapproved = connection("stein")("questions").find(MongoDBObject("approved" -> false)).map(grater[Question].asObject(_)).toList
  def propose(question: Question) = connection("stein")("questions") += grater[Question].asDBObject(question)
  def approved = connection("stein")("questions").find(MongoDBObject("approved" -> true)).map(grater[Question].asObject(_)).toList
  def approve(id: String) {
    connection("stein")("questions").update(
      MongoDBObject("_id" ->  new ObjectId(id)),
      $set("approved" -> true))
  }

}