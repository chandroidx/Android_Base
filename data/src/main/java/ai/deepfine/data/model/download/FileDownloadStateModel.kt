package ai.deepfine.data.model.download

import android.net.Uri

/**
 * @Description FileDownloadStateModel
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-07
 * @version 1.0.0
 */
sealed interface FileDownloadStateModel {
  data class Progress(val downloadedSize: Int, val fileSize: Int) : FileDownloadStateModel
  data class Success(val uri: Uri) : FileDownloadStateModel
  object Failure : FileDownloadStateModel
}