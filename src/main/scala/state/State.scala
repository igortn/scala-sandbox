package state

/**
  * Wrapper around computation that carries a state along.
  */
case class State[S,A](run: S => (A,S)) {

  def unit(a: A): State[S,A] =
    State(s => (a, s))

  def map[B](f: A => B): State[S,B] =
    State(s => {
      val (a, s1) = run(s)
      (f(a), s1)
    })

  def flatMap[B](f: A => State[S,B]): State[S,B] =
    State(s => {
      val (a, s1) = run(s)
      val State(rb) = f(a)
      rb(s1)
    })
}
