plugins {
  androidLibrary()
  kotlinAndroid()
  kotlinKapt()
  jUnit5()
}

android {
  setLibraryConfig()
}

dependencies {
  configureTestImplements()

  implementation(
    Libraries.Test.Robolectric,
    Libraries.Test.Jupiter.Api,
    Libraries.Test.Jupiter.Params,
    Libraries.Kotlin.Coroutine.Test,
    Libraries.Kotlin.TestJUnit,
    Libraries.Test.CoreTesting,
    Libraries.Test.Mockk,
    Libraries.Test.Kluent,
    Libraries.Test.Turbine,
    Libraries.Lifecycle.LiveData,
  )

  testRuntimeOnly(
    Libraries.Test.Jupiter.Engine,
  )

  androidTestRuntimeOnly(
    Libraries.Test.JUnit5.AndroidTestRunner
  )
}