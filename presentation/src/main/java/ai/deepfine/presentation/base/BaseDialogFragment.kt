package ai.deepfine.presentation.base

import ai.deepfine.presentation.R
import ai.deepfine.presentation.extensions.repeatOnStarted
import ai.deepfine.presentation.extensions.showToast
import ai.deepfine.presentation.view.DeepFineIndicator
import ai.deepfine.utility.error.Failure
import ai.deepfine.utility.error.NetworkConnectionFailure
import ai.deepfine.utility.error.ServerFailure
import ai.deepfine.utility.extensions.nullIfEmpty
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.launch

/**
 * @Description BaseDialogFragment
 * @author yc.park (DEEP.FINE)
 * @since 2021-03-09
 * @version 1.0.0
 */
abstract class BaseDialogFragment<B : ViewDataBinding, VM : BaseViewModel>(
  private val layoutId: Int,
) : DialogFragment() {
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
    setStyle(STYLE_NO_TITLE, R.style.DialogTheme)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
    return binding.root
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return object : Dialog(requireContext(), theme) {
      override fun onBackPressed() {
        onBackPressedDialog()
      }
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    onBind()
    initView()

    observeBase()
  }

  //================================================================================================
  // Abstract
  //================================================================================================
  abstract fun onBind()
  abstract fun initView()

  open fun onBackPressedDialog() {
    super.dismiss()
  }

  //================================================================================================
  // Observe
  //================================================================================================
  private fun observeBase() {
    with(viewModel) {
      repeatOnStarted {
        launch {
          event.collect(::observeEvent)
        }
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
        DeepFineIndicator.showLoading(requireContext())
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

  //================================================================================================
  // Functions
  //================================================================================================
  protected fun applyPopupAnimation() {
    dialog?.window?.setWindowAnimations(R.style.DialogPopupAnimation)
  }
}