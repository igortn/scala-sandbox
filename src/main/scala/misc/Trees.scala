package misc

import scala.collection.mutable


sealed trait Tree[A]
case class Leaf[A](value: A) extends Tree[A]
case class Node[A](value: A, children: List[Tree[A]]) extends Tree[A]

object Trees {

  val exTree: Tree[Int] = Node(7, List(
      Node(5, List(Leaf(8), Leaf(3)))
    , Node(11, List(Leaf(9))), Node(7, List(Leaf(1)))
    , Node(2, List(Leaf(9), Leaf(10)))
  ))

  def breadthFirstTraversal[A](tree: Tree[A], f: A => Unit) {
    val q = new mutable.Queue[Tree[A]]()

    def processNodeOrLeaf(node: Tree[A]) {
      node match {
        case Leaf(v) => f(v)
        case Node(v, cs) =>
          f(v)
          q.enqueue(cs: _*)
      }
    }

    def processQueue() {
      while (q.nonEmpty)
        processNodeOrLeaf(q.dequeue())
    }

    processNodeOrLeaf(tree)
    processQueue()
  }
}


