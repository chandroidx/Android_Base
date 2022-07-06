package ai.deepfine.androidBase.di

import ai.deepfine.domain.repository.DataStoreRepository
import ai.deepfine.domain.repository.FileDownloadRepository
import ai.deepfine.domain.usecase.datastore.GetSample
import ai.deepfine.domain.usecase.datastore.SetSample
import ai.deepfine.domain.usecase.download.DownloadFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Description UseCaseModule
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
  @Provides
  @Singleton
  fun provideDownloadFileUseCase(repository: FileDownloadRepository) = DownloadFile(repository)

  @Provides
  @Singleton
  fun provideSetSampleUseCase(repository: DataStoreRepository) = SetSample(repository)

  @Provides
  @Singleton
  fun provideGetSampleUseCase(repository: DataStoreRepository) = GetSample(repository)
}