import scala.collection.immutable.Stream.Empty

object streams {

  // corecursive function that generates a stream
  def unfold[A,S](s: S)(f: S => Option[(A,S)]): Stream[A] = f(s) match {
    case None => Empty
    case Some((a, s1)) => a #:: unfold(s1)(f)
  }

  def from(n: Int): Stream[Int] =
    unfold(n)(i => Some((i, i+1)))

  def constant[A](a: A): Stream[A] =
    unfold(a)(a => Some((a, a)))

}
