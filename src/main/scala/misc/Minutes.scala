package misc

case class Minutes(minutes: Int) {
  val duration: Long = minutes * 60000
}
