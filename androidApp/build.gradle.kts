plugins {
  id("com.android.application")
  kotlin("android")
}

android {
  compileSdk = 31
  defaultConfig {
    applicationId = "ge.dev.waroffingerskmm.android"
    minSdk = 23
    targetSdk = 30
    versionCode = 1
    versionName = "1.0"
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
  buildFeatures {
    // Enables Jetpack Compose for this module
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.1.1"
  }
}

dependencies {
  implementation(project(":shared"))
  implementation("androidx.core:core-ktx:1.7.0")
  implementation("com.google.android.material:material:1.5.0")
  implementation("androidx.appcompat:appcompat:1.4.1")
  implementation("androidx.constraintlayout:constraintlayout:2.1.3")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

  implementation("io.insert-koin:koin-android:3.2.0-beta-1")
  implementation("io.insert-koin:koin-androidx-compose:3.2.0-beta-1")


  // Integration with activities
  implementation("androidx.activity:activity-compose:1.4.0")
  // Navigation
  implementation("androidx.navigation:navigation-compose:2.4.1")
  implementation("androidx.compose.ui:ui:1.1.1")
  implementation("androidx.compose.ui:ui-util:1.2.0-alpha05")
  // Tooling support (Previews, etc.)
  implementation("androidx.compose.ui:ui-tooling:1.1.1")
  // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
  implementation("androidx.compose.foundation:foundation:1.1.1")
  // Material Design
  implementation("androidx.compose.material:material:1.1.1")
  // ConstraintLayout
  implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0")

  // Material design icons
  implementation("androidx.compose.material:material-icons-core:1.1.1")
  implementation("androidx.compose.material:material-icons-extended:1.1.1")
  // Integration with observables
  implementation("androidx.compose.runtime:runtime-livedata:1.1.1")
}