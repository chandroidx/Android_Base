package ai.deepfine.splash.view

import ai.deepfine.presentation.base.BaseActivity
import ai.deepfine.presentation.extensions.repeatOnStarted
import ai.deepfine.presentation.extensions.showToast
import ai.deepfine.splash.R
import ai.deepfine.splash.databinding.ActivitySplashBinding
import ai.deepfine.splash.util.SplashEvent
import ai.deepfine.splash.viewmodel.SplashViewModel
import android.annotation.SuppressLint
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Description SplashActivity
 * @author yc.park (DEEP.FINE)
 * @since 2021-12-13
 * @version 1.0.0
 */
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>(R.layout.activity_splash), SplashEvent.Observer {
  override val viewModel: SplashViewModel by viewModels()

  //================================================================================================
  // Initialize
  //================================================================================================
  override fun onBind() {
    with(binding) {
      lifecycleOwner = this@SplashActivity
      viewModel = this@SplashActivity.viewModel
    }

    repeatOnStarted {
      viewModel.splashEvent.collect(::observeEvent)
    }
  }

  override fun initView() {
    viewModel.setSample("123")
    viewModel.showLoginAfterMillis()
  }

  //================================================================================================
  // Observe
  //================================================================================================
  override fun observeDataStoreSample(sample: SplashEvent.DataStoreSample) {
    binding.textView.text = sample.data
  }

  override fun observeLogin() {
    showToast("화면 이동")
  }
}