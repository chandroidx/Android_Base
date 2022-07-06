package ai.deepfine.androidBase.di

import ai.deepfine.data.api.FileDownloadAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * @Description ApiServiceModule
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-20
 * @version 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
class ApiServiceModule {
  @Singleton
  @Provides
  fun provideFileDownloadAPIService(retrofit: Retrofit) = retrofit.create(FileDownloadAPIService::class.java)
}