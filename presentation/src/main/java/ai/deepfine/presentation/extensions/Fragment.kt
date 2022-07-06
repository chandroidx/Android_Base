package ai.deepfine.presentation.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

fun Fragment.showToast(msg: String) {
  Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToast(resId: Int) {
  showToast(getString(resId))
}

fun Fragment.weakFragment(): Fragment? {
  return WeakReference(this).get()
}

fun Fragment.transactionReplace(layoutId: Int, fragment: Fragment) {
  childFragmentManager.beginTransaction().replace(layoutId, fragment).commit()
}