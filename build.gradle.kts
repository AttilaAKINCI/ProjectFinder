// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.3.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin") version("2.0.1") apply false
}