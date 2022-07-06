package ai.deepfine.data.datasource

import ai.deepfine.data.api.FileDownloadAPIService
import ai.deepfine.data.utils.CatchFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject

/**
 * @Description FileDownloadRemoteDataSource
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
interface FileDownloadRemoteDataSource {
  fun downloadFile(url: String): Flow<ResponseBody>
}

class FileDownloadRemoteDataSourceImpl @Inject constructor(
  private val service: FileDownloadAPIService,
) : FileDownloadRemoteDataSource {

  override fun downloadFile(url: String): Flow<ResponseBody> =
    CatchFlow.networkCatch {
      flow {
        emit(service.downloadFile(url))
      }
    }
}