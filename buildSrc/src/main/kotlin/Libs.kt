object Libs {

    object AndroidX {
        private const val appCompatVersion = "1.3.0-rc01"
        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
    }

    object Compose {
        const val composeVersion = "1.2.0"
        const val runtime = "androidx.compose.runtime:runtime:${composeVersion}"
        const val ui = "androidx.compose.ui:ui:${composeVersion}"
        const val material = "androidx.compose.material3:material3:1.0.0-alpha15"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${composeVersion}"
        const val foundation = "androidx.compose.foundation:foundation:${composeVersion}"
        const val compiler = "androidx.compose.compiler:compiler:${composeVersion}"

        private const val constraintLayoutComposeVersion = "1.0.0-alpha07"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout-compose:${constraintLayoutComposeVersion}"

        private const val composeActivitiesVersion = "1.3.0-rc01"
        const val activity = "androidx.activity:activity-compose:${composeActivitiesVersion}"

        private const val navVersion = "2.4.2"
        const val navigation = "androidx.navigation:navigation-compose:${navVersion}"

        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:0.26.0-alpha"
    }

    object Coil {
        const val compose = "io.coil-kt:coil-compose:2.1.0"
    }

    object Coroutine {
        private const val version = "1.6.3"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    }

    object Koin {
        private const val koinVersion = "3.2.0"
        const val android = "io.insert-koin:koin-android:$koinVersion"
        const val core = "io.insert-koin:koin-core:$koinVersion"
        const val compose = "io.insert-koin:koin-androidx-compose:$koinVersion"
        const val test = "io.insert-koin:koin-test:$koinVersion"
    }

    object Kotlin {
        const val version = "1.7.0"
    }

    object Kotlinx {
        private const val kotlinxDatetimeVersion = "0.3.3"
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${kotlinxDatetimeVersion}"
    }

    object Ktor {
        private const val ktorVersion = "2.0.3"
        const val core = "io.ktor:ktor-client-core:${ktorVersion}"
        const val jsonSerialization = "io.ktor:ktor-serialization-kotlinx-json:${ktorVersion}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${ktorVersion}"
        const val clientAndroid = "io.ktor:ktor-client-android:$ktorVersion"
        const val clientDarwin = "io.ktor:ktor-client-darwin:${ktorVersion}"
    }

    object Logger {
        const val timber = "com.jakewharton.timber:timber:5.0.1"
    }

    object SQLDelight {
        const val sqlDelightVersion = "1.5.3"
        const val runtime = "com.squareup.sqldelight:runtime:${sqlDelightVersion}"
        const val androidDriver = "com.squareup.sqldelight:android-driver:${sqlDelightVersion}"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:${sqlDelightVersion}"
    }
}
