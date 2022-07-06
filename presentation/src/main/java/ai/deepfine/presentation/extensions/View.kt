package ai.deepfine.presentation.extensions

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2021-12-14
 * @version 1.0.0
 */

@ObsoleteCoroutinesApi
fun View.debounce(delayMillis: Long = 300L, action: suspend (View) -> Unit) {
  val event = GlobalScope.actor<View>(Dispatchers.Main) {
    for (event in channel) {
      action(event)
      delay(delayMillis)
    }
  }
  setOnClickListener {
    event.trySend(it)
  }
}

fun View.showKeyboard() {
  val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  this.requestFocus()
  imm.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
  val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
  imm.hideSoftInputFromWindow(windowToken, 0)
}