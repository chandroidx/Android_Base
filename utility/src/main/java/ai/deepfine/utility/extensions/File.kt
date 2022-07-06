package ai.deepfine.utility.extensions

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import androidx.core.content.FileProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2021-12-13
 * @version 1.0.0
 */
fun File.toMultipartBody(param: String): MultipartBody.Part =
  MultipartBody.Part.createFormData(
    param,
    name,
    asRequestBody("multipart/form-data".toMediaTypeOrNull())
  )

fun File.moveTo(newFile: File) {
  FileInputStream(this).use { `in` ->
    FileOutputStream(newFile).use { out ->
      val buf = ByteArray(1024)
      var len: Int
      while (`in`.read(buf).also { len = it } > 0) {
        out.write(buf, 0, len)
      }
      this.delete()
    }
  }
}

fun File.scan(context: Context) {
  MediaScannerConnection.scanFile(context, arrayOf(toString()), arrayOf(name), null)
}

fun File.getUriFromProvider(context: Context): Uri =
  FileProvider.getUriForFile(context, "ai.deepfine.fileprovider", this)