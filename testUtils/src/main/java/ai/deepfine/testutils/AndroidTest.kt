package ai.deepfine.testutils

import android.app.Application
import android.content.Context
import android.os.Build
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * @Description Class
 * @author jh.kim (DEEP.FINE)
 * @since 3/5/21
 * @version 1.0.0
 */

@RunWith(RobolectricTestRunner::class)
@Config(
  application = AndroidTest.ApplicationStub::class,
  manifest = Config.NONE,
  sdk = [Build.VERSION_CODES.O_MR1]
)
abstract class AndroidTest {
  fun context(): Context = RuntimeEnvironment.systemContext

  internal class ApplicationStub : Application()
}