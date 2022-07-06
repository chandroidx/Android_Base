package ai.deepfine.domain.entity

import ai.deepfine.data.model.download.FileDownloadStateModel
import android.net.Uri

/**
 * @Description FileDownloadState
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-06
 * @version 1.0.0
 */
sealed interface FileDownloadState {
  data class Progress(val downloadedSize: Int, val fileSize: Int) : FileDownloadState {
    companion object {
      fun fromModel(model: FileDownloadStateModel.Progress) = Progress(model.downloadedSize, model.fileSize)
    }
  }

  data class Success(val uri: Uri) : FileDownloadState {
    companion object {
      fun fromModel(model: FileDownloadStateModel.Success) = Success(model.uri)
    }
  }

  object Failure : FileDownloadState

  companion object {
    fun fromModel(model: FileDownloadStateModel) = when (model) {
      is FileDownloadStateModel.Success -> Success.fromModel(model)
      FileDownloadStateModel.Failure -> Failure
      is FileDownloadStateModel.Progress -> Progress.fromModel(model)
    }
  }
}