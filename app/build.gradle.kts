import java.util.Properties
import java.io.FileInputStream

plugins {
  androidApp()
  kotlinAndroid()
  kotlinKapt()
  hilt()
  jUnit5()
}

android {
  val keystorePropertiesFile = rootProject.file("keystore.properties")
  val properties = Properties()
  properties.load(FileInputStream(keystorePropertiesFile))

  signingConfigs {
    create("release") {
      storeFile = rootProject.file(properties["storeFile"]!!)
      storePassword = properties["storePassword"] as String
      keyAlias = properties["keyAlias"] as String
      keyPassword = properties["keyPassword"] as String
    }
  }

  setAppConfig()
  setProductFlavors(project::property, true)

  buildTypes {
    debug {
      isDebuggable = true
      isMinifyEnabled = false
      isShrinkResources = false
    }

    release {
      isDebuggable = false
      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("release")
      ndk.abiFilters.add("arm64-v8a")
    }
  }
}

dependencies {
  projectImplementation(
    Modules.UTILITY,
    Modules.DATA,
    Modules.DOMAIN,
    Modules.PRESENTATION,
    Modules.PRESENTATION_SPLASH,
  )

  implementation(
    Libraries.Retrofit2,
    Libraries.Retrofit2.ConverterGson,
    Libraries.Retrofit2.ConverterScalars,
    Libraries.OkHttp3.LoggingInterceptor,
    Libraries.Hilt,
  )

  kapt(Libraries.Hilt.AndroidCompiler)

  implementation(
    Libraries.AndroidX.MULTIDEX,
    Libraries.AndroidX.APPCOMPAT,
    Libraries.Google.MATERIAL,
    Libraries.Google.GSON,
  )

  configureTestImplements()
}