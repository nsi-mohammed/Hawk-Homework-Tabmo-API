package utils

import model.Movie
import play.api.libs.json._
import play.api.Logger

object HawkMovieHelper {

  val accessLogger: Logger = Logger("access")

  val titleFiled =  "Titre"
  val countryField = "Nationalité"
  val yearField = "Années de production"
  val originalTitleFiled = "Titre original"
  val frenchReleaseField = "Date de sortie française"
  val synopsisField =   "Synopsis"
  val genreField = "Genres"
  val rankingField = "Note"

  var movieListDbMacok = List (
    Movie("les Trois Frères", "FR" , 1995, List("comédie"), 9, synopsis = Some("Le même jour, trois hommes découvrent qu'ils sont frères et héritent de 3 millions. Mais dix jours plus tard, l'héritage est détourné... et la galère commence pour trois frères qui n'ont que faire d'être frères.")),
    Movie("La Cité de Dieu", "BRA" , 2003, List("drame","policier"), 7, Some("Cidade de Deus"), Some("2003-03-12"), Some("Le film raconte l'histoire de la Cité de Dieu, un quartier violent de Rio de Janeiro, sur une période allant de la fin des années 1960 au milieu des années 1970. Le personnage principal (et narrateur) est issu de ce quartier et veut devenir photographe. À la fois acteur et spectateur des événements, il témoigne ainsi de l'évolution de ce quartier, notamment en ce qui concerne les gangs, l'armement, la drogue et ses amis d'enfance qui ne suivent pas la même voie que lui.")),
    Movie("Un aprés midi de chien", "USA" , 1973, List("policier","thriller"), 6, Some("Dog Day Afternoon"), Some("1973-01-04"), Some("Des gangsters débutants braquent une banque et se retrouvent cernés par la police et les médias. Ils prennent en otage les employés de la banque. Débute alors un cauchemar qui va durer des heures...")),
    Movie("Man on Fire", "USA" , 2004, List("action"), 9, None, Some("2004-07-01"), Some("À Mexico, un ancien agent de la CIA jure de se venger de ceux qui ont commis un acte inqualifiable contre la famille qu'il était chargé de protéger"))
  )

  val frenchCodeKeysMovie = Map(
    "title" -> "Titre",
    "country" -> "Nationalité",
    "year" -> "Années de production",
    "original_title" -> "Titre original",
    "french_release" -> "Date de sortie française",
    "synopsis" -> "Synopsis",
    "genre" -> "Genres",
    "ranking" -> "Note"
  )

  val objTojson: JsValue = JsObject(
    Seq(
      "name"     -> JsString("Watership Down"),
      "location" -> JsObject(Seq("lat" -> JsNumber(51.235685), "long" -> JsNumber(-1.309197))),
      "residents" -> JsArray(
        IndexedSeq(
          JsObject(
            Seq(
              "name" -> JsString("Fiver"),
              "age"  -> JsNumber(4),
              "role" -> JsNull
            )
          ),
          JsObject(
            Seq(
              "name" -> JsString("Bigwig"),
              "age"  -> JsNumber(6),
              "role" -> JsString("Owsla")
            )
          )
        )
      )
    )
  )


  def movieJsFromObject(movie : Movie): JsValue = JsObject(
    Seq(
      frenchCodeKeysMovie("title")          -> JsString(movie.title),
      frenchCodeKeysMovie("country")        -> JsString(movie.country),
      frenchCodeKeysMovie("year")           -> JsNumber(movie.year),
      frenchCodeKeysMovie("original_title") -> JsString(movie.original_title.getOrElse("")),
      frenchCodeKeysMovie("french_release") -> JsString(movie.french_release.getOrElse("")),
      frenchCodeKeysMovie("synopsis")       -> JsString(movie.synopsis.getOrElse("")),
      frenchCodeKeysMovie("genre")          -> {
        JsArray(
          IndexedSeq(
            movie.genre.map( g => JsString(g)) : _ *
          )
        )
      },
      frenchCodeKeysMovie("ranking")        -> JsNumber(movie.ranking)
      )
    )
  def addMovie(movie : Movie, movieList : List[Movie]): List[Movie] = {
    movieListDbMacok = List(movie) ++ movieList
    movieListDbMacok
  }

  def getAllMovies(movieList : List[Movie]): JsValue = {
    JsObject(
      Seq(
      "Size"     -> JsNumber(movieList.size),
      "movies" -> JsArray(
          IndexedSeq(
            movieList.map(movie => movieJsFromObject(movie))  : _ *
          )
        )
      )
    )
  }

}
