package ai.deepfine.utility.extensions

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.*
import java.io.IOException

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-27
 * @version 1.0.0
 */

internal suspend inline fun <T : Any> DataStore<Preferences>.read(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
  data.catch { recoverOrThrow(it) }.map { it[key] ?: defaultValue }

internal suspend inline fun <T : Any> DataStore<Preferences>.read(key: Preferences.Key<T>): Flow<T?> =
  data.catch { recoverOrThrow(it) }.map { it[key] }

internal suspend fun FlowCollector<Preferences>.recoverOrThrow(throwable: Throwable) {
  if (throwable is IOException) {
    emit(emptyPreferences())
  } else {
    throw throwable
  }
}

internal suspend inline fun <T : Any> DataStore<Preferences>.store(key: Preferences.Key<T>, value: T?) = edit { preferences ->
  if (value == null) {
    preferences.remove(key)
  } else {
    preferences[key] = value
  }
}