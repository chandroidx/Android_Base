package ai.deepfine.presentation.extensions

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

internal fun Activity.weakActivity(): Activity? {
  return WeakReference(this).get()
}

fun Activity.showToast(resId: Int) {
  showToast(getString(resId))
}

fun Activity.showToast(msg: String) {
  Toast.makeText(this@showToast, msg, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.transactionReplace(layoutId: Int, fragment: Fragment) {
  supportFragmentManager.beginTransaction().replace(layoutId, fragment).commit()
}