package ai.deepfine.utility.utils

import ai.deepfine.utility.BuildConfig
import android.util.Log

/**
 * @Description Class
 * @author jh.kim (DEEP.FINE)
 * @since 3/5/21
 * @version 1.0.0
 */
@Suppress("unused")
object L {
  // log tag
  private const val TAG = "DeepFine"

  // 개발계 빌드 시 에만 로그 노출
  private val isDev: Boolean = BuildConfig.DEBUG

  /**
   * debug
   * msg : 로그 메시지
   */
  @JvmStatic
  fun d(msg: String) {
    if (isDev) {
      try {
        Log.d(TAG, msg)
      } catch (e: Exception) {
        Log.e(TAG, "error : $e")
      }
    }
  }

  @JvmStatic
  fun d(tag: String = TAG, msg: String) {
    if (isDev) {
      try {
        Log.d(tag, msg)
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }
    }
  }

  /**
   * info
   * msg : 로그 메시지
   */
  @JvmStatic
  fun i(msg: String) {
    if (isDev) {
      try {
        Log.i(TAG, msg)
      } catch (e: Exception) {
        Log.e(TAG, "error : $e")
      }
    }
  }

  @JvmStatic
  fun i(tag: String = TAG, msg: String) {
    if (isDev) {
      try {
        Log.i(tag, msg)
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }
    }
  }

  /**
   * verbose
   * msg : 로그 메시지
   */
  @JvmStatic
  fun v(msg: String) {
    if (isDev) {
      try {
        Log.v(TAG, msg)
      } catch (e: Exception) {
        Log.e(TAG, "error : $e")
      }
    }
  }

  @JvmStatic
  fun v(tag: String = TAG, msg: String) {
    if (isDev) {
      try {
        Log.v(tag, msg)
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }
    }
  }

  /**
   * warning
   * msg : 로그 메시지
   */
  @JvmStatic
  fun w(msg: String) {
    if (isDev) {
      try {
        Log.w(
          TAG,
          "======================================================================"
        )
        Log.w(
          TAG,
          "${Throwable().stackTrace[1].className}[Line = ${Throwable().stackTrace[1].lineNumber}]\n"
        )
        Log.w(TAG, msg)
        Log.w(
          TAG,
          "======================================================================"
        )
      } catch (e: Exception) {
        Log.e(TAG, "error : $e")
      }
    }
  }

  @JvmStatic
  fun w(tag: String = TAG, msg: String) {
    if (isDev) {
      try {
        Log.w(
          tag,
          "======================================================================"
        )
        Log.w(
          tag,
          "${Throwable().stackTrace[1].className}[Line = ${Throwable().stackTrace[1].lineNumber}]\n"
        )
        Log.w(tag, msg)
        Log.w(
          tag,
          "======================================================================"
        )
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }
    }
  }

  /**
   * error
   * msg : 로그 메시지
   */
  @JvmStatic
  fun e(msg: String) {
    if (isDev) {
      try {
        Log.e(
          TAG,
          "======================================================================"
        )
        Log.e(
          TAG,
          "${Throwable().stackTrace[1].className}[Line = ${Throwable().stackTrace[1].lineNumber}]\n"
        )

        Log.e(TAG, msg)

        Log.e(
          TAG,
          "======================================================================"
        )
      } catch (e: Exception) {
        Log.e(TAG, "error : $e")
      }
    }
  }

  @JvmStatic
  fun e(t: Throwable?) {
    if (isDev) {
      t?.apply { printStackTrace() }
    }
  }

  @JvmStatic
  fun e(tag: String = TAG, msg: String) {
    if (isDev) {
      try {
        Log.e(
          tag,
          "======================================================================"
        )
        Log.e(
          tag,
          "${Throwable().stackTrace[1].className}[Line = ${Throwable().stackTrace[1].lineNumber}]\n"
        )

        Log.e(tag, msg)

        Log.e(
          tag,
          "======================================================================"
        )
      } catch (e: Exception) {
        Log.e(tag, "error : $e")
      }
    }
  }
}