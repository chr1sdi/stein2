import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "stein"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "com.mongodb.casbah" %% "casbah" % "2.1.5-1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(

    )

}
