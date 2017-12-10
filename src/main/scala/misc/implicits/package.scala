package misc

package object implicits {

  implicit def intToMinutes(n: Int): Minutes = Minutes(n)

}
