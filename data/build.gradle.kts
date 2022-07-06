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
  projectImplementation(
    Modules.UTILITY
  )

  implementation(
    Libraries.Google.GSON
  )

  implementation(
    Libraries.Kotlin.Coroutine.Core,
    Libraries.Retrofit2,
    Libraries.Hilt
  )

  kapt(
    Libraries.Hilt.AndroidCompiler
  )

  configureTestImplements()
}