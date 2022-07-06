package ai.deepfine.utility.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import java.io.*
import javax.inject.Inject
import kotlin.jvm.Throws

/**
 * @Description FileSaver
 * @author yc.park (DEEP.FINE)
 * @since 2021-06-22
 * @version 1.0.0
 */
interface FileSaver {
  suspend fun saveFile(responseBody: ResponseBody, uri: Uri, progress: suspend (Long, Long) -> Unit, completion: suspend (Uri?) -> Unit)
  suspend fun saveMediaFile(responseBody: ResponseBody, mimeType: String, fileName: String, progress: suspend (Long, Long) -> Unit, completion: suspend (Uri?) -> Unit)
  suspend fun saveBitmap(bitmap: Bitmap, fileName: String, completion: suspend (Uri?) -> Unit)
}

@Suppress("BlockingMethodInNonBlockingContext", "deprecation")
class FileSaverImpl @Inject constructor(@ApplicationContext private val context: Context) : FileSaver {
  @Throws(Exception::class)
  override suspend fun saveFile(responseBody: ResponseBody, uri: Uri, progress: suspend (Long, Long) -> Unit, completion: suspend (Uri?) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
      val contentResolver: ContentResolver = context.contentResolver
      contentResolver.openFileDescriptor(uri, "w", null)?.use { parcelFileDescriptor ->
        writeFile(parcelFileDescriptor, responseBody, uri, progress, completion) {
          sendBroadcastGallery(context, uri)
        }
      } ?: logError(completion, "fail to open FileDescriptor")
    }
  }

  @Throws(Exception::class)
  override suspend fun saveMediaFile(
    responseBody: ResponseBody,
    mimeType: String,
    fileName: String,
    progress: suspend (Long, Long) -> Unit,
    completion: suspend (Uri?) -> Unit,
  ) {
    CoroutineScope(Dispatchers.IO).launch {
      val values = getContentValues(mimeType, fileName)
      val contentResolver: ContentResolver = context.contentResolver
      getFileUri(fileName, contentResolver, values, mimeType)?.let { uri ->
        contentResolver.openFileDescriptor(uri, "w", null)?.use { parcelFileDescriptor ->
          writeFile(parcelFileDescriptor, responseBody, uri, progress, completion) {
            scanDownloadFile(context, uri, values, contentResolver)
          }
        } ?: logError(completion, "fail to open FileDescriptor")
      } ?: logError(completion, "fail to get file's uri")
    }
  }

  @Throws(Exception::class)
  override suspend fun saveBitmap(bitmap: Bitmap, fileName: String, completion: suspend (Uri?) -> Unit) {
    val mimeType = "image/png"

    CoroutineScope(Dispatchers.IO).launch {
      val values = getContentValues(mimeType, fileName)
      val contentResolver: ContentResolver = context.contentResolver
      getFileUri(fileName, contentResolver, values, mimeType)?.let { uri ->
        contentResolver.openFileDescriptor(uri, "w", null)?.use { parcelFileDescriptor ->
          writeBitmapFile(parcelFileDescriptor, bitmap, uri, completion) {
            scanDownloadFile(context, uri, values, contentResolver)
          }
        } ?: logError(completion, "fail to open FileDescriptor")
      } ?: logError(completion, "fail to get file's uri")
    }
  }

  private fun getContentValues(mimeType: String, fileName: String) = ContentValues().apply {
    put(
      MediaStore.MediaColumns.DISPLAY_NAME,
      fileName
    )
    put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      put(MediaStore.MediaColumns.IS_PENDING, 1)
    }
  }

  private fun getFileUri(
    fileName: String,
    contentResolver: ContentResolver,
    values: ContentValues,
    mimeType: String,
  ) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
    contentResolver.insert(getSavedDirectory(mimeType), values)
  } else {
    File(getSavedDirectoryFile(mimeType), fileName).toUri()
  }

  private suspend fun writeFile(
    parcelFileDescriptor: ParcelFileDescriptor,
    responseBody: ResponseBody,
    fileUri: Uri,
    progress: suspend (Long, Long) -> Unit,
    completion: suspend (Uri?) -> Unit,
    scan: (() -> Unit)? = null,
  ) {
    try {
      val fileReader = ByteArray(4096)
      val fileSize = responseBody.contentLength()
      var fileSizeDownloaded: Long = 0
      var downloadPercent: Int
      val inputStream = responseBody.byteStream()

      val outputStream = FileOutputStream(parcelFileDescriptor.fileDescriptor)
      while (true) {
        val read: Int = inputStream.read(fileReader)
        fileSizeDownloaded += if (read.completeReadFile()) 0 else read.toLong()
        downloadPercent = if (read.completeReadFile()) 100 else (fileSizeDownloaded.toFloat() / fileSize.toFloat() * 100f).toInt()

        L.d("File Download: $fileSizeDownloaded of $fileSize || percent : $downloadPercent")

        CoroutineScope(Dispatchers.Main).launch {
          progress(fileSizeDownloaded, fileSize)
        }

        if (read.completeReadFile()) {
          break
        }

        outputStream.write(fileReader, 0, read)
      }
      outputStream.flush()
      outputStream.close()

      CoroutineScope(Dispatchers.Main).launch {
        completion(fileUri)
        scan?.invoke()
      }
    } catch (exception: Exception) {
      logError(completion, "fail to write file due to\n${exception.message}")
    }
  }

  private suspend fun writeBitmapFile(
    parcelFileDescriptor: ParcelFileDescriptor,
    bitmap: Bitmap,
    uri: Uri,
    completion: suspend (Uri?) -> Unit,
    scan: (() -> Unit)? = null
  ) {
    try {
      val outputStream = FileOutputStream(parcelFileDescriptor.fileDescriptor)
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
      outputStream.close()

      CoroutineScope(Dispatchers.Main).launch {
        completion(uri)
        scan?.invoke()
      }
    } catch (exception: Exception) {
      logError(completion, "fail to write file due to\n${exception.message}")
    }
  }

  private fun Int.completeReadFile() = this == -1

  @RequiresApi(Build.VERSION_CODES.Q)
  private fun getSavedDirectory(mimeType: String) =
    when {
      mimeType.contains("image") -> {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
      }
      mimeType.contains("audio") -> {
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
      }
      mimeType.contains("video") -> {
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
      }
      else -> {
        MediaStore.Downloads.EXTERNAL_CONTENT_URI
      }
    }

  private fun getSavedDirectoryFile(mimeType: String) =
    when {
      mimeType.contains("image") -> {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
      }
      mimeType.contains("audio") -> {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
      }
      mimeType.contains("video") -> {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
      }
      else -> {
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
      }
    }

  private fun scanDownloadFile(context: Context, fileUri: Uri, values: ContentValues, contentResolver: ContentResolver) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
      values.complete()
      contentResolver.update(fileUri, values, null, null)
    } else {
      sendBroadcastGallery(context, fileUri)
    }
  }

  private fun sendBroadcastGallery(context: Context, fileUri: Uri) {
    val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
    mediaScanIntent.data = fileUri
    context.sendBroadcast(mediaScanIntent)
  }

  @RequiresApi(Build.VERSION_CODES.Q)
  private fun ContentValues.complete() = apply {
    clear()
    put(MediaStore.MediaColumns.IS_PENDING, 0)
  }

  private suspend fun logError(completion: suspend (Uri?) -> Unit, msg: String) {
    completion(null)
    L.e(TAG, msg)
  }

  companion object {
    private const val TAG = "FileSaver"
  }
}