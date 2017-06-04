package typeclasses

import scala.annotation.implicitNotFound

/**
  * Type class which abstracts over a pretty-printing strategy.
 *
  * @tparam T
  */
@implicitNotFound(msg = "No instance of typeclass Showable found for type ${T}.")
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