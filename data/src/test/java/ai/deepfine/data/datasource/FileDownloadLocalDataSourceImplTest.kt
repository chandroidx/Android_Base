package ai.deepfine.data.datasource

import ai.deepfine.data.fixture.responseBodyFixture
import ai.deepfine.data.model.download.FileDownloadStateModel
import ai.deepfine.testutils.UnitTest
import ai.deepfine.utility.utils.FileSaverImpl
import android.net.Uri
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-22
 * @version 1.0.0
 */
@ExperimentalCoroutinesApi
@ExperimentalTime
class FileDownloadLocalDataSourceImplTest : UnitTest() {
  private val fileSaver = mockk<FileSaverImpl>()
  val onProgress = slot<suspend (Long, Long) -> Unit>()
  val onComplete = slot<suspend (Uri?) -> Unit>()

  lateinit var fileDownloadLocalDataSource: FileDownloadLocalDataSourceImpl

  private val testUri = mockk<Uri>()

  var lastCollectedItem: FileDownloadStateModel? = null

  @BeforeEach
  fun setUp() {
    fileDownloadLocalDataSource = FileDownloadLocalDataSourceImpl(fileSaver)

    coEvery {
      fileSaver.saveFile(any(), any(), capture(onProgress), capture(onComplete))
    } just runs
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  @Nested
  inner class SuccessCreateFile {
    @BeforeEach
    fun arrange() {
    }

    @Test
    fun onParseResponseBodyToFile() = runOnTestDispatcher {
      val flow = fileDownloadLocalDataSource.responseBodyToFile(responseBodyFixture, "", testUri).onEach {
        lastCollectedItem = it
      }

      val collectJob = launch { flow.collect() }
      advanceUntilIdle() // wait for callbackFlow builder to call addListener

      assertProgress(0, 100)
      assertProgress(100, 100)
      assertResultSuccess("", testUri)

      collectJob.cancel()

      advanceUntilIdle() // wait for awaitClose
    }

    private suspend fun TestScope.assertProgress(downloadedSize: Long, fileSize: Long) {
      onProgress.captured.invoke(downloadedSize, fileSize)
      advanceUntilIdle()

      assertEquals(FileDownloadStateModel.Progress(downloadedSize.toInt(), fileSize.toInt()), lastCollectedItem)
    }

    private suspend fun TestScope.assertResultSuccess(mimeType: String, uri: Uri) {
      onComplete.captured.invoke(testUri)
      advanceUntilIdle()
      assertEquals(FileDownloadStateModel.Success(uri, mimeType), lastCollectedItem)
    }
  }

  @Nested
  inner class FailureCreateFile {
    @BeforeEach
    fun arrange() {
    }

    @Test
    fun onThrowErrorWhenParseFile() = runOnTestDispatcher {
      val flow = fileDownloadLocalDataSource.responseBodyToFile(responseBodyFixture, "", testUri).onEach {
        lastCollectedItem = it
      }

      val collectJob = launch { flow.collect() }
      advanceUntilIdle() // wait for callbackFlow builder to call addListener

      assertResultFailure()
      collectJob.cancel()

      advanceUntilIdle() // wait for awaitClose
    }

    private suspend fun TestScope.assertResultFailure() {
      onComplete.captured.invoke(null)
      advanceUntilIdle()
      assertEquals(FileDownloadStateModel.Failure, lastCollectedItem)
    }
  }
}