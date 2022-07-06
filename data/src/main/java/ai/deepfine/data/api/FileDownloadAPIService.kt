package ai.deepfine.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @Description FileDownloadAPIService
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
interface FileDownloadAPIService {
  @GET
  suspend fun downloadFile(@Url url: String): ResponseBody
}