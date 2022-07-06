package ai.deepfine.androidBase.di

import ai.deepfine.data.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * @Description DataSourceModule
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
  @Binds
  abstract fun bindFileDownloadServiceDataSource(
    fileDownloadServiceDataSourceImpl: FileDownloadRemoteDataSourceImpl,
  ): FileDownloadRemoteDataSource

  @Binds
  abstract fun bindFileDownloadLocalDataSource(
    fileDownloadLocalDataSourceImpl: FileDownloadLocalDataSourceImpl,
  ): FileDownloadLocalDataSource

  @Binds
  abstract fun bindDataStoreDataSource(
    dataStoreDataSourceImpl: DataStoreDataSourceImpl,
  ): DataStoreDataSource
}