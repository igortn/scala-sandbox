package fpscala.parser

trait Parsers[Err, Parser[+ _]] {
  self =>

  def run[A](p: Parser[A])(input: String): Either[Err, A]

  def char(c: Char): Parser[Char]

  /** *******************************************/

  implicit def string(s: String): Parser[String]

  implicit def operators[A](p: Parser[A]) = ParserOps[A](p)

  implicit def asStringParser[A](a: A)(implicit f: A => Parser[String]): ParserOps[String] =
    ParserOps(f(a))

  def or[A](p1: Parser[A], p2: Parser[A]): Parser[A]

  def listOfN[A](n: Int, p: Parser[A]): Parser[List[A]]

//  def zeroPlus(c: Char): Parser[Int] =
//    map(many(char(c)))(_.size)




  case class ParserOps[A](p: Parser[A]) {
    def |[B >: A](p1: Parser[B]): Parser[B] = self.or(p, p1)

    def or[B >: A](p1: Parser[B]): Parser[B] = self.or(p, p1)

    //def many[A](p: Parser[A]): Parser[List[A]]

    //def map[A, B](p: Parser[A])(f: A => B): Parser[B]
  }

}

