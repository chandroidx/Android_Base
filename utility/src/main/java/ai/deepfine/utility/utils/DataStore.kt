package ai.deepfine.utility.utils

import ai.deepfine.utility.extensions.read
import ai.deepfine.utility.extensions.store
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-27
 * @version 1.0.0
 */
interface DataStore {
  suspend fun setSample(loginKey: String?)
  suspend fun getSample(): Flow<String?>
}

class DataStoreImpl @Inject constructor(@ApplicationContext context: Context) : ai.deepfine.utility.utils.DataStore {
  companion object {
    // File Name
    private const val PREFERENCE_FILE_NAME = "android_base_pref"

    // Key
    private val PREF_SAMPLE = stringPreferencesKey("sample")

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_FILE_NAME)
  }

  private val dataStore: DataStore<Preferences> = context.dataStore

  override suspend fun setSample(loginKey: String?) {
    dataStore.store(PREF_SAMPLE, loginKey)
  }

  override suspend fun getSample(): Flow<String?> {
    return dataStore.read(PREF_SAMPLE)
  }
}