package fpscala.parallel.nonblocking

import java.util.concurrent.{CountDownLatch, ExecutorService}
import java.util.concurrent.atomic.AtomicReference

sealed trait Future[A] {
  private[nonblocking] def apply(k: A => Unit): Unit
}

object Par {
  def run[A](es: ExecutorService)(p: Par[A]): A = {

    // stores the result
    val ref = new AtomicReference[A]()

    // countDown on this latch is called once when we receive a value of type A from p.
    // run blocks until that happens.
    val latch = new CountDownLatch(1)

    // when we receive the value, sets the result and releases the latch
    p(es) { a => ref.set(a); latch.countDown() }

    // waits until the result becomes available and the latch is released
    latch.await()
    ref.get

  }
}