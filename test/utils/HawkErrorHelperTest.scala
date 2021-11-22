package utils

import play.api.libs.json.{JsError, JsSuccess}
import utils.HawkErrorHelper.errorMsgResponse
import org.junit.Test

class HawkErrorHelperTest {


  // input error json object with code 230 and 'error' as message
  @Test
  def errorMsgResponseTest01(): Unit ={
    val result = errorMsgResponse("error", 230)
    val errMsgResult = (result \ "errorMsg").validate[String] match {
      case JsSuccess(field, _) => field
      case e: JsError         => "e.get"
    }

    val errCodeResult = (result \ "errorCode").validate[String] match {
        case JsSuccess(field, _) => field
        case e: JsError         => "e.get"
    }
    assert(errMsgResult.equals("error"))
    assert(errCodeResult.equals("230"))
  }

}
