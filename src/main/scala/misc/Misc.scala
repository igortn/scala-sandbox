package misc

object Misc {

  def shuffle[A](as: List[A]): List[A] = {
    import scala.collection.mutable.ListBuffer

    val in = as.to[ListBuffer]
    val out = ListBuffer[A]()
    val r = new java.util.Random()

    while (in.nonEmpty) {
      val index = r.nextInt(in.size)
      out += in(index)
      in -= in(index)
    }

    out.toList
  }

  def reverse[A](as: List[A]): List[A] =
    as.foldLeft(Nil: List[A])((ys, y) => y :: ys)
}
