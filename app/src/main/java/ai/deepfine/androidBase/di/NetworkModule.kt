package ai.deepfine.androidBase.di

import ai.deepfine.androidBase.BuildConfig
import ai.deepfine.utility.network.UnsafeOKHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @Description NetworkModule
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-14
 * @version 1.0.0
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
  @Singleton
  @Provides
  fun provideHttpClient(): OkHttpClient = UnsafeOKHttpClient.createUnsafeOkHttpClient(BuildConfig.DEBUG)

  @Singleton
  @Provides
  fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.API_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(httpClient)
    .build()
}