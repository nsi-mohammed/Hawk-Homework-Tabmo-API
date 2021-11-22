package utils

import model.Movie
import org.junit.Test
import play.api.libs.json.{JsError, JsSuccess}
import utils.HawkMovieHelper.movieJsFromObject

class HawkMovieHelperTest {

  val movie =  Movie("les Trois Frères", "FR" , 1995, List("comédie"), 9, synopsis = Some("Le même jour, trois hommes découvrent qu'ils sont frères et héritent de 3 millions. Mais dix jours plus tard, l'héritage est détourné... et la galère commence pour trois frères qui n'ont que faire d'être frères."))

  @Test
  def movieJsFromObject01(): Unit ={
    val result: String = (movieJsFromObject(movie) \ HawkMovieHelper.titleFiled).validate[String] match {
      case JsSuccess(field, _) => field
      case e: JsError         => e.get
    }
    println("result => " + result)
    assert( result.equals("les Trois Frères"))
  }

  @Test
  def addMovieTest01(): Unit= {
    val result = HawkMovieHelper.addMovie(movie,Nil)
    assert(result.size == 1)
    assert(result.head.title.equals("les Trois Frères"))
  }

  @Test
  def getAllMovieTest01(): Unit= {
    val result = HawkMovieHelper.getAllMovies(List(movie))

    val resultSize: Int = (result \ "Size").validate[Int] match {
      case JsSuccess(field, _) => field
      case e: JsError         => -1
    }

    assert(resultSize ==  1)
  }

}
