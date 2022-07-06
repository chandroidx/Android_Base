package ai.deepfine.androidBase.di

import ai.deepfine.presentation.coroutine.BaseCoroutineScope
import ai.deepfine.presentation.coroutine.UICoroutineScope
import ai.deepfine.utility.network.NetworkInfo
import ai.deepfine.utility.network.NetworkInfoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Description AbstractNetworkModule
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractNetworkModule {
  @Binds
  abstract fun bindNetworkInfo(
    networkInfoImpl: NetworkInfoImpl,
  ): NetworkInfo

  @Binds
  abstract fun bindBaseCoroutineScope(
    uiCoroutineScope: UICoroutineScope,
  ): BaseCoroutineScope
}