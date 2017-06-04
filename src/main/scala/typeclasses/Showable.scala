package typeclasses

/**
  * Type class which abstracts over a pretty-printing strategy.
  * @tparam T
  */
trait Showable[T] {

  def show(x: T): String

}

/**
  * Companion object to keep implicits in.
  */
object Showable {

  implicit object IntShowable extends Showable[Int] {
    def show(x: Int): String = x.toString
  }

}