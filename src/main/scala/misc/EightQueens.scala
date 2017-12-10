package misc

/**
  * 8 Queens problem and possibly extension to N queens problem
  */
object EightQueens extends App {


  /**
    * All permutations of the list of different integers.
    * Used to generate all possible boards where the queens
    * do not attack each other on horizontals or verticals.
    * They still can attack each other on diagonals which needs
    * to be filtered out further.
    */
  def permutations(xs: List[Int]): List[List[Int]] =
    if (xs.size == 1)
      List(xs)
    else
      for {
        x <- xs
        ys <- permutations(removeInt(x, xs))
      } yield x :: ys

  def removeInt(n: Int, xs: List[Int]): List[Int] =
    (scala.collection.mutable.ListBuffer(xs: _*) -= n).toList

}

// [ [1, 2,3], [1, 3,2], [[2, 1,3], [2, 3,1]], [[3, 1,2], [3, 2,1]] ]
// [ ] ]