// This implementation is based on the java Future which blocks
// on the get() call. Thus, this is not fully non-blocking implementation.

package fpscala.parallel.blocking

import java.util.concurrent.{Callable, ExecutorService, Future, TimeUnit}


object Par {

  private case class UnitFuture[A](a: A) extends Future[A] {
    override def cancel(mayInterruptIfRunning: Boolean): Boolean = false

    override def isCancelled: Boolean = false

    override def isDone: Boolean = true

    override def get(): A = a

    override def get(timeout: Long, unit: TimeUnit): A = a
  }

  def unit[A](a: A): Par[A] = (_: ExecutorService) => UnitFuture(a)

  def map2[A, B, C](pa: Par[A], pb: Par[B])(f: (A, B) => C): Par[C] =
    s => {
      val fa = pa(s)
      val fb = pb(s)
      new Future[C] {
        override def cancel(mayInterruptIfRunning: Boolean): Boolean =
          fa.cancel(mayInterruptIfRunning) && fb.cancel(mayInterruptIfRunning)

        override def isCancelled: Boolean = fa.isCancelled || fb.isCancelled

        override def isDone: Boolean = fa.isDone && fb.isDone

        override def get(): C = f(fa.get, fb.get)

        override def get(timeout: Long, unit: TimeUnit): C = {
          val millisStarted = System.currentTimeMillis
          val a = fa.get(timeout, unit)
          val millisPassed = System.currentTimeMillis - millisStarted
          val unitsPassed = unit.convert(millisPassed, TimeUnit.MILLISECONDS)
          val b = fb.get(timeout - unitsPassed, unit)
          f(a, b)
        }
      }
    }

  def fork[A](pa: => Par[A]): Par[A] =
    s => s.submit(new Callable[A] {
      override def call(): A = pa(s).get
    })

  def delay[A](pa: => Par[A]): Par[A] =
    es => pa(es)

  def lazyUnit[A](a: => A): Par[A] = fork(unit(a))

  def run[A](s: ExecutorService)(pa: Par[A]): Future[A] = pa(s)

  /*******************************************/

  def asyncF[A,B](f: A => B): A => Par[B] =
    a => lazyUnit(f(a))

  // lift a function A => B to Par[A] => Par[B]
  def map[A,B](pa: Par[A])(f: A => B): Par[B] =
    map2(pa, unit(-1))((a,_) => f(a))

  def sortPar(parList: Par[List[Int]]): Par[List[Int]] =
    map(parList)(_.sorted)

  def sequence[A](ps: List[Par[A]]): Par[List[A]] =
    ps.foldRight[Par[List[A]]](unit(List()))((pa, pb) => map2(pa, pb)(_ :: _))

  // wrap it in a call to fork to make it a thunk that gets handed to run
  def parMap[A,B](as: List[A])(f: A => B): Par[List[B]] = fork {
    val fbs: List[Par[B]] = as.map { asyncF(f) }
    sequence(fbs)
  }

  // anything more straightforward than this?
  def parFilter[A](as: List[A])(p: A => Boolean): Par[List[A]] = {
    val pars: List[Par[List[A]]] = as map { asyncF((a: A) => if (p(a)) List(a) else Nil) }
    map(sequence(pars))(_.flatten)
  }

  def equal[A](es: ExecutorService)(p1: Par[A], p2: Par[A]): Boolean =
    p1(es).get == p2(es).get
}
