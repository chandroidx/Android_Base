package ai.deepfine.utility.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

/**
 * @Description Class
 * @author jh.kim (DEEP.FINE)
 * @since 4/14/21
 * @version 1.0.0
 */
object DialogUtils {
  fun showAlertDialog(
    context: Context,
    title: Any? = null,
    msg: Any? = null,
    positiveButtonText: Int = android.R.string.ok,
    negativeButtonText: Int = android.R.string.cancel,
    cancelable: Boolean = false,
    positive: (() -> Unit)? = {},
    negative: (() -> Unit)? = {},
  ) {
    try {
      AlertDialog.Builder(context).apply {
        if (title != null) {
          when (title) {
            is Int -> setTitle(title)
            is String -> setTitle(title)
          }
        }
        if (msg != null) {
          when (msg) {
            is Int -> setMessage(msg)
            is String -> setMessage(msg)
          }
        }
        setCancelable(cancelable)

        setPositiveButton(positiveButtonText) { d, _ ->
          positive?.let { it() }
          d.dismiss()
        }
        setNegativeButton(negativeButtonText) { d, _ ->
          negative?.let { it() }
          d.dismiss()
        }

        show()
      }
    } catch (e: Exception) {
      L.e(e)
    }
  }

  fun showAlertDialog(
    context: Context,
    title: Any? = null,
    msg: Any? = null,
    positiveButtonText: Int = android.R.string.ok,
    negativeButtonText: Int = android.R.string.cancel,
    neutralButtonText: Int? = null,
    cancelable: Boolean = false,
    positive: (() -> Unit)? = {},
    negative: (() -> Unit)? = {},
    neutral: (() -> Unit)? = null,
  ) {
    try {
      AlertDialog.Builder(context).apply {
        if (title != null) {
          when (title) {
            is Int -> setTitle(title)
            is String -> setTitle(title)
          }
        }
        if (msg != null) {
          when (msg) {
            is Int -> setMessage(msg)
            is String -> setMessage(msg)
          }
        }
        setCancelable(cancelable)

        setPositiveButton(positiveButtonText) { d, _ ->
          positive?.let { it() }
          d.dismiss()
        }
        setNegativeButton(negativeButtonText) { d, _ ->
          negative?.let { it() }
          d.dismiss()
        }
        neutralButtonText?.let {
          setNeutralButton(it) { d, _ ->
            neutral?.invoke()
            d.dismiss()
          }
        }

        show()
      }
    } catch (e: Exception) {
      L.e(e)
    }
  }

  fun showAlertDialog(
    context: Context,
    title: Any? = null,
    msg: Any? = null,
    buttonText: Int = android.R.string.ok,
    cancelable: Boolean = false,
    callback: (() -> Unit)? = {},
  ) {
    try {
      AlertDialog.Builder(context).apply {
        if (title != null) {
          when (title) {
            is Int -> setTitle(title)
            is String -> setTitle(title)
          }
        }
        if (msg != null) {
          when (msg) {
            is Int -> setMessage(msg)
            is String -> setMessage(msg)
          }
        }
        setCancelable(cancelable)
        setPositiveButton(buttonText) { d, _ ->
          callback?.let { it() }
          d.dismiss()
        }

        show()
      }
    } catch (e: Exception) {
      L.e(e)
    }
  }
}