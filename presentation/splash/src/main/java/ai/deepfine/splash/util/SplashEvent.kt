package ai.deepfine.splash.util

sealed interface SplashEvent {
  object Login : SplashEvent
  data class DataStoreSample(val data: String?) : SplashEvent

  interface Observer {
    fun observeEvent(event: SplashEvent) {
      when (event) {
        is DataStoreSample -> observeDataStoreSample(event)
        Login -> observeLogin()
      }
    }

    fun observeDataStoreSample(sample: DataStoreSample)
    fun observeLogin()
  }
}