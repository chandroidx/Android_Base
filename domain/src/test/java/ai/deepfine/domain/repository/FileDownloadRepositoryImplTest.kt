package ai.deepfine.domain.repository

import ai.deepfine.data.datasource.FileDownloadLocalDataSource
import ai.deepfine.data.datasource.FileDownloadRemoteDataSource
import ai.deepfine.data.model.download.FileDownloadStateModel
import ai.deepfine.domain.entity.FileDownloadState
import ai.deepfine.testutils.UnitTest
import ai.deepfine.utility.error.NetworkDisconnect
import ai.deepfine.utility.network.NetworkInfo
import android.net.Uri
import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.*
import kotlin.time.ExperimentalTime

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-21
 * @version 1.0.0
 */
@ExperimentalTime
@ExperimentalCoroutinesApi
@FlowPreview
class FileDownloadRepositoryImplTest : UnitTest() {
  val networkInfo = mockk<NetworkInfo>()
  val serviceDataSource = mockk<FileDownloadRemoteDataSource>()
  val localDataSource = mockk<FileDownloadLocalDataSource>()
  val mockUri = mockk<Uri>()
  val mockResponseBody = mockk<ResponseBody>()

  lateinit var fileDownloadRepository: FileDownloadRepository

  @BeforeEach
  fun setUp() {
    fileDownloadRepository = FileDownloadRepositoryImpl(networkInfo, serviceDataSource, localDataSource)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Nested
  @DisplayName("FileDownloadRepository's Success Case")
  inner class SuccessCase {
    private val successModel = FileDownloadStateModel.Success(mockUri, "")

    @BeforeEach
    fun arrange() {
      every {
        networkInfo.isNetworkAvailable()
      } returns true

      coEvery {
        serviceDataSource.downloadFile(any())
      } returns flow { emit(mockResponseBody) }

      coEvery {
        localDataSource.responseBodyToFile(any(), any(), any())
      } returns flow { emit(successModel) }

      coEvery {
        localDataSource.responseBodyToMediaFile(any(), any(), any())
      } returns flow { emit(successModel) }
    }

    @Test
    @DisplayName("convert state model to entity successful with file")
    fun responseBodyToFileSuccess() = runOnTestDispatcher {
      fileDownloadRepository.downloadFile("", "", mockUri).test {
        expectItem() `should equal` FileDownloadState.fromModel(successModel)
        expectComplete()
      }
    }

    @Test
    @DisplayName("convert state model to entity successful with media file")
    fun responseBodyToMediaFileSuccess() = runOnTestDispatcher {
      fileDownloadRepository.downloadMediaFile("", "", "").test {
        expectItem() `should equal` FileDownloadState.fromModel(successModel)
        expectComplete()
      }
    }
  }

  @Nested
  @DisplayName("FileDownloadRepository's Error Case")
  inner class FailureCase {
    @BeforeEach
    fun arrange() {
      every {
        networkInfo.isNetworkAvailable()
      } returns true
    }

    @Test
    @DisplayName("flow must throw error thrown by serviceDataSource")
    fun `serviceDataSource throws error`() = runOnTestDispatcher {
      coEvery {
        serviceDataSource.downloadFile(any())
      } throws Exception()

      fileDownloadRepository.downloadFile("", "", mockUri).test {
        expectError()
      }
    }

    @Test
    @DisplayName("flow must throw error thrown by localDataSource")
    fun `localDataSource throws error`() = runOnTestDispatcher {
      coEvery {
        serviceDataSource.downloadFile(any())
      } returns flow { emit(mockResponseBody) }

      coEvery {
        localDataSource.responseBodyToFile(any(), any(), any())
      } throws Exception()

      fileDownloadRepository.downloadFile("", "", mockUri).test {
        expectError()
      }

      fileDownloadRepository.downloadMediaFile("", "", "").test {
        expectError()
      }
    }
  }

  @Nested
  @DisplayName("FileDownloadRepository's Network Error Case")
  inner class NetworkErrorCase {
    @BeforeEach
    fun arrange() {
      every {
        networkInfo.isNetworkAvailable()
      } returns false
    }

    @Test
    @DisplayName("must throw NetworkDisconnect when network unavailable")
    fun networkDisconnectError() = runOnTestDispatcher {
      fileDownloadRepository.downloadFile("", "", mockUri).test {
        expectError() `should be instance of` NetworkDisconnect::class.java
      }

      fileDownloadRepository.downloadMediaFile("", "", "").test {
        expectError() `should be instance of` NetworkDisconnect::class.java
      }
    }
  }
}