package models

import com.mongodb.casbah.Imports._
import com.mongodb.casbah.commons.ValidBSONType.BasicDBList
import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._
import com.novus.salat.annotations._

case class Question(@Key("_id") id: Option[ObjectId] = None,
                    question:String,
                    answer: String,
                    tags: List[String] = List.empty[String],
                    approved: Boolean = false)

object Questions{
  val connection = MongoConnection()
  def collection = connection("stein")("questions")

  def single(id:String) = collection.findOne(MongoDBObject("_id" ->  new ObjectId(id))).map(grater[Question].asObject(_)).get
  def many(ids:List[String]) = collection.find("_id" $in ids.map(new ObjectId(_))).map(grater[Question].asObject(_)).toList
  def withTag(tag: String) = collection.find(MongoDBObject("tags" -> tag, "approved" -> true)).map(grater[Question].asObject(_)).toList
  def unapproved = collection.find(MongoDBObject("approved" -> false)).map(grater[Question].asObject(_)).toList
  def propose(question: Question) = collection += grater[Question].asDBObject(question)
  def approved = collection.find(MongoDBObject("approved" -> true)).map(grater[Question].asObject(_)).toList
  def approve(id: String) {
    collection.update(
      MongoDBObject("_id" ->  new ObjectId(id)),
      $set("approved" -> true))
  }
}