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

  def map[A,B](s: Stream[A])(f: A => B): Stream[B] = ???

  def take[A](s: Stream[A])(n: Int): Stream[A] = ???

  def takeWhile[A](s: Stream[A])(p: A => Boolean): Stream[A] = ???

//  def zipWith[A]
//
//  def zipAll[B](s2: Stream[B]): Stream[(Option[A],Option[B])]

}


