package controllers


import akka.actor.Status.Success

import javax.inject._
import play.api.mvc._
import utils.HawkErrorHelper.{missingContentType, validateCreateMovieForm}
import utils.HawkMovieHelper
import utils.HawkMovieHelper.{movieJsFromObject, movieListDbMacok}

/**
 * This controller creates ACTIONS to handle HTTP requests to the
 * HawK Movies API REST.
 * - Create Movie
 * - GET ALL MOVIES
 */
@Singleton
class HawkMovieController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {



  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def getAllMovies() = Action { implicit request: Request[AnyContent] => {
      val genFilterOpt =  request.headers.toSimpleMap.get("Raw-Request-URI")

      if (genFilterOpt.isDefined && genFilterOpt.get.contains("?genre=")) {
        val filter : String= genFilterOpt.get.split("\\?").last.split("=").last
        println("filter =>" + filter)
        val filtredList  = HawkMovieHelper.getAllMovies(HawkMovieHelper.movieListDbMacok.sorted.filter(movie => movie.genre.contains(filter.toLowerCase)))
        Ok(filtredList)
      }else {
        Ok(HawkMovieHelper.getAllMovies(HawkMovieHelper.movieListDbMacok.sorted))
      }
    }
  }

  def createMovie() = Action { req =>
    req.body.asJson
      .toRight(missingContentType)
      .map { form => {
        validateCreateMovieForm(form) match {
          case Right(movie) => {
            HawkMovieHelper.addMovie(movie,movieListDbMacok)
            Created(movieJsFromObject(movie))
          }
          case Left(e) => BadRequest(e)
          }
        }
      }
      .merge
  }

  def getStatistics()= Action { implicit request: Request[AnyContent] => {

    Ok(HawkMovieHelper.getStatisticsMovieByYear(HawkMovieHelper.movieListDbMacok))
    }
  }
}