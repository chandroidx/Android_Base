package ai.deepfine.presentation.base

import ai.deepfine.presentation.R
import ai.deepfine.presentation.extensions.repeatOnStarted
import ai.deepfine.presentation.view.DeepFineIndicator
import ai.deepfine.utility.error.Failure
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ai.deepfine.presentation.extensions.showToast
import ai.deepfine.utility.error.NetworkConnectionFailure
import ai.deepfine.utility.error.ServerFailure
import ai.deepfine.utility.extensions.nullIfEmpty
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlinx.coroutines.launch

/**
 * @Description BaseActivity
 * @author yc.park (DEEP.FINE)
 * @since 2022-01-28
 * @version 1.0.0
 */
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel>(
  private val layoutId: Int,
) : AppCompatActivity() {
  //================================================================================================
  // Properties
  //================================================================================================
  lateinit var binding: B
  abstract val viewModel: VM

  //================================================================================================
  // Lifecycle
  //================================================================================================
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, layoutId)

    onBind()
    initView()
    observeBase()
  }

  //================================================================================================
  // Abstract
  //================================================================================================
  abstract fun onBind()
  abstract fun initView()

  //================================================================================================
  // Observe
  //================================================================================================
  private fun observeBase() {
    with(viewModel) {
      repeatOnStarted {
        event.collect(::observeEvent)
      }
    }
  }

  private fun observeEvent(event: BaseViewModel.BaseEvent) {
    when (event) {
      is BaseViewModel.BaseEvent.Loading -> observeLoading(event.showLoading)
      is BaseViewModel.BaseEvent.Failure -> observeFailure(event.failure)
    }
  }

  protected open fun observeLoading(isLoading: Boolean) {
    when (isLoading) {
      true -> {
        DeepFineIndicator.showLoading(this)
      }
      false -> {
        DeepFineIndicator.hideLoading()
      }
    }
  }

  protected open fun observeFailure(failure: Failure) {
    when (failure) {
      is NetworkConnectionFailure -> {
        showToast(getString(R.string.common_network_disconnected_try_again))
      }

      is ServerFailure -> {
        failure.message?.nullIfEmpty?.let {
          showToast(it)
        } ?: showToast(getString(R.string.common_server_request_error))
      }

      else -> {
        showToast(failure.toString())
      }
    }
  }
}