package ai.deepfine.data.datasource

import ai.deepfine.utility.utils.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * @Description DataStoreDataSource
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
interface DataStoreDataSource {
  suspend fun setSample(sample: String?): Flow<Unit>
  suspend fun getSample(): Flow<String?>
}

class DataStoreDataSourceImpl @Inject constructor(
  private val dataStore: DataStore,
) : DataStoreDataSource {
  override suspend fun setSample(sample: String?) = flow {
    dataStore.setSample(sample)
    emit(Unit)
  }

  override suspend fun getSample(): Flow<String?> =
    dataStore.getSample()
}