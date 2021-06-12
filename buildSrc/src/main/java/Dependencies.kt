object Dependencies {
    object Network {
        const val LoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Network.LoggingInterceptor}"
        const val OkHttp = "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}"
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
        const val GsonConverter =
            "com.squareup.retrofit2:converter-gson:${Versions.Network.GsonConverter}"
        const val MoshiConverter =
            "com.squareup.retrofit2:converter-moshi:${Versions.Network.MoshiConverter}"
        const val Moshi =
            "com.squareup.moshi:moshi:${Versions.Network.Moshi}"
    }

    object AndroidX {
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.AppCompat}"
        const val CardView = "androidx.cardview:cardview:${Versions.AndroidX.CardView}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.ConstraintLayout}"
        const val Material = "com.google.android.material:material:${Versions.AndroidX.Material}"
        const val RecyclerView =
            "androidx.recyclerview.recyclerview:${Versions.AndroidX.RecyclerView}"
        const val Fragment = "androidx.fragment:fragment:${Versions.AndroidX.Fragment}"
        const val Browser = "androidx.browser:browser:1.3.0"
        const val Room = "androidx.room:room-runtime:${Versions.AndroidX.Room}"
        const val WorkManager = "androidx.work:work-runtime-ktx:${Versions.AndroidX.WorkManager}"
        const val SwipeRefreshLayout =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.AndroidX.SwipeRefreshLayout}"
    }

    object JetBrains {
        const val Coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.JetBrains.Coroutines}"
        const val CoroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.JetBrains.Coroutines}"
        const val Kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Gradle.Kotlin}"
        const val KotlinExtensions =
            "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.Gradle.Kotlin}"
    }

    object Ktx {
        const val Core = "androidx.core:core-ktx:${Versions.Ktx.Core}"
        const val FragmentNavigation =
            "androidx.navigation:navigation-fragment-ktx:${Versions.Ktx.Fragment}"
        const val Fragment = "androidx.fragment:fragment-ktx:${Versions.Ktx.Fragment}"
        const val Room = "androidx.room:room-ktx:${Versions.AndroidX.Room}"

    }

    object LifeCycle {
        const val LifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.LifeCycleExtensions}"
        const val LifecycleRuntime = "androidx.lifecycle:lifecycle-runtime:${Versions.AndroidX.LifeCycleRuntime}"
        const val LifecycleCommonJava = "androidx.lifecycle:lifecycle-common-java8:2.3.1"
        const val LifeCycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.LifeCycleViewModel}"
        const val LifeCycleOnly =
            "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.AndroidX.LifeCycleRuntime}"
    }

    object Di {
        const val Dagger = "com.google.dagger:dagger:${Versions.Di.Dagger}"
        const val Hilt = "com.google.dagger:hilt-android:${Versions.Di.Hilt}"
    }

    object Compilers {
        const val Dagger = "com.google.dagger:dagger-compiler:${Versions.Di.Dagger}"
        const val Hilt = "com.google.dagger:hilt-android-compiler:${Versions.Di.Hilt}"
        const val GlideCompiler = "com.github.bumptech.glide:compiler:${Versions.Helpers.Glide}"
        const val Room = "androidx.room:room-compiler:${Versions.AndroidX.Room}"
        const val Moshi =
            "com.squareup.moshi:moshi-kotlin-codegen:${Versions.Network.Moshi}"
    }

    object Helpers {
        const val Glide = "com.github.bumptech.glide:glide:${Versions.Helpers.Glide}"
        const val Shimmer = "com.facebook.shimmer:shimmer:0.5.0"
    }

    object ClassPath {
        const val Gradle = "com.android.tools.build:gradle:${Versions.Gradle.Gradle}"
        const val Kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Gradle.Kotlin}"
        const val Hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Di.Hilt}"
        const val Firebase = "com.google.gms:google-services:${Versions.Firebase.GoogleServices}"
        const val Crashlytics =
            "com.google.firebase:firebase-crashlytics-gradle:${Versions.Firebase.Crashlytics}"
    }

    object AndroidTest {
        const val ArchCore = "androidx.arch.core:core-testing:2.1.0"
        const val Hamcrest = "org.hamcrest:hamcrest-all:1.3"
        const val MockRetrofit = "com.squareup.okhttp:mockwebserver:2.7.2"
        const val Junit = "junit:junit:4.12"
        const val TestRunner = "androidx.test:runner:1.2.0"
        const val TestExt = "androidx.test.ext:junit:1.1.2"
        const val EspressoCore = "androidx.test.espresso:espresso-core:3.2.0"
        const val EspressoIntents = "com.android.support.test.espresso:espresso-intents:3.0.1"
        const val KotlinCoroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.2"
    }

    object Mockito {
        const val Core = "org.mockito:mockito-core:${Versions.Test.Mockito}"
        const val Android = "org.mockito:mockito-android:${Versions.Test.Mockito}"
    }

    object Utils {
        const val Jsoup = "org.jsoup:jsoup:1.13.1"
        const val RssParser = "com.tickaroo.tikxml:retrofit-converter:0.8.15"
        const val Gson = "com.google.code.gson:gson:2.8.7"
    }

    object TikXML {
        const val annotation = "com.tickaroo.tikxml:annotation:${Versions.Helpers.Tikxml}"
        const val apt = "com.tickaroo.tikxml:processor:${Versions.Helpers.Tikxml}"
        const val core = "com.tickaroo.tikxml:core:${Versions.Helpers.Tikxml}"
        const val htmlEscape = "com.tickaroo.tikxml:converter-htmlescape:${Versions.Helpers.Tikxml}"
        const val retrofit = "com.tickaroo.tikxml:retrofit-converter:${Versions.Helpers.Tikxml}"
    }

    object Compose {
        const val UI = "androidx.compose.ui:ui:${Versions.Compose.ComposeVersion}"
        const val ActivityCompose = "androidx.activity:activity-compose:1.3.0-alpha07"
        const val MaterialCompose =
            "androidx.compose.material:material:${Versions.Compose.ComposeVersion}"
        const val MaterialIconsCompose =
            "androidx.compose.material:material-icons-extended:${Versions.Compose.ComposeVersion}"
        const val Foundation =
            "androidx.compose.foundation:foundation:${Versions.Compose.ComposeVersion}"
        const val FoundationLayout =
            "androidx.compose.foundation:foundation-layout:${Versions.Compose.ComposeVersion}"
        const val Animation =
            "androidx.compose.animation:animation:${Versions.Compose.ComposeVersion}"
        const val Runtime = "androidx.compose.runtime:runtime:${Versions.Compose.ComposeVersion}"
        const val RuntimeLivedata =
            "androidx.compose.runtime:runtime-livedata:${Versions.Compose.ComposeVersion}"
        const val Navigation = "androidx.navigation:navigation-compose:1.0.0-alpha10"
        const val Tooling = "androidx.compose.ui:ui-tooling:${Versions.Compose.ComposeVersion}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout-compose:1.0.0-alpha06"
    }

    object Firebase {
        const val Bom = "com.google.firebase:firebase-bom:28.1.0"
        const val AnalyticsKtx = "com.google.firebase:firebase-analytics-ktx"
        const val CrashlyticsKtx = "com.google.firebase:firebase-crashlytics-ktx"
    }


    object DevOnly {
        const val Leakcanary = "com.squareup.leakcanary:leakcanary-android:2.7"
        const val Stetho = "com.facebook.stetho:stetho:1.6.0"
        const val StethoOkhttp = "com.facebook.stetho:stetho-okhttp3:1.6.0"
        const val Chuck = "com.github.ChuckerTeam.Chucker:library:3.2.0"
        const val ChuckNoOp = "com.github.ChuckerTeam.Chucker:library-no-op:3.2.0"
        const val Timber = "com.jakewharton.timber:timber:4.7.1"

        //flipper
        const val Flipper = "com.facebook.flipper:flipper:0.85.0"
        const val FlipperSoLoader = "com.facebook.soloader:soloader:0.10.1"
        const val FlipperNoOp = "com.facebook.flipper:flipper-noop:0.85.0"
        const val FlipperNetwork = "com.facebook.flipper:flipper-network-plugin:0.85.0"
    }
}