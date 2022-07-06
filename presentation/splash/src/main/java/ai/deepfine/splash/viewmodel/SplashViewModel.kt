package ai.deepfine.splash.viewmodel

import ai.deepfine.domain.usecase.datastore.GetSample
import ai.deepfine.domain.usecase.datastore.SetSample
import ai.deepfine.domain.utils.UseCase
import ai.deepfine.presentation.base.BaseViewModelImpl
import ai.deepfine.presentation.coroutine.BaseCoroutineScope
import ai.deepfine.splash.util.SplashEvent
import ai.deepfine.utility.utils.EventFlow
import ai.deepfine.utility.utils.MutableEventFlow
import ai.deepfine.utility.utils.asEventFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * @Description SplashViewModel
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
  private val setSample: SetSample,
  private val getSample: GetSample,
  scope: BaseCoroutineScope,
) : BaseViewModelImpl(), BaseCoroutineScope by scope {


  //================================================================================================
  // Properties
  //================================================================================================
  private val _splashEvent = MutableEventFlow<SplashEvent>()
  val splashEvent: EventFlow<SplashEvent>
    get() = _splashEvent.asEventFlow()

  fun showLoginAfterMillis(timeMillis: Long = SPLASH_MILLIS) {
    viewModelScope.launch {
      withContext(coroutineContext) {
        delay(timeMillis)
        _splashEvent.emit(SplashEvent.Login)
      }
    }
  }

  fun setSample(sample: String?) {
    viewModelScope.launch {
      withContext(ioDispatchers) {
        setSample.execute(SetSample.Params(sample))
          .collect {
            getSample()
          }
      }
    }
  }

  private fun getSample() {
    viewModelScope.launch {
      withContext(ioDispatchers) {
        getSample.execute(UseCase.NoneParam)
          .collect {
            _splashEvent.emit(SplashEvent.DataStoreSample(it))
          }
      }
    }
  }

  override fun clearViewModel() {
    releaseCoroutine()
  }

  //================================================================================================
  // Companion object
  //================================================================================================
  companion object {
    private const val SPLASH_MILLIS = 1500L
  }
}