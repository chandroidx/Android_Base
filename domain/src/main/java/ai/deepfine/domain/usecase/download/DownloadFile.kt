package ai.deepfine.domain.usecase.download

import ai.deepfine.domain.entity.FileDownloadState
import ai.deepfine.domain.repository.FileDownloadRepository
import ai.deepfine.domain.utils.UseCase
import android.net.Uri
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Description DownloadFile
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-27
 * @version 1.0.0
 */
class DownloadFile @Inject constructor(private val repository: FileDownloadRepository) : UseCase<FileDownloadState, DownloadFile.Params>() {
  override suspend fun execute(params: Params): Flow<FileDownloadState> =
    when (params) {
      is Params.File -> repository.downloadFile(params.url, params.mimeType, params.targetUri)
      is Params.Media -> repository.downloadMediaFile(params.url, params.fileName, params.mimeType)
    }

  sealed class Params {
    data class File(val url: String, val mimeType: String, val targetUri: Uri) : Params()
    data class Media(val url: String, val mimeType: String, val fileName: String) : Params()
  }
}