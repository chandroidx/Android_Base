package ai.deepfine.testutils

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-21
 * @version 1.0.0
 */
@OptIn(InternalCoroutinesApi::class)
class TestUIContext : CoroutineDispatcher(), Delay {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    block.run()
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
    continuation.resume(Unit, null)
  }
}