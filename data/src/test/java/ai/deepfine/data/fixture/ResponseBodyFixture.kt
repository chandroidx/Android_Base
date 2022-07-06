package ai.deepfine.data.fixture

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-22
 * @version 1.0.0
 */
val responseBodyFixture = "test".toResponseBody("text/plain".toMediaTypeOrNull())