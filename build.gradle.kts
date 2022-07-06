// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
  repositories {
    google()
    mavenCentral()
    maven(url = "https://www.jitpack.io")
    jcenter()
  }

  dependencies {
    classpath("com.android.tools.build:gradle:7.1.3")
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.40.5")
    classpath("de.mannodermaus.gradle.plugins:android-junit5:1.7.1.1")
    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
  }
}

plugins {
  id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
}

subprojects {
  apply(plugin = "org.jlleitschuh.gradle.ktlint")

  repositories {
    mavenCentral()
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}