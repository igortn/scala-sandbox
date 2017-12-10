package fpscala.parallel

import java.util.concurrent.{ExecutorService, Future}

package object blocking {
  type Par[A] = ExecutorService => Future[A]
}
