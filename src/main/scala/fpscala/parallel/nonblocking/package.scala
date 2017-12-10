package fpscala.parallel

import java.util.concurrent.ExecutorService

package object nonblocking {
  type Par[A] = ExecutorService => Future[A]
}
