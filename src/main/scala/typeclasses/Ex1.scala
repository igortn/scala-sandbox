package typeclasses


object Ex1 {

  def show[T](x: T)(implicit s: Showable[T]): String = s.show(x)

  def main(args: Array[String]): Unit = {

    println(show(42))

    // Implicit not found. Custom error message is printed (annotation on the trait).
    // println(show("42"))
  }

}
