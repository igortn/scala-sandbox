package typeclasses

trait CanFoo[A] {
  def foo(a: A): String
}

case class Wrapper(wrapped: String)

/*********************************************************
 * We want to enrich the Wrapper type with the CanFoo
 * functionality while keeping these two decoupled.
 * We need to provide the "evidence" that Wrapper complies
 * to CanFoo. It's convenient to make this evidence implicit
 * in order not to pass it around explicitly every time we need
 * to invoke foo() on a Wrapper.
 *
 * In addition, we can use an implicit conversion to make
 * foo() look like it's available on the Wrapper instance.
 */

object CanFoo {
  def apply[A:CanFoo]: CanFoo[A] = implicitly

  implicit val wrapperCanFooInstance: CanFoo[Wrapper] = new CanFoo[Wrapper] {
    override def foo(w: Wrapper): String = w.wrapped
  }

  implicit class CanFooConverter[A:CanFoo](a: A) {
    def foo: String = CanFoo[A].foo(a)
  }
}

object Main {
  // We have to import the CanFoo namespace
  // to resolve implicits
  import CanFoo._

  def run: String = Wrapper("abc").foo
}