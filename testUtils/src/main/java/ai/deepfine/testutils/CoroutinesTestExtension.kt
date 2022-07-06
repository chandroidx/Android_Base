package ai.deepfine.testutils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

/**
 * @Description Class
 * @author jh.kim (DEEP.FINE)
 * @since 3/5/21
 * @version 1.0.0
 */
@ExperimentalCoroutinesApi
class CoroutinesTestExtension(
  private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
) : BeforeEachCallback, AfterEachCallback, TestCoroutineScope by TestCoroutineScope(dispatcher) {

  override fun beforeEach(context: ExtensionContext?) {
    Dispatchers.setMain(dispatcher)
  }

  override fun afterEach(context: ExtensionContext?) {
//    cleanupTestCoroutines()
    Dispatchers.resetMain()
  }
}