package runar

package object depinjection {
  // Following the talk "Dead-Simple Dependency Injection
  // http://functionaltalks.org/2013/06/17/runar-oli-bjarnason-dead-simple-dependency-injection/

  // This is how the whole design logic goes.
  // Write a function that accepts a DB connection and parameters for a query. Than conclude
  // that it's better to provide the connection later at some point and curry the function so
  // it would return a function from a connection to the return type of the result of the query,
  // i.e. Connection => Int, or Connection => Unit, etc. Construct a monadic type (DB action) that
  // wraps this function:
  //    case class DB[A](g: Connection => A) { ... }.
  // Now you can thread your DB state through using monadic operations and inject the connection
  // and finalize the result later.
}
