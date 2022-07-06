package ai.deepfine.presentation.coroutine

import ai.deepfine.presentation.BuildConfig
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UICoroutineScope @Inject constructor() : BaseCoroutineScope {
  override val job: Job = Job()
  private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()
  }

  override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + coroutineExceptionHandler + job

  override val ioDispatchers: CoroutineContext
    get() = Dispatchers.IO + coroutineExceptionHandler

  override fun releaseCoroutine() {
    if (BuildConfig.DEBUG) {
      Log.d("UICoroutineScope", "onRelease coroutine")
    }
    job.cancel()
  }
}