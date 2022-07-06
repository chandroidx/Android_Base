package ai.deepfine.data.utils

import ai.deepfine.utility.error.NetworkDisconnect
import ai.deepfine.utility.error.ServerError
import ai.deepfine.utility.network.NetworkInfo
import ai.deepfine.utility.utils.L
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object CatchFlow {
  inline fun <T> networkCatch(
    errorMessage: String? = null,
    function: () -> Flow<T>,
  ) = try {
    function.invoke()
  } catch (exception: Exception) {
    L.e(exception)
    throw ServerError(errorMessage ?: exception.message)
  }

  inline fun <T> networkDisconnectCatch(
    netWorkInfo: NetworkInfo,
    function: () -> Flow<T>,
  ): Flow<T> = try {
    when (netWorkInfo.isNetworkAvailable()) {
      true -> function.invoke()
      false -> flow {
        throw NetworkDisconnect()
      }
    }
  } catch (exception: Exception) {
    L.e(exception)
    flow { throw ServerError(exception.message) }
  }
}