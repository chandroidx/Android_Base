/**
 * @Description Class
 * @author yc.park (DEEP.FINE)
 * @since 2022-04-18
 * @version 1.0.0
 */
object ProjectConfiguration {
  const val APPLICATION_NAME = "Android Base"
  const val APPLICATION_ID = "ai.deepfine.androidBase"

  const val COMPILE_SDK = 31
  const val TARGET_SDK = 31
  const val MIN_SDK = 26
  const val BUILD_TOOLS = "30.0.3"
  const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
  val TEST_INSTRUMENTATION_RUNNER_ARGUMENT = mutableMapOf("runnerBuilder" to "de.mannodermaus.junit5.AndroidJUnit5Builder") // For JUnit5

  const val VERSION_CODE = 1
  const val VERSION_NAME = "1.0.0"

  const val PRODUCTION_VERSION_CODE = 1
  const val PRODUCTION_VERSION_NAME = "1.0.0"
}