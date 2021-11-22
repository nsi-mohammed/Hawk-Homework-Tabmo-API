import sbt._
//import sbt.Opts._

import java.io.FileInputStream
import java.util.Properties

import play.sbt.PlayImport._


object BuildProperties {
  def readProperties(fileName: String): Option[Properties] = {
    try {
      val prop = new Properties()
      prop.load(new FileInputStream("project/"+fileName))
      println("Using properties from: "+fileName)
      Some(prop)
    }catch{
      case e: Exception =>
        //println("Exception reading: "+fileName + ": " + e.getMessage)
        None
    }
  }

  val properties = readProperties("hawk.properties").orElse(readProperties("default.properties"))
  val localProperties = readProperties("../../../hawk-local.properties").orElse(readProperties("local-default.properties"))

  //if you want to set the defaults in this file...
  val projectNameProperty: String = properties.flatMap( props => Option(props.getProperty("project.name"))).getOrElse("myproject")

  //if you want to set the defaults in .sbt file...
  def propertyOption(key: String): Option[String] = properties.flatMap(props => Option(props.getProperty(key)))

  val userName: String = localProperties.flatMap( props => Option(props.getProperty("repoUser"))).getOrElse("no-user-defined")
  val password: String = localProperties.flatMap( props => Option(props.getProperty("repoPassword"))).getOrElse("no-user-pssword-defined")

  //  println(s"===================>>>>> from file ${BuildProperties.localProperties} found use ${BuildProperties.userName}")
  //  println(s"===================>>>>> properties ${BuildProperties.localProperties}")

}

/**
 * Application settings. Configure the build for your application here.
 * You normally don't have to touch the actual build definition after this.
 */


object Settings {
  val name = "hawk-homework-tabmo-api"


  /** Declare global dependency versions here to avoid mismatches in multi part dependencies */
  object versions {
    val scala = "2.12.4"
    val posgresql = "9.1-901.jdbc4"
    val scalikejdbc = "3.5.0"
    val sbtGiter8Scaffold = "0.11.0"
    val play = "2.8.8"
    val circeVersion = "0.11.0"

    /*
    val scalaTest = "3.0.5"
    val log4js = "1.4.10"
    val autowire = "0.2.6"
    val booPickle = "1.2.6"
    val uTest = "0.4.7"
    val akkaJsVersion = "1.2.5.8"
    val mockitoVersion = "1.10.19"
*/

    //    val hicpVersion = "4.11.1"
  /*  val hicpVersion = BuildProperties.propertyOption("hicp.bof.version").get // pas de version pas de compilation
    val hicpWebVersion = "1.0.19"
    val hicpBasicComponentVersion = "1.0.10"*/
  }

}
