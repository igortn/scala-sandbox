package state

trait RNG {
  def nextInt: (Int, RNG)
}

case class SimpleRNG(seed: Long) extends RNG {

  def nextInt: (Int, RNG) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xB) & 0xFFFFFFFFFFFFL
    val nextRNG = SimpleRNG(newSeed)
    val n = (newSeed >>> 16).toInt
    (n, nextRNG)
  }

}

object RNG {
  type Rand[+A] = RNG => (A, RNG)

  def unit[A](a: A): Rand[A] =
    rng => (a, rng)

  def map[A, B](rand: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (a, r) = rand(rng)
      (f(a), r)
    }

  def map2[A,B,C](ra: Rand[A])(rb: Rand[B])(f: (A, B) => C): Rand[C] =
    rng => {
      val (a, r1) = ra(rng)
      val (b, r2) = rb(r1)

      (f(a, b), r2)
    }

  def flatMap[A,B](ra: Rand[A])(f: A => Rand[B]): Rand[B] =
    r => {
      val(a, r1) = ra(r)
      val rb = f(a)
      rb(r1)
    }
}
