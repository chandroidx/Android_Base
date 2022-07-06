package ai.deepfine.data.datasource

import android.net.Uri
import ai.deepfine.data.model.download.FileDownloadStateModel
import ai.deepfine.utility.error.FileSaveError
import ai.deepfine.utility.utils.FileSaver
import android.graphics.Bitmap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.ResponseBody
import java.lang.Exception
import javax.inject.Inject

/**
 * @Description FileDownloadLocalDataSource
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
interface FileDownloadLocalDataSource {
  fun responseBodyToFile(responseBody: ResponseBody, mimeType: String, targetUri: Uri): Flow<FileDownloadStateModel>
  fun responseBodyToMediaFile(responseBody: ResponseBody, fileName: String, mimeType: String): Flow<FileDownloadStateModel>
  fun bitmapToFile(bitmap: Bitmap, fileName: String): Flow<FileDownloadStateModel>
}

@ExperimentalCoroutinesApi
class FileDownloadLocalDataSourceImpl @Inject constructor(
  private val fileSaver: FileSaver,
) : FileDownloadLocalDataSource {
  override fun responseBodyToFile(responseBody: ResponseBody, mimeType: String, targetUri: Uri): Flow<FileDownloadStateModel> = callbackFlow {
    try {
      fileSaver.saveFile(
        responseBody, targetUri,
        progress = onFileDownloadProgress(),
        completion = onFileDownloadComplete()
      )
    } catch (e: Exception) {
      close()
      throw FileSaveError(e.message)
    }

    awaitClose()
  }

  override fun responseBodyToMediaFile(responseBody: ResponseBody, fileName: String, mimeType: String): Flow<FileDownloadStateModel> = callbackFlow {
    try {
      fileSaver.saveMediaFile(
        responseBody, mimeType, fileName,
        progress = onFileDownloadProgress(),
        completion = onFileDownloadComplete()
      )
    } catch (e: Exception) {
      close()
      throw FileSaveError(e.message)
    }

    awaitClose()
  }

  override fun bitmapToFile(bitmap: Bitmap, fileName: String) = callbackFlow {
    try {
      fileSaver.saveBitmap(
        bitmap, fileName,
        completion = onFileDownloadComplete()
      )
    } catch (e: Exception) {
      close()
      throw FileSaveError(e.message)
    }

    awaitClose()
  }

  private fun ProducerScope<FileDownloadStateModel>.onFileDownloadComplete(): suspend (Uri?) -> Unit = { uri ->
    when (uri == null) {
      true -> send(FileDownloadStateModel.Failure)
      false -> send(FileDownloadStateModel.Success(uri))
    }

    close()
  }

  private fun ProducerScope<FileDownloadStateModel>.onFileDownloadProgress(): suspend (Long, Long) -> Unit = { downloadedSize, fileSize ->
    send(FileDownloadStateModel.Progress(downloadedSize.toInt(), fileSize.toInt()))
  }
}