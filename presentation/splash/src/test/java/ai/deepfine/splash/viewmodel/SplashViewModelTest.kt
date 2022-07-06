package ai.deepfine.splash.viewmodel

import ai.deepfine.domain.usecase.datastore.GetSample
import ai.deepfine.domain.usecase.datastore.SetSample
import ai.deepfine.presentation.coroutine.UICoroutineScope
import ai.deepfine.testutils.UnitTest
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-20
 * @version 1.0.0
 */

@ExperimentalCoroutinesApi
@ExperimentalTime
class SplashViewModelTest : UnitTest() {
  val setSample = mockk<SetSample>()
  val getSample = mockk<GetSample>()
  var viewModel: SplashViewModel = SplashViewModel(setSample, getSample, UICoroutineScope())

  @BeforeEach
  fun setUp() {
    coEvery {
      setSample.execute(any())
    } returns flow {
      emit(Unit)
    }

    coEvery {
      getSample.execute(any())
    } returns flow {
      emit("")
    }
  }

  @AfterEach
  fun tearDown() {
  }

  @Test
  fun `when entered splash, then get sample from data store`() = runOnTestDispatcher {
    viewModel.setSample("")

    advanceUntilIdle()

    viewModel.sample.getOrAwaitValue()

    assertEquals("", viewModel.sample.value)
  }

  @Test
  fun `when entered splash, then notice to LiveData`() = runOnTestDispatcher {
    viewModel.showLoginAfterMillis()

    advanceUntilIdle()

    viewModel.splashEvent.test {
      expectItem() `should equal` SplashViewModel.SplashState.Activate
    }
  }
}