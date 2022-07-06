package ai.deepfine.utility.extensions

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */

val String.nullIfEmpty: String?
  get() = if (this.isEmpty()) {
    null
  } else {
    this
  }

fun String.toRequestBody(): RequestBody = this.toRequestBody("text/plain".toMediaTypeOrNull())