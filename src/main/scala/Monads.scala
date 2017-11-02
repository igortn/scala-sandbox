object Monads {

  trait Functor[T] {
    def map[S](f: T => S): Functor[S]
  }

  trait Monad[T] extends Functor[T] {
    def flatMap[S](f: T => Monad[S]): Monad[S]
  }
}
