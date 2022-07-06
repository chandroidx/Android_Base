package ai.deepfine.presentation.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

interface BaseCoroutineScope : CoroutineScope {
  val job: Job
  val ioDispatchers: CoroutineContext

  fun releaseCoroutine()
}