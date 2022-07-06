package ai.deepfine.data.utils

import ai.deepfine.utility.error.ServerError
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * @Description Class
 * @author jh.kim (DEEP.FINE)
 * @since 2/2/21
 * @version 1.0.0
 */

data class ResultListDTO<T>(
  @SerializedName("status_code")
  val statusCode: String?,

  @SerializedName("status_message")
  val statusMessage: String?,

  @SerializedName("error_code")
  val errorCode: String?,

  @SerializedName("error_message")
  val errorMessage: String?,

  @SerializedName("result_cnt")
  val resultCnt: Int?,

  @SerializedName("result_yn")
  val resultYn: String?,

  @SerializedName("result_data")
  val resultData: List<T>?
) {
  fun responseToFlow(validationFunction: () -> Boolean = ::validatesAll): Flow<ResultListDTO<T>> =
    flow {
      if (validationFunction()) {
        emit(this@ResultListDTO)
      } else {
        throw ServerError(errorMessage)
      }
    }

  fun responseToFlowWithResultData(validationFunction: () -> Boolean = ::validatesAll): Flow<List<T>> = flow {
    if (validationFunction()) {
      emit(resultData!!)
    } else {
      throw ServerError(errorMessage)
    }
  }

  fun responseToFlowWithNullableResultData(): Flow<List<T>?> = flow {
    if (validatesWithoutNullCheck()) {
      emit(resultData)
    } else {
      throw ServerError(errorMessage)
    }
  }

  fun responseToCompletableFlow(validationFunction: () -> Boolean = ::validatesAll): Flow<Boolean> =
    flow {
      if (validationFunction()) {
        emit(true)
      } else {
        throw ServerError(errorMessage)
      }
    }

  private fun validatesWithoutNullCheck() = statusCode == "200"
  private fun validatesAll() = statusCode == "200" && null != resultData
}

data class ResultDTO<T>(
  @SerializedName("status_code")
  val statusCode: String?,

  @SerializedName("status_message")
  val statusMessage: String?,

  @SerializedName("error_code")
  val errorCode: String?,

  @SerializedName("error_message")
  val errorMessage: String?,

  @SerializedName("result_cnt")
  val resultCnt: String?,

  @SerializedName("result_yn")
  val resultYn: String?,

  @SerializedName("result_data")
  val resultData: T?
) {
  fun responseToOwnerFlow(validationFunction: () -> Boolean = ::validatesAll): Flow<ResultDTO<T>> = flow {
    if (validationFunction()) {
      emit(this@ResultDTO)
    } else {
      throw ServerError(errorMessage)
    }
  }

  fun responseToOwnerFlowWithOutNullCheck() = responseToOwnerFlow(::validatesWithoutNullCheck)

  fun responseToFlow(validationFunction: () -> Boolean = ::validatesAll): Flow<T> = flow {
    if (validationFunction()) {
      emit(resultData!!)
    } else {
      throw ServerError(errorMessage)
    }
  }

  fun responseToUnitFlow(): Flow<Unit> = flow {
    if (statusCode == "200") {
      emit(Unit)
    } else {
      throw ServerError(errorMessage)
    }
  }

  fun responseToNullableFlow(validationFunction: () -> Boolean = ::validatesWithoutNullCheck): Flow<T?> = flow {
    if (validationFunction()) {
      emit(resultData)
    } else {
      throw ServerError(errorMessage)
    }
  }

  fun responseToCompletableFlow(validationFunction: () -> Boolean = ::validatesAll): Flow<Boolean> =
    flow {
      if (validationFunction()) {
        emit(true)
      } else {
        throw ServerError(errorMessage)
      }
    }

  fun responseToResultYnFlow(validationFunction: () -> Boolean = ::validatesWithoutNullCheck): Flow<String?> = flow {
    if (validationFunction()) {
      emit(resultYn)
    } else {
      throw ServerError(errorMessage)
    }
  }

  private fun validatesWithoutNullCheck() = statusCode == "200"
  private fun validatesAll() = statusCode == "200" && null != resultData
}