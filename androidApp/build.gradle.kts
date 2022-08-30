plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version Libs.Kotlin.version
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "com.rasel.introtokmm.android"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Compose.composeVersion
    }
}

dependencies {
    implementation(project(":shared"))

    // Coroutine
    implementation(Libs.Coroutine.android)

    // Coil
    implementation(Libs.Coil.compose)

    // Koin
    implementation(Libs.Koin.core)
    implementation(Libs.Koin.android)
    implementation(Libs.Koin.compose)

    // Logger
    implementation(Libs.Logger.timber)

    // Copy
    implementation(Libs.AndroidX.appCompat)

    implementation(Libs.Compose.runtime)
    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.material)
    implementation(Libs.Compose.uiTooling)
    implementation(Libs.Compose.foundation)
    implementation(Libs.Compose.compiler)
    implementation(Libs.Compose.constraintLayout)
    implementation(Libs.Compose.activity)
    implementation(Libs.Compose.navigation)
    implementation(Libs.Compose.swipeRefresh)
}