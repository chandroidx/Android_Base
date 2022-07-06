package ai.deepfine.presentation.base

import ai.deepfine.utility.error.*
import ai.deepfine.utility.utils.EventFlow
import ai.deepfine.utility.utils.MutableEventFlow
import ai.deepfine.utility.utils.asEventFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * @Description BaseViewModel
 * @author yc.park (DEEP.FINE)
 * @since 2021-09-30
 * @version 1.0.0
 */
interface BaseViewModel {
  fun handleFailure(failure: Failure)
  fun handleLoading(showLoading: Boolean)
  fun handleThrowable(throwable: Throwable)

  val event: EventFlow<BaseEvent>

  sealed interface BaseEvent {
    data class Loading(val showLoading: Boolean) : BaseEvent
    data class Failure(val failure: ai.deepfine.utility.error.Failure) : BaseEvent
  }
}

abstract class BaseViewModelImpl : ViewModel(), BaseViewModel {
  private val _event = MutableEventFlow<BaseViewModel.BaseEvent>()
  override val event: EventFlow<BaseViewModel.BaseEvent>
    get() = _event.asEventFlow()

  override fun handleFailure(failure: Failure) {
    _event.emitOn(BaseViewModel.BaseEvent.Failure(failure))
  }

  override fun handleLoading(showLoading: Boolean) {
    _event.emitOn(BaseViewModel.BaseEvent.Loading(showLoading))
  }

  override fun handleThrowable(throwable: Throwable) {
    when (throwable) {
      is NetworkDisconnect -> handleFailure(NetworkConnectionFailure)
      else -> handleFailure(ServerFailure(throwable.message))
    }

    handleLoading(false)
  }

  override fun onCleared() {
    clearViewModel()
    super.onCleared()
  }

  abstract fun clearViewModel()

  fun <T> MutableEventFlow<T>.emitOn(value: T) {
    viewModelScope.launch {
      this@emitOn.emit(value)
    }
  }
}

class EmptyViewModel : BaseViewModel {
  override val event: EventFlow<BaseViewModel.BaseEvent>
    get() = MutableEventFlow<BaseViewModel.BaseEvent>().asEventFlow()

  override fun handleFailure(failure: Failure) {
  }

  override fun handleLoading(showLoading: Boolean) {
  }

  override fun handleThrowable(throwable: Throwable) {
  }
}