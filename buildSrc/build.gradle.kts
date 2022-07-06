repositories {
  google()
  mavenCentral()
  maven(url = "https://www.jitpack.io")
  jcenter()
}

plugins {
  `kotlin-dsl` // java dsl 설정
}

dependencies {
  implementation("com.android.tools.build:gradle:7.1.3")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
  implementation("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
}
