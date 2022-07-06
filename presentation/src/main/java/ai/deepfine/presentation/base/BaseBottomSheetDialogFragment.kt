package ai.deepfine.presentation.base

import ai.deepfine.presentation.extensions.showToast
import ai.deepfine.presentation.view.DeepFineIndicator
import ai.deepfine.utility.error.Failure
import ai.deepfine.utility.extensions.nullIfEmpty
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ai.deepfine.presentation.R
import ai.deepfine.presentation.extensions.repeatOnStarted
import ai.deepfine.utility.error.NetworkConnectionFailure
import ai.deepfine.utility.error.ServerFailure
import kotlinx.coroutines.launch

/**
 * @Description BaseBottomSheetDialogFragment
 * @author yc.park (DEEP.FINE)
 * @since 2021-03-31
 * @version 1.0.0
 */
abstract class BaseBottomSheetDialogFragment<B : ViewDataBinding, VM : BaseViewModel>(
  private val layoutId: Int,
) : BottomSheetDialogFragment() {
  //================================================================================================
  // Properties
  //================================================================================================
  lateinit var binding: B
  abstract val viewModel: VM

  //================================================================================================
  // Lifecycle
  //================================================================================================
  override fun onStart() {
    super.onStart()

    dialog?.let {
      val bottomSheet: View = dialog!!.findViewById(R.id.design_bottom_sheet)
      BottomSheetBehavior.from(bottomSheet).apply {
        state = BottomSheetBehavior.STATE_EXPANDED
        isHideable = false
      }
    }
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    return BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialog).apply {
      setCanceledOnTouchOutside(true)
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
    return binding.root
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
}