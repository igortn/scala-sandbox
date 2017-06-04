package implicitmacro

/**
  * Type class which abstracts over a pretty-printing strategy.
  * @tparam T
  */
trait Showable[T] {
  def show(x: T): String
}
