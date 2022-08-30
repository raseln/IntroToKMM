plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version Libs.Kotlin.version
    id("com.squareup.sqldelight")
}

kotlin {
    android()
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies{
                // Ktor
                implementation(Libs.Ktor.core)
                implementation(Libs.Ktor.jsonSerialization)
                implementation(Libs.Ktor.contentNegotiation)
                // Date time formatter that will work on both platform
                implementation(Libs.Kotlinx.datetime)

                // SQLDelight
                implementation(Libs.SQLDelight.runtime)

                // Coroutine
                implementation(Libs.Coroutine.core)

                // Koin
                implementation(Libs.Koin.core)
            }
        }
        val androidMain by getting {
            dependencies{
                // Ktor
                implementation(Libs.Ktor.clientAndroid)

                // SQLDelight
                implementation(Libs.SQLDelight.androidDriver)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                // Ktor
                implementation(Libs.Ktor.clientDarwin)

                // SQLDelight
                implementation(Libs.SQLDelight.nativeDriver)
            }
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 26
        targetSdk = 32
    }
}

// Adding the new memory manager for Kotlin/Native, which will become default soon.
kotlin.targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java) {
    binaries.all {
        binaryOptions["memoryModel"] = "experimental"
    }
}

// SQLDelight
sqldelight {
    database("AppDatabase") {
        packageName = "com.rasel.introtokmm.shared.cache"
    }
}
