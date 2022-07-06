package ai.deepfine.presentation.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Description LifecycleOwner
 * @author yc.park (DEEP.FINE)
 * @since 2022-05-24
 * @version 1.0.0
 */

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
  lifecycleScope.launch {
    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
  }
}