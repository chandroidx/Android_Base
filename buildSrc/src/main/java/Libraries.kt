object Libraries {
  object AndroidX {
    const val MULTIDEX = "androidx.multidex:multidex:2.0.1"
    const val CORE_KTX = "androidx.core:core-ktx:1.7.0"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.4.1"
    const val ANNOTATION = "androidx.annotation:annotation:1.3.0"
    const val ACTIVITY = "androidx.activity:activity-ktx:1.4.0"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:1.4.1"
  }

  object Google {
    const val MATERIAL = "com.google.android.material:material:1.5.0"
    const val GSON = "com.google.code.gson:gson:2.8.6"
  }

  object Kotlin : LibraryGroup(groupName = "org.jetbrains.kotlin", version = "1.6.0") {
    object StdLib : LibraryGroupChild(group = Kotlin, name = "kotlin-stdlib"), Implementable
    object TestJUnit : LibraryGroupChild(group = Kotlin, name = "kotlin-test-junit"), TestImplementable, Implementable

    object Coroutine : LibraryGroup(groupName = "org.jetbrains.kotlinx", version = "1.6.1") {
      object Core : LibraryGroupChild(group = Coroutine, name = "kotlinx-coroutines-core"), Implementable
      object Android : LibraryGroupChild(group = Coroutine, name = "kotlinx-coroutines-android"), Implementable
      object Test : LibraryGroupChild(group = Coroutine, name = "kotlinx-coroutines-test"), TestImplementable, Implementable
    }
  }

  object Lifecycle : LibraryGroup(groupName = "androidx.lifecycle", version = "2.4.1") {
    object LiveDataCore : LibraryGroupChild(group = Lifecycle, name = "lifecycle-livedata-core-ktx"), Implementable
    object LiveData : LibraryGroupChild(group = Lifecycle, name = "lifecycle-livedata-ktx"), Implementable
    object Runtime : LibraryGroupChild(group = Lifecycle, name = "lifecycle-runtime-ktx"), Implementable
    object ViewModel : LibraryGroupChild(group = Lifecycle, name = "lifecycle-viewmodel-ktx"), Implementable
  }

  object DataStore : LibraryGroup(groupName = "androidx.datastore", version = "1.0.0"), Implementable, Library {
    override val name: String = "datastore"

    object Core : LibraryGroupChild(group = DataStore, name = "datastore-core"), Implementable
    object Preference : LibraryGroupChild(group = DataStore, name = "datastore-preferences"), Implementable
    object PreferenceCore : LibraryGroupChild(group = DataStore, name = "datastore-preferences-core"), Implementable
  }

  object Navigation : LibraryGroup(groupName = "androidx.navigation", version = "2.4.2") {
    object Runtime : LibraryGroupChild(group = Navigation, name = "navigation-runtime-ktx"), Implementable
    object Fragment : LibraryGroupChild(group = Navigation, name = "navigation-fragment-ktx"), Implementable
    object UI : LibraryGroupChild(group = Navigation, name = "navigation-ui-ktx"), Implementable
  }

  object Glide : LibraryGroup(groupName = "com.github.bumptech.glide", version = "4.13.0"), Implementable, Library {
    override val name: String = "glide"

    object Compiler : LibraryGroupChild(group = Glide, name = "compiler"), Kapt
    object Integration : LibraryGroupChild(group = Glide, name = "okhttp3-integration"), Implementable
    object Transformations : BaseLibrary(groupName = "jp.wasabeef", name = "glide-transformations", version = "4.0.0"), Implementable
  }

  object Retrofit2 : LibraryGroup(groupName = "com.squareup.retrofit2", version = "2.9.0"), Implementable, Library {
    override val name: String = "retrofit"

    object ConverterGson : LibraryGroupChild(group = Retrofit2, name = "converter-gson"), Implementable
    object ConverterScalars : LibraryGroupChild(group = Retrofit2, name = "converter-scalars", version = "2.5.0"), Implementable
  }

  object OkHttp3 : LibraryGroup(groupName = "com.squareup.okhttp3", version = "4.9.3"), Implementable, Library {
    override val name: String = "okhttp"

    object LoggingInterceptor : LibraryGroupChild(group = OkHttp3, name = "logging-interceptor"), Implementable
  }

  object Hilt : LibraryGroup(groupName = "com.google.dagger", version = "2.40.5"), Implementable, Library {
    override val name: String = "hilt-android"

    object AndroidCompiler : LibraryGroupChild(group = Hilt, name = "hilt-android-compiler"), Kapt
  }

  object TedPermission : BaseLibrary(groupName = "gun0912.ted", name = "tedpermission", version = "2.2.2"), Implementable

  object Test {
    object Mockk : BaseLibrary(groupName = "io.mockk", name = "mockk", version = "1.12.3"), TestImplementable, Implementable
    object Kluent : BaseLibrary(groupName = "org.amshove.kluent", name = "kluent", version = "1.15"), TestImplementable, Implementable // For Fluent Assertion
    object CoreTesting : BaseLibrary(groupName = "androidx.arch.core", name = "core-testing", version = "2.1.0"), TestImplementable, Implementable
    object Robolectric : BaseLibrary(groupName = "org.robolectric", name = "robolectric", version = "4.5.1"), TestImplementable, Implementable
    object Turbine : BaseLibrary(groupName = "app.cash.turbine", name = "turbine", version = "0.5.1"), TestImplementable, Implementable // For Flow Test

    object Jupiter : LibraryGroup(groupName = "org.junit.jupiter", version = "5.8.2") {
      object Api : LibraryGroupChild(group = Jupiter, name = "junit-jupiter-api"), TestImplementable, Implementable
      object Engine : LibraryGroupChild(group = Jupiter, name = "junit-jupiter-engine"), TestImplementable, Implementable
      object Params : LibraryGroupChild(group = Jupiter, name = "junit-jupiter-params"), TestImplementable, Implementable
    }

    object JUnit5 : LibraryGroup(groupName = "de.mannodermaus.junit5", version = "1.0.0") {
      object AndroidTestCore : LibraryGroupChild(group = JUnit5, name = "android-test-core"), TestImplementable, Implementable
      object AndroidTestRunner : LibraryGroupChild(group = JUnit5, name = "android-test-runner"), TestImplementable, Implementable
    }
  }
}