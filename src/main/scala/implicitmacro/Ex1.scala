package implicitmacro


object Ex1 {

  def show[T](x: T)(implicit s: Showable[T]): String = s.show(x)

  def main(args: Array[String]): Unit = {

    println(show(42))

  }

}
