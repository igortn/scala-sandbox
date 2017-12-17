/**
  * Conceptually the same example as CanFoo
  * but with multiple typeclasses.
  */

package typeclasses

case class Person(firstName: String, lastName: String, ssn: String)

trait Show[A] {
  def show(a: A): String
}

object Show {
  def apply[A : Show]: Show[A] = implicitly

  implicit val personShowInst: Show[Person] = new Show[Person] {
    override def show(p: Person): String = s"${p.firstName} ${p.lastName}"
  }

  implicit class ShowConverter[A:Show](a: A) {
    def show: String = Show[A].show(a)
  }

}

trait Eq[A] {
  def <>(a1: A, a2: A): Boolean
  def ><(a1: A, a2: A): Boolean = ! <>(a1, a2)
}

object Eq {
  def apply[A : Eq]: Eq[A] = implicitly

  implicit val personEqInst: Eq[Person] = new Eq[Person] {
    override def <>(p1: Person, p2: Person): Boolean = p1.ssn == p2.ssn
  }

  implicit class EqConverter[A:Eq](a: A) {
    def <>(a1: A): Boolean = Eq[A].<>(a, a1)
    def ><(a1: A): Boolean = Eq[A].><(a, a1)
  }
}

/****************************
  * Runner
  */
object PersonMain {
  import Show._
  import Eq._

  val p1 = Person("Bob", "Smith", "123-45-7890")
  val p2 = Person("Mary", "Jones", "345-67-1234")
  val p3 = Person("Robert", "Smith", "123-45-7890")

  def run1(): Unit = {
    println(s"p1: ${p1.show}")
    println(s"p2: ${p2.show}")
  }

  def run2(): Unit = {
    println(s"p1 <> p2: ${p1 <> p2}")
    println(s"p1 <> p3: ${p1 <> p3}")
    println(s"p1 >< p2: ${p1 >< p2}")
  }
}
