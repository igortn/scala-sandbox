package runar.depinjection

trait KeyValueStore {
  def put(k: String, v: String): Unit
  def get(k: String): String
  def delete(k: String): Unit
}

object KeyValueStore {
  import runar.depinjection.Reader._

  def modify(k: String, f: String => String): Reader[KeyValueStore, Unit] =
    for {
      v <- (_: KeyValueStore).get(k)
      _ <- (_: KeyValueStore).put(k, f(v))
    } yield ()
}