package runar.depinjection

import java.sql.Connection

import scala.io.StdIn

case class DB[A](g: Connection => A) {
  def apply(c: Connection): A = g(c)

  def map[B](f: A => B): DB[B] = DB(f compose g)

  def flatMap[B](f: A => DB[B]): DB[B] =
    DB(c => (f compose g)(c)(c))
}

object DB {
  def pure[A](a: A): DB[A] = DB(_ => a)
}

object Queries {

  def setUserPwd(userId: Int, pwd: String): DB[Unit] =
    DB(c => {
      val stmt = c.prepareStatement("update users set pwd = ? where id = ?")
      stmt.setString(1, pwd)
      stmt.setInt(2, userId)
      stmt.executeUpdate
      stmt.close()
    })

  def getUserPwd(userId: Int): DB[String] =
    DB(c => {
      val stmt = c.prepareStatement("select pwd from users where id = ?")
      stmt.setInt(1, userId)
      val resultSet = stmt.executeQuery()
      resultSet.next
      val result = resultSet.getString("pwd")
      resultSet.close()
      stmt.close()
      result
    })
}

object App {

  import DB._
  import Queries._
  import ConnProvider._

  def changePwd(userId: Int, oldPwd: String, newPwd: String): DB[Boolean] =
    for {
      pwd <- getUserPwd(userId)
      result <- if (pwd == oldPwd)
        for {
          _ <- setUserPwd(userId, newPwd)
        } yield true
      else
        pure(false)
    } yield result

  def program(userId: Int): ConnProvider => Unit =
    cp => {
      println("Enter old password")
      val oldPwd = StdIn.readLine()
      println("Enter new password")
      val newPwd = StdIn.readLine()
      cp(changePwd(userId, oldPwd, newPwd))
    }

  def run(): Unit =
    runInTest(program(1))
}
