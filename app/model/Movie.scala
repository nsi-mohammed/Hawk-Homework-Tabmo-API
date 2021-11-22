package model

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
