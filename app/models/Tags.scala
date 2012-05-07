package models


import com.novus.salat._
import com.novus.salat.global._
import com.mongodb.casbah.Imports._
import com.novus.salat.annotations._


object Tags {
  val connection = MongoConnection()
  def collection = connection("stein")("questions")

  def all = List("tag1", "tag2")
}
