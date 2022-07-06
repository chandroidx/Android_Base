package ai.deepfine.androidBase.di

import ai.deepfine.utility.utils.DataStore
import ai.deepfine.utility.utils.DataStoreImpl
import ai.deepfine.utility.utils.FileSaver
import ai.deepfine.utility.utils.FileSaverImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Description DataModule
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-27
 * @version 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
  @Binds
  abstract fun bindDataStore(
    dataStoreImpl: DataStoreImpl,
  ): DataStore

  @Binds
  abstract fun bindFileSaver(
    fileSaverImpl: FileSaverImpl,
  ): FileSaver
}