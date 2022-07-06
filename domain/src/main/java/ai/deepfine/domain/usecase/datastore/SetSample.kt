package ai.deepfine.domain.usecase.datastore

import ai.deepfine.domain.repository.DataStoreRepository
import ai.deepfine.domain.utils.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Description SetSample
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-27
 * @version 1.0.0
 */
class SetSample @Inject constructor(private val repository: DataStoreRepository) : UseCase<Unit, SetSample.Params>() {
  override suspend fun execute(params: Params): Flow<Unit> =
    repository.setSample(params.sample)

  data class Params(val sample: String?)
}