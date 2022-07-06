package ai.deepfine.domain.usecase.datastore

import ai.deepfine.domain.repository.DataStoreRepository
import ai.deepfine.domain.utils.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @Description GetSample
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-27
 * @version 1.0.0
 */
class GetSample @Inject constructor(private val repository: DataStoreRepository) : UseCase<String?, UseCase.NoneParam>() {
  override suspend fun execute(params: NoneParam): Flow<String?> = repository.getSample()
}