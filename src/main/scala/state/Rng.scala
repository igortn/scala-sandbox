package state

import State._

trait Rng {
  def next(s: Seed): (Int, Seed)
}

case class SimpleRng() extends Rng {

  def next(s: Seed): (Int, Seed) = {
    val s1 = (s * 0x5DEECE66DL + 0xB) & 0xFFFFFFFFFFFFL
    val n1 = (s1 >>> 16).toInt
    (n1, s1)
  }

}

object Rng {
  val intGen: State[Seed,Int] = State(
    s => SimpleRng().next(s)
  )

  val intPairsGen: State[Seed,(Int,Int)] = for {
    x <- intGen
    y <- intGen
  } yield (x,y)

  val doubleGen: State[Seed, Double] = for {
    x <- intGen
  } yield Math.abs(x.toDouble/Int.MaxValue)

  def intsGen(count: Int): State[Seed, List[Int]] = {
    if (count < 1)
      unit(List.empty)
    else if (count == 1)
      for {
        x <- intGen
      } yield List(x)
    else
      for {
        x <- intGen
        xs <- intsGen(count-1)
      } yield x :: xs
  }
}
