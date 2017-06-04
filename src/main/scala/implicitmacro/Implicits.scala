package implicitmacro


object Implicits {

  implicit object IntShowable extends Showable[Int] {
    def show(x: Int): String = x.toString
  }

}
