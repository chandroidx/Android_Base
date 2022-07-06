package ai.deepfine.domain.usecase

import ai.deepfine.domain.entity.FileDownloadState
import ai.deepfine.domain.repository.FileDownloadRepository
import ai.deepfine.domain.usecase.download.DownloadFile
import ai.deepfine.testutils.UnitTest
import android.net.Uri
import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.*
import kotlin.Exception
import kotlin.time.ExperimentalTime

/**
 * @author yc.park (DEEP.FINE)
 * @version 1.0.0
 * @Description Class
 * @since 2022-04-25
 */
@ExperimentalCoroutinesApi
@ExperimentalTime
class DownloadFileTest : UnitTest() {
  private val fileDownloadRepository = mockk<FileDownloadRepository>()
  private val mockUri = mockk<Uri>()

  private val fileDownloadState = listOf(
    FileDownloadState.Progress(0, 100),
    FileDownloadState.Progress(100, 100),
    FileDownloadState.Success(mockUri, ""),
  )

  private val downloadFile = DownloadFile(fileDownloadRepository)

  @BeforeEach
  fun setUp() {
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Nested
  inner class SuccessCase {
    @BeforeEach
    fun arrange() {
      coEvery {
        fileDownloadRepository.downloadFile(any(), any(), any())
      } returns flow {
        fileDownloadState.forEach {
          emit(it)
        }
      }

      coEvery {
        fileDownloadRepository.downloadMediaFile(any(), any(), any())
      } returns flow {
        fileDownloadState.forEach {
          emit(it)
        }
      }
    }

    @Test
    fun downloadFileSuccess() = runOnTestDispatcher {
      downloadFile.execute(DownloadFile.Params.File("", "", mockUri)).test {
        fileDownloadState.forEach {
          expectItem() `should equal` it
        }

        expectComplete()
      }
    }

    @Test
    fun downloadMediaFileSuccess() = runOnTestDispatcher {
      downloadFile.execute(DownloadFile.Params.Media("", "", "")).test {
        fileDownloadState.forEach {
          expectItem() `should equal` it
        }

        expectComplete()
      }
    }
  }

  @Nested
  inner class FailureCase {
    @BeforeEach
    fun arrange() {
      coEvery {
        fileDownloadRepository.downloadFile(any(), any(), any())
      } returns flow {
        throw Exception()
      }

      coEvery {
        fileDownloadRepository.downloadMediaFile(any(), any(), any())
      } returns flow {
        throw Exception()
      }
    }

    @Test
    fun downloadFileSuccess() = runOnTestDispatcher {
      downloadFile.execute(DownloadFile.Params.File("", "", mockUri)).test {
        expectError()
      }
    }

    @Test
    fun downloadMediaFileSuccess() = runOnTestDispatcher {
      downloadFile.execute(DownloadFile.Params.Media("", "", "")).test {
        expectError()
      }
    }
  }
}