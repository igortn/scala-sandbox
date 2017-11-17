package misc

object Misc {

  def shuffle[A](list: List[A]): List[A] = {
    import scala.collection.mutable.ListBuffer

    val in = list.to[ListBuffer]
    val out = ListBuffer[A]()
    val r = new java.util.Random()

    while (in.nonEmpty) {
      val index = r.nextInt(in.size)
      out += in(index)
      in -= in(index)
    }

    out.toList
  }
}
