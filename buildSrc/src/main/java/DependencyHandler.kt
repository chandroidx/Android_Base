import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

/**
 * Put in this file all the ways the libraries configurations can be added in the [DependencyHandler].
 */

fun DependencyHandler.projectImplementation(vararg path: String) {
  path.forEach { projectDependency ->
    add("implementation", project(projectDependency))
  }
}

fun DependencyHandler.implementation(vararg dependencies: Implementable) {
  dependencies.forEach { dependency ->
    add("implementation", dependency.asString())
  }
}

fun DependencyHandler.implementation(vararg dependencies: String) {
  dependencies.forEach { dependency ->
    add("implementation", dependency)
  }
}

fun DependencyHandler.testProjectImplementation(vararg path: String) {
  path.forEach { projectDependency ->
    add("testImplementation", project(projectDependency))
  }
}

fun DependencyHandler.testImplementation(vararg dependencies: TestImplementable) {
  dependencies.forEach { dependency ->
    add("testImplementation", dependency.asString())
  }
}

fun DependencyHandler.testRuntimeOnly(vararg dependencies: TestImplementable) {
  dependencies.forEach { dependency ->
    add("testRuntimeOnly", dependency.asString())
  }
}

fun DependencyHandler.androidTestImplementation(vararg dependencies: TestImplementable) {
  dependencies.forEach { dependency ->
    add("androidTestImplementation", dependency.asString())
  }
}

fun DependencyHandler.androidTestRuntimeOnly(vararg dependencies: TestImplementable) {
  dependencies.forEach { dependency ->
    add("androidTestRuntimeOnly", dependency.asString())
  }
}

fun DependencyHandler.kapt(vararg dependencies: Kapt) {
  dependencies.forEach { dependency ->
    add("kapt", dependency.asString())
  }
}

fun DependencyHandler.kapt(vararg dependencies: String) {
  dependencies.forEach { dependency ->
    add("kapt", dependency)
  }
}


fun DependencyHandler.basePresentation() {
  projectImplementation(
    Modules.UTILITY,
    Modules.DOMAIN,
    Modules.PRESENTATION
  )

  implementation(
    Libraries.AndroidX.CORE_KTX,
    Libraries.AndroidX.APPCOMPAT,
    Libraries.Google.MATERIAL,
    Libraries.AndroidX.ACTIVITY,
    Libraries.AndroidX.FRAGMENT,
  )

  implementation(
    Libraries.Kotlin.Coroutine.Core,
    Libraries.Kotlin.Coroutine.Android,
    Libraries.Lifecycle.LiveDataCore,
    Libraries.Lifecycle.LiveData,
    Libraries.Lifecycle.Runtime,
    Libraries.Lifecycle.ViewModel,
    Libraries.TedPermission,
    Libraries.Glide,
    Libraries.Glide.Transformations,
    Libraries.Glide.Integration,
    Libraries.Hilt,
    Libraries.Navigation.Fragment,
    Libraries.Navigation.Runtime,
    Libraries.Navigation.UI
  )

  kapt(Libraries.AndroidX.ANNOTATION)
  kapt(
    Libraries.Glide.Compiler,
    Libraries.Hilt.AndroidCompiler
  )
}

fun DependencyHandler.configureTestImplements() {
  testProjectImplementation(Modules.TEST_UTILS)
  testImplementation(
    Libraries.Test.Robolectric,
    Libraries.Test.Jupiter.Api,
    Libraries.Test.Jupiter.Params,
    Libraries.Kotlin.Coroutine.Test,
    Libraries.Kotlin.TestJUnit,
    Libraries.Test.CoreTesting,
    Libraries.Test.Mockk,
    Libraries.Test.Kluent,
    Libraries.Test.Turbine
  )

  testRuntimeOnly(
    Libraries.Test.Jupiter.Engine,
  )

  androidTestImplementation(
    Libraries.Test.Jupiter.Api,
    Libraries.Test.Jupiter.Params,
    Libraries.Kotlin.Coroutine.Test,
    Libraries.Test.JUnit5.AndroidTestCore
  )

  androidTestRuntimeOnly(
    Libraries.Test.JUnit5.AndroidTestRunner
  )
}
