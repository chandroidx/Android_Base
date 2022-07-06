plugins {
  androidLibrary()
  kotlinAndroid()
  kotlinKapt()
  hilt()
  jUnit5()
}

android {
  setLibraryConfig()
  setDataBindingEnabled()
  setProductFlavors(project::property)
}

dependencies {
  projectImplementation(
    Modules.UTILITY,
    Modules.DOMAIN
  )

  implementation(
    Libraries.AndroidX.CORE_KTX,
    Libraries.AndroidX.APPCOMPAT,
    Libraries.Google.MATERIAL,
    Libraries.AndroidX.ACTIVITY,
    Libraries.AndroidX.FRAGMENT,
  )

  implementation(
    Libraries.Lifecycle.Runtime,
    Libraries.Lifecycle.ViewModel,
    Libraries.Retrofit2,
    Libraries.Retrofit2.ConverterGson,
    Libraries.Retrofit2.ConverterScalars,
    Libraries.OkHttp3.LoggingInterceptor,
    Libraries.Glide,
    Libraries.Glide.Transformations,
    Libraries.Glide.Integration,
    Libraries.TedPermission,
    Libraries.Hilt,
    Libraries.Navigation.Fragment,
    Libraries.Navigation.Runtime,
    Libraries.Navigation.UI
  )

  kapt(
    Libraries.AndroidX.ANNOTATION
  )

  kapt(
    Libraries.Glide.Compiler,
    Libraries.Hilt.AndroidCompiler,
  )

  configureTestImplements()
}