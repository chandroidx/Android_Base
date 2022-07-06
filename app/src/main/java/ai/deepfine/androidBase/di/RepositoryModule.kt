package ai.deepfine.androidBase.di

import ai.deepfine.domain.repository.DataStoreRepository
import ai.deepfine.domain.repository.DataStoreRepositoryImpl
import ai.deepfine.domain.repository.FileDownloadRepository
import ai.deepfine.domain.repository.FileDownloadRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Description RepositoryModule
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
  @Binds
  abstract fun bindFileDownloadRepository(
    fileDownloadRepositoryImpl: FileDownloadRepositoryImpl,
  ): FileDownloadRepository

  @Binds
  abstract fun bindDataStoreRepository(
    dataStoreRepositoryImpl: DataStoreRepositoryImpl,
  ): DataStoreRepository
}