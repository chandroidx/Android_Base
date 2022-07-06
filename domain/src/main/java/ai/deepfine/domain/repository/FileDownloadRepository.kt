package ai.deepfine.domain.repository

import ai.deepfine.data.datasource.FileDownloadLocalDataSource
import ai.deepfine.data.datasource.FileDownloadRemoteDataSource
import ai.deepfine.data.utils.CatchFlow
import ai.deepfine.domain.entity.FileDownloadState
import ai.deepfine.utility.network.NetworkInfo
import android.net.Uri
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * @Description FileDownloadRepository
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-27
 * @version 1.0.0
 */
@ExperimentalCoroutinesApi
interface FileDownloadRepository {
  fun downloadFile(url: String, mimeType: String, targetUri: Uri): Flow<FileDownloadState>
  fun downloadMediaFile(url: String, fileName: String, mimeType: String): Flow<FileDownloadState>
}

@ExperimentalCoroutinesApi
@FlowPreview
class FileDownloadRepositoryImpl @Inject constructor(
  private val networkInfo: NetworkInfo,
  private val remoteDataSource: FileDownloadRemoteDataSource,
  private val localDataSource: FileDownloadLocalDataSource,
) : FileDownloadRepository {
  override fun downloadFile(url: String, mimeType: String, targetUri: Uri): Flow<FileDownloadState> =
    CatchFlow.networkDisconnectCatch(networkInfo) {
      remoteDataSource.downloadFile(url)
    }.flatMapConcat { responseBody ->
      localDataSource.responseBodyToFile(responseBody, mimeType, targetUri)
    }.map { model ->
      FileDownloadState.fromModel(model)
    }

  override fun downloadMediaFile(url: String, fileName: String, mimeType: String): Flow<FileDownloadState> =
    CatchFlow.networkDisconnectCatch(networkInfo) {
      remoteDataSource.downloadFile(url)
    }.flatMapConcat { responseBody ->
      localDataSource.responseBodyToMediaFile(responseBody, fileName, mimeType)
    }.map { model ->
      FileDownloadState.fromModel(model)
    }
}