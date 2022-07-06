package ai.deepfine.domain.utils

import kotlinx.coroutines.flow.Flow

/**
 * @Description UseCase
 * @author yc.park (DEEP.FINE)
 * @since 2021-12-13
 * @version 1.0.0
 */
abstract class UseCase<out Type, in Params> where Type : Any? {
  abstract suspend fun execute(params: Params): Flow<Type>

  object NoneParam
}