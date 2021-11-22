package utils

import play.api.libs.json.{JsError, JsObject, JsPath, JsSuccess, JsValue, Json}

object HawkJsonHelper {

  def parseStr(str : String ):Either [String, JsValue]= {
    val res = Json.parse(str)
    val y = res.validate[JsValue]
    y match {
      case JsSuccess(js:JsValue,path :JsPath) => Right(js)
      case JsError(error: Error) => Left(error.getMessage)
    }
  }
  def extractField(js :JsValue, path : String)= {
  /*  val pathArray = path.split("\\.")
    var jsResponse = js
    pathArray.foreach(e => {
      var newJsResponse = (jsResponse \ e)
      newJsResponse match {
        case JsSuccess(json) => newJsResponse = js
        case JsError(err) =>
      }

      if (newJsResponse.isNull || newJsResponse == null ){
        newJsResponse = emptyJson()
        // throw new Exception(s"error : no Path ${path} found to extract Field")
      }else {
        jsResponse = newJsResponse
      }
    })*/

  }

}
