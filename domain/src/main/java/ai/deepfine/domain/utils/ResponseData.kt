package ai.deepfine.domain.utils

import ai.deepfine.data.utils.ResultDTO
import ai.deepfine.data.utils.ResultListDTO

sealed class ResponseData<out O> {
  open class IncludeCountNumberList<out O>(val response: List<O>?, val count: Int?) : ResponseData<O>()
  open class IncludeCountNumber<out O>(val response: O?, val count: Int?) : ResponseData<O>()

  companion object {
    fun <T, O> fromResultList(list: ResultListDTO<T>, map: (T) -> O) = IncludeCountNumberList(
      list.resultData?.map {
        map(it)
      },
      list.resultCnt
    )

    fun <T, O> fromResult(data: ResultDTO<T>, map: (T) -> O, empty: O? = null) = IncludeCountNumber(data.resultData?.let { map(it) } ?: empty!!, data.resultCnt?.toInt())
  }
}