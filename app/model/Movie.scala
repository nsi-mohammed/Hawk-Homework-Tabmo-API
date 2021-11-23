package model
/*
import play.api.libs.json.Json
import scalikejdbc.WrappedResultSet
import scalikejdbc._
import scalikejdbc.async._
import scalikejdbc.async.FutureImplicits._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
*/
case class Movie(
                 title : String,
                 country :String,
                 year : Int,
                 genre : List[String],
                 ranking : Int,
                 original_title : Option[String] = None,
                 french_release : Option[String] = None,
                 synopsis : Option[String] = None
               ) extends Ordered[Movie] {

  override def compare(that: Movie): Int = {
    val res = this.year - that.year
        if(res == 0) {
          if (this.title.equals(that.title) ) 0
          else if (this.title < that.title) -1
          else 1
        }
        else if (res < 0) -1
        else 1
  }
}

/*
object Movie extends SQLSyntaxSupport[Movie] {

  implicit val jsonFormat = Json.format[Movie]

  override val columnNames = Seq("id", "name")

  lazy val b = Movie.syntax

  def db(b: SyntaxProvider[Movie])(rs: WrappedResultSet): Movie = db(b.resultName)(rs)

  def db(b: ResultName[Movie])(rs: WrappedResultSet): Movie = MovieMovie(
    rs.long(b.id),
    rs.string(b.name)
  )

  def create(name: String)(implicit session: AsyncDBSession = AsyncDB.sharedSession): Future[Movie] = {
    val sql = withSQL(insert.into(Movie).namedValues(column.name -> name).returningId)
    sql.updateAndReturnGeneratedKey().map(id => Movie(id, name))
  }

  def findAll(implicit session: AsyncDBSession = AsyncDB.sharedSession): Future[List[Movie]] = {
    withSQL(select.from[Movie](Movie as b)).map(Movie.db(b))
  }

}
*/