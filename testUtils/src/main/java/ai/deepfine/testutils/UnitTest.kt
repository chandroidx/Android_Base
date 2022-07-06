package ai.deepfine.testutils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.extension.ExtendWith

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-25
 * @version 1.0.0
 */
@ExperimentalCoroutinesApi
@ExtendWith(CoroutinesTestExtension::class, InstantExecutorExtension::class)
abstract class UnitTest {
  fun fail(message: String): Nothing = throw AssertionError(message)

  private val testDispatcher by lazy {
    StandardTestDispatcher()
  }

  protected fun runOnTestDispatcher(testBody: suspend TestScope.() -> Unit) = runTest(testDispatcher) {
    testBody()
  }
}