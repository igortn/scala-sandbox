package misc

sealed trait Result[+T]
case class Ok[T](vs: List[T]) extends Result[T]
case class Fail(msg: String) extends Result[Nothing]

object Result {
  def handleResult(res: Result[String]): Unit = res match {
    case Fail(msg) => println(s"error: $msg")
    case Ok(vs: List[String]) => println(s"strings' total length: ${vs.map(_.length).sum}")
    case _ => println("unknown")
  }

  def run():Unit = {
    handleResult(Fail("something failed"))
    handleResult(Ok(List("one", "two", "three")))
    //handleResult(Ok(List("one", "two", 3)))
  }

}
