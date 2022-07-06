import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion

/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-18
 * @version 1.0.0
 */

/**
 * Sets the android{} configuration for the "app" module.
 */
fun AppExtension.setAppConfig() {
  setLibraryConfig()

  defaultConfig {
    applicationId = ProjectConfiguration.APPLICATION_ID
    versionCode = ProjectConfiguration.VERSION_CODE
    versionName = ProjectConfiguration.VERSION_NAME
    multiDexEnabled = true
  }

  dataBinding {
    isEnabled = true
  }
}

fun BaseExtension.setProductFlavors(getProperty: (String) -> Any?, isAppConfig: Boolean = false) {
  flavorDimensions("api")

  productFlavors {
    // 개발계
    create("dev") {
      versionCode = ProjectConfiguration.VERSION_CODE
      versionName = ProjectConfiguration.VERSION_NAME
      buildConfigField("String", "API_URL", getProperty("api.url").toString())
      buildConfigField("String", "SOCKET_URL", getProperty("socket.url").toString())
      resValue("string", "app_name", ProjectConfiguration.APPLICATION_NAME.plus("(개발)"))
      if (isAppConfig) applicationIdSuffix = ".dev"
    }

    create("production") {
      versionCode = ProjectConfiguration.PRODUCTION_VERSION_CODE
      versionName = ProjectConfiguration.PRODUCTION_VERSION_NAME
      buildConfigField("String", "API_URL", getProperty("production.api.url").toString())
      buildConfigField("String", "SOCKET_URL", getProperty("production.socket.url").toString())
      resValue("string", "app_name", ProjectConfiguration.APPLICATION_NAME)
    }
  }
}

/**
 * Sets the android{} configuration for the "library" modules.
 */
fun BaseExtension.setLibraryConfig() {
  compileSdkVersion(ProjectConfiguration.COMPILE_SDK)
  buildToolsVersion(ProjectConfiguration.BUILD_TOOLS)

  defaultConfig {
    minSdk = ProjectConfiguration.MIN_SDK
    targetSdk = ProjectConfiguration.TARGET_SDK

    testInstrumentationRunner = ProjectConfiguration.TEST_INSTRUMENTATION_RUNNER
    setTestInstrumentationRunnerArguments(ProjectConfiguration.TEST_INSTRUMENTATION_RUNNER_ARGUMENT)
  }

  sourceSets {
    getByName("main").java.srcDirs("src/main/kotlin")
  }

  packagingOptions {
    setExcludes(
      setOf("AndroidManifest.xml",
        "META-INF/*",
        "META-INF/MANIFEST.MF",
        "META-INF/DEPENDENCIES",
        "META-INF/NOTICE",
        "META-INF/LICENSE",
        "META-INF/LICENSE.txt",
        "META-INF/LICENSE*",
        "META-INF/NOTICE.txt",
        "META-INF/ASL2.0",
        "META-INF/INDEX.LIST",
        "META-INF/proguard/coroutines.pro",
        "META-INF/com.android.tools/proguard/coroutines.pro",
        "build.xml"
      )
    )

    setDoNotStrip(
      setOf("*/armeabi-v7a/*.so",
        "*/arm64-v8a/*.so")
    )
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}

fun BaseExtension.setDataBindingEnabled() {
  dataBinding {
    isEnabled = true
  }
}