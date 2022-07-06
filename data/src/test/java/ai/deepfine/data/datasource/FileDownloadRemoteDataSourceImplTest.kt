package ai.deepfine.data.datasource

import ai.deepfine.data.api.FileDownloadAPIService
import ai.deepfine.data.fixture.responseBodyFixture
import ai.deepfine.testutils.UnitTest
import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.amshove.kluent.*
import org.junit.jupiter.api.*

import kotlin.time.ExperimentalTime

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-22
 * @version 1.0.0
 */
@ExperimentalCoroutinesApi
internal class FileDownloadRemoteDataSourceImplTest : UnitTest() {
  val service = mockk<FileDownloadAPIService>()
  lateinit var dataSource: FileDownloadRemoteDataSource

  @BeforeEach
  fun setUp() {
    dataSource = FileDownloadRemoteDataSourceImpl(service)
  }

  @AfterEach
  fun tearDown() {
    clearAllMocks()
  }

  val responseBody = responseBodyFixture

  @Nested
  @DisplayName("success request download file to get responsebody ")
  inner class RequestSuccess {
    @BeforeEach
    fun arrange() {
      coEvery {
        service.downloadFile(any())
      } returns responseBodyFixture
    }

    @ExperimentalTime
    @Test
    @DisplayName("should return non-null ResponseBody when request success")
    fun getResponseBodySuccess() = runOnTestDispatcher {
      // given

      // when
      val result = dataSource.downloadFile("")

      // then
      result.test {
        with(expectItem()) {
          shouldNotBeNull()
          shouldEqual(responseBody)
        }

        expectComplete()
      }
    }
  }
}