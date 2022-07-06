package ai.deepfine.domain.repository

import ai.deepfine.data.datasource.DataStoreDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Description DataStoreRepository
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-27
 * @version 1.0.0
 */
interface DataStoreRepository {
  suspend fun setSample(sample: String?): Flow<Unit>
  suspend fun getSample(): Flow<String?>
}

class DataStoreRepositoryImpl @Inject constructor(
  private val dataSource: DataStoreDataSource,
) : DataStoreRepository {
  override suspend fun setSample(sample: String?): Flow<Unit> =
    dataSource.setSample(sample)

  override suspend fun getSample(): Flow<String?> =
    dataSource.getSample()
}