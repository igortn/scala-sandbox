package misc

import implicits._

object ImplicitsExamples extends App {

  // This syntax is enabled by implicit conversion from Int to Minutes
  val m = 7 minutes

  println(s"${m.minutes} minutes")
  println(s"duration = ${m.duration}")

}
