package runar.depinjection

import java.sql.DriverManager

abstract class ConnProvider {
  def apply[A](f: DB[A]): A
}

object ConnProvider {
  def mkProvider(driver: String, url: String): ConnProvider =
    new ConnProvider {
      override def apply[A](db: DB[A]): A = {
        Class.forName(driver)
        val conn = DriverManager.getConnection(url)
        try { db(conn) }
        finally { conn.close() }
      }
    }

  lazy val sqliteTestDB: ConnProvider =
    mkProvider("org.sqlite.JDBC", "jdbc:sqlite::memory:")

  lazy val prodDB: ConnProvider =
    mkProvider("prod.db.driver.class", "jdbc:prod://prod.db.url")

  def runInTest[A](f: ConnProvider => A): A =
    f(sqliteTestDB)

  def runInProd[A](f: ConnProvider => A): A =
    f(prodDB)
}
