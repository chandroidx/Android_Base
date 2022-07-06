plugins {
  androidLibrary()
  kotlinAndroid()
  kotlinKapt()
  hilt()
  jUnit5()
}

android {
  setLibraryConfig()
  setProductFlavors(project::property)
}

dependencies {
  implementation(
    Libraries.AndroidX.CORE_KTX,
    Libraries.AndroidX.APPCOMPAT,
    Libraries.Google.GSON,
  )

  implementation(
    Libraries.OkHttp3,
    Libraries.OkHttp3.LoggingInterceptor,
    Libraries.Kotlin.StdLib,
    Libraries.Kotlin.Coroutine.Core,
    Libraries.Retrofit2,
    Libraries.Retrofit2.ConverterGson,
    Libraries.Retrofit2.ConverterScalars,
    Libraries.Glide,
    Libraries.Glide.Transformations,
    Libraries.Glide.Integration,
    Libraries.Hilt,
    Libraries.DataStore,
    Libraries.DataStore.Core,
    Libraries.DataStore.Preference,
    Libraries.DataStore.PreferenceCore
  )

  kapt(
    Libraries.AndroidX.ANNOTATION
  )

  kapt(
    Libraries.Glide.Compiler,
    Libraries.Hilt.AndroidCompiler
  )

  configureTestImplements()
}