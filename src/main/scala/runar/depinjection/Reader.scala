/*
 * Generalization of the DB example - the Reader monad.
 * C is any type that needs to be injected.
 *
 * Here is how the Reader monad is defined in Haskell:
 *   data Reader r a = Reader { runReader :: r -> a } .
 * The Scala definition below is quite similar.
 */

package runar.depinjection

case class Reader[C, A](g: C => A) {
  def apply(c: C): A = g(c)

  def map[B](f: A => B): Reader[C, B] =
    f compose g

  def flatMap[B](f: A => Reader[C, B]): Reader[C, B] =
    (c: C) => f(g(c))(c)
}

object Reader {
  implicit def reader[A, B](f: A => B): Reader[A, B] =
    Reader(f)

  def pure[C, A](a: A): Reader[C, A] = _ => a
}