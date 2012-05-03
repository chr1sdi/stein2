package models

import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._
import com.novus.salat.annotations._

case class Pack(@Key("_id") id: Option[ObjectId] = None, questions: List[ObjectId], identifier: String)

object Packs {

  val connection = MongoConnection()
  def collection = connection("stein")("packs")

  def save(pack: Pack) = collection += grater[Pack].asDBObject(pack)
  def retrieve(identifier:String) = collection.findOne(MongoDBObject("identifier" -> identifier)).map(grater[Pack].asObject(_))
}
