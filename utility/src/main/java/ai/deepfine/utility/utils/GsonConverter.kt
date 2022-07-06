package ai.deepfine.utility.utils

import com.google.gson.GsonBuilder
import java.lang.reflect.Type

object GsonConverter {
  private const val GSON_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"

  private val gson = GsonBuilder().serializeNulls().setDateFormat(GSON_TIME_FORMAT).create()

  fun toJson(anyObject: Any): String? = try {
    gson.toJson(anyObject)
  } catch (exception: Exception) {
    null
  }

  fun <T> fromJson(jsonString: String, type: Type): T? = try {
    gson.fromJson(jsonString, type)
  } catch (exception: Exception) {
    null
  }

  fun <T> fromJson(jsonString: String, classType: Class<T>): T? = try {
    gson.fromJson(jsonString, classType)
  } catch (exception: Exception) {
    null
  }
}