package utils

import model.Movie
import play.api.mvc.Results.UnprocessableEntity
import play.api.libs.json.{JsError, JsObject, JsPath, JsResult, JsString, JsSuccess, JsValue, Json, __}

object HawkErrorHelper {


  val missingContentType = UnprocessableEntity("Expected 'Content-Type' set to 'application/json'")
  val missingJsonForm = UnprocessableEntity("Expected content Json form")


  val errorCodeMap :Map[String,Int]= Map(
    (HawkMovieHelper.titleFiled , 1001),
    (HawkMovieHelper.countryField , 1002),
    (HawkMovieHelper.yearField , 1003),
    (HawkMovieHelper.genreField , 1004),
    (HawkMovieHelper.rankingField , 1005),
    (HawkMovieHelper.originalTitleFiled , 1006),
    (HawkMovieHelper.frenchReleaseField , 1007),
    (HawkMovieHelper.synopsisField , 1008)
  )

  def getAndValidateStrField(bodyJs :JsValue, filedName: String, validateFunct : (String) => Boolean, errMsg : String ): Either[String,Option[String]]= {
    val strFieldResult: JsResult[String] = (bodyJs \ filedName).validate[String]
    strFieldResult match {
      case JsSuccess(field, _) => if (validateFunct(field)) Right(Some(strFieldResult.get)) else Left(errMsg)
      case e: JsError         => Right(None)
    }
  }

  def errorMsgResponse(errMsg : String, errCode : Int): JsValue = {
    Json.parse(s"""
      |{
      |"errorCode" : "${errCode}",
      |"errorMsg" :"${errMsg}"
      |}
      |""".stripMargin)
      .validate[JsValue] match {
        case JsSuccess(js:JsValue,path :JsPath) => js
        case JsError(error: Error) => JsObject(Seq("error" -> JsString(s"${error.getMessage}")))
      }
  }

  def getAndValidateIntField(bodyJs :JsValue, filedName: String, validateFunct : (Int) => Boolean, errMsg : String ): Either[String,Option[Int]]= {
    val intFieldResult: JsResult[Int] = (bodyJs \ filedName).validate[Int]

    intFieldResult match {
      case JsSuccess(field, _) => if (validateFunct(field)) Right(Some(intFieldResult.get)) else Left(errMsg)
      case e: JsError         => Right(None)
    }
  }
  def getAndValidateArrayStrField(bodyJs :JsValue, filedName: String, validateFunct : (Array[String]) => Boolean, errMsg : String ): Either[String,Option[Array[String]]]= {
    val arrayStrFieldResult: JsResult[Array[String]] = (bodyJs \ filedName).validate[Array[String]]

    arrayStrFieldResult match {
      case JsSuccess(field, _) => if (validateFunct(field)) Right(Some(arrayStrFieldResult.get)) else Left(errMsg)
      case e: JsError         =>  Right(None)
    }
  }

  def getAndValidateDateTimeField(bodyJs :JsValue, filedName: String, validateFunct : (String) => Boolean, errMsg : String ): Either[String,Option[String]]= {
    val dateStrFieldResult: JsResult[String] = (bodyJs \ filedName).validate[String]

    dateStrFieldResult match {
      case JsSuccess(field, _) => if (validateFunct(field)){
        try {
          Right(Some(field))
        } catch {
          case ex => Left(s"${errMsg}")
        }
      }else Left(errMsg)
      case e: JsError         => Right(None)
    }
  }

  def validateTitleField(bodyJs : JsValue,movie : Movie): Either[JsValue,Movie] = {
    def validate(s :String) : Boolean = s.size > 0 && s.size < 250
    getAndValidateStrField(bodyJs, HawkMovieHelper.titleFiled,validate,s"'${HawkMovieHelper.titleFiled}' 250 caractères maximum")
    match {
      case Right(strFieldOpt) => if (strFieldOpt.isDefined) Right(movie.copy(title = strFieldOpt.get))else Left(errorMsgResponse(s"${HawkMovieHelper.titleFiled} champ obligatoire !", 1000))
      case Left(errMsg) => Left(errorMsgResponse(errMsg, errorCodeMap.get(HawkMovieHelper.titleFiled).get))
    }
  }
  def validateCountryField(bodyJs : JsValue,movie : Movie): Either[JsValue,Movie] = {
    def validate(s :String) : Boolean = s.size == 3
    getAndValidateStrField(bodyJs, HawkMovieHelper.countryField,validate,s"'${HawkMovieHelper.countryField}' Format ISO 3166-1 alpha-3 (3 caractères)")
    match {
      case Right(strFieldOpt) => if (strFieldOpt.isDefined) Right(movie.copy(country = strFieldOpt.get)) else Left(errorMsgResponse(s"${HawkMovieHelper.countryField} champ obligatoire !", 1000))
      case Left(errMsg) => Left(errorMsgResponse(errMsg, errorCodeMap.get(HawkMovieHelper.countryField).get))
    }
  }
  def validateYearField(bodyJs : JsValue,movie : Movie): Either[JsValue,Movie] = {
    def validate(i :Int) : Boolean = i > 1850 && i < 2021
    getAndValidateIntField(bodyJs, HawkMovieHelper.yearField,validate,s"'${HawkMovieHelper.yearField}' year")
    match {
      case Right(strFieldOpt) => if (strFieldOpt.isDefined) Right(movie.copy(year = strFieldOpt.get)) else Left(errorMsgResponse(s"${HawkMovieHelper.yearField} champ obligatoire !", 1000))
      case Left(errMsg) => Left(errorMsgResponse(errMsg, errorCodeMap.get(HawkMovieHelper.yearField).get))
    }
  }
  def validateOriginaleTitleField(bodyJs : JsValue,movie : Movie): Either[JsValue,Movie] = {
    def validate(s :String) : Boolean = s.size > 0 && s.size < 250
    getAndValidateStrField(bodyJs, HawkMovieHelper.originalTitleFiled,validate,s"'${HawkMovieHelper.originalTitleFiled}' 250 caractères maximum")
    match {
      case Right(strFieldOpt) => {
        if (!strFieldOpt.isDefined && movie.country.equals("FRA")) Left(errorMsgResponse(s"${HawkMovieHelper.originalTitleFiled} champ obligatoire !", 1000))
        else Right(movie.copy(original_title = strFieldOpt))
      }
      case Left(errMsg) => Left(errorMsgResponse(errMsg, errorCodeMap.get(HawkMovieHelper.originalTitleFiled).get))
    }
  }
  def validateSypnosisField(bodyJs : JsValue,movie : Movie): Either[JsValue,Movie] = {
    def validate(s :String) : Boolean = s.size > 0 && s.size < 250
    getAndValidateStrField(bodyJs, HawkMovieHelper.synopsisField,validate,s"'${HawkMovieHelper.synopsisField}' 250 caractères maximum")
    match {
      case Right(strFieldOpt) => Right(movie.copy(synopsis = strFieldOpt))
      case Left(errMsg) =>  Left(errorMsgResponse(errMsg, errorCodeMap.get(HawkMovieHelper.synopsisField).get))
    }
  }
  def validatefrenchReleaseField(bodyJs : JsValue,movie : Movie): Either[JsValue,Movie] = {
    def validate(s :String) : Boolean = s.size > 0 && s.size < 250
    getAndValidateDateTimeField(bodyJs, HawkMovieHelper.frenchReleaseField,validate,s"'${HawkMovieHelper.frenchReleaseField}' format YYYY/MM/DD (ex: 2016/08/23")
    match {
      case Right(dateFieldOpt) => Right(movie.copy(french_release = dateFieldOpt))
      case Left(errMsg) => Left(errorMsgResponse(errMsg, errorCodeMap.get(HawkMovieHelper.frenchReleaseField).get))
    }
  }
  def validateGenreField(bodyJs : JsValue,movie : Movie): Either[JsValue,Movie] = {
    def validate(s :Array[String]) : Boolean = s.size > 0
    getAndValidateArrayStrField(bodyJs, HawkMovieHelper.genreField,validate,s"'${HawkMovieHelper.genreField}' list size > 0")
    match {
      case Right(arrayOpt) => if (arrayOpt.isDefined) Right(movie.copy(genre = arrayOpt.get.toList.map(_.toLowerCase()))) else Left(errorMsgResponse(s"${HawkMovieHelper.genreField} champ obligatoire !", 1000))
      case Left(errMsg) => Left(errorMsgResponse(errMsg, errorCodeMap.get(HawkMovieHelper.genreField).get))
    }
  }
  def validaterankingField(bodyJs : JsValue,movie : Movie): Either[JsValue,Movie] = {
    def validate(i :Int) : Boolean = i >= 0 && i <= 10
    getAndValidateIntField(bodyJs, HawkMovieHelper.rankingField,validate,s"'${HawkMovieHelper.rankingField}' entre 1 et 10")
    match {
      case Right(intFieldOpt) => if (intFieldOpt.isDefined) Right(movie.copy(ranking = intFieldOpt.get)) else Left(errorMsgResponse(s"${HawkMovieHelper.rankingField} champ obligatoire !", 1000))
      case Left(errMsg) => Left(errorMsgResponse(errMsg, errorCodeMap.get(HawkMovieHelper.rankingField).get))
    }
  }

  def validateCreateMovieForm(bodyJs :JsValue): Either[JsValue,Movie] = {
    val movie = Movie ("", "", 0, Nil, 0)
    for{
      mvTitle <- validateTitleField(bodyJs, movie)
      mvCountry <- validateCountryField(bodyJs, mvTitle)
      mvYear <- validateYearField(bodyJs, mvCountry)
      mvOriginaleTitle <- validateOriginaleTitleField(bodyJs, mvYear)
      mvSyspnosis <- validateSypnosisField(bodyJs, mvOriginaleTitle)
      mvFrenchRelease <- validatefrenchReleaseField(bodyJs, mvSyspnosis)
      mvGenre <- validateGenreField (bodyJs, mvFrenchRelease)
      finalResult <- validaterankingField(bodyJs, mvGenre)
    } yield finalResult
  }

}
