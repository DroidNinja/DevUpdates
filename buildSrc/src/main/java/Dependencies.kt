object Dependencies {
    object Network {
        const val LoggingInterceptor =
            "com.squareup.okhttp3:logging-interceptor:${Versions.Network.LoggingInterceptor}"
        const val OkHttp = "com.squareup.okhttp3:okhttp:${Versions.Network.OkHttp}"
        const val Retrofit = "com.squareup.retrofit2:retrofit:${Versions.Network.Retrofit}"
        const val GsonConverter =
            "com.squareup.retrofit2:converter-gson:${Versions.Network.GsonConverter}"
    }

    object AndroidX {
        const val AppCompat = "androidx.appcompat:appcompat:${Versions.AndroidX.AppCompat}"
        const val LifeCycleExtensions =
            "androidx.lifecycle:lifecycle-extensions:${Versions.AndroidX.LifeCycleExtensions}"
        const val LifeCycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.AndroidX.LifeCycleViewModel}"
        const val CardView = "androidx.cardview:cardview:${Versions.AndroidX.CardView}"
        const val ConstraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.AndroidX.ConstraintLayout}"
        const val Material = "com.google.android.material:material:${Versions.AndroidX.Material}"
        const val RecyclerView = "androidx.recyclerview.recyclerview:${Versions.AndroidX.RecyclerView}"
        const val Fragment = "androidx.fragment:fragment:${Versions.AndroidX.Fragment}"
        const val Browser = "androidx.browser:browser:1.3.0"
        const val Room = "androidx.room:room-runtime:${Versions.AndroidX.Room}"
    }
    
    object JetBrains {
        const val Coroutines="org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.JetBrains.Coroutines}"
        const val CoroutinesAndroid="org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.JetBrains.Coroutines}"
        const val Kotlin="org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.Gradle.Kotlin}"
        const val KotlinExtensions="org.jetbrains.kotlin:kotlin-android-extensions:${Versions.Gradle.Kotlin}"
    }

    object Ktx {
        const val Core = "androidx.core:core-ktx:${Versions.Ktx.Core}"
        const val FragmentNavigation = "androidx.navigation:navigation-fragment-ktx:${Versions.Ktx.Fragment}"
        const val Fragment = "androidx.fragment:fragment-ktx:${Versions.Ktx.Fragment}"
    }
    
    object LifeCycle {
        const val LifecycleExtensions="androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val LifecycleRuntime="androidx.lifecycle:lifecycle-runtime:2.2.0"
        const val LifecycleCommonJava="androidx.lifecycle:lifecycle-common-java8:2.2.0"
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
    }

    object Helpers {
        const val Glide = "com.github.bumptech.glide:glide:${Versions.Helpers.Glide}"
        const val Shimmer = "com.facebook.shimmer:shimmer:0.5.0"
    }

    object ClassPath {
        const val Gradle = "com.android.tools.build:gradle:${Versions.Gradle.Gradle}"
        const val Kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.Gradle.Kotlin}"
        const val Hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Di.Hilt}"
    }

    object AndroidTest {
        const val ArchCore="androidx.arch.core:core-testing:2.1.0"
        const val Hamcrest="org.hamcrest:hamcrest-all:1.3"
        const val MockRetrofit="com.squareup.okhttp:mockwebserver:2.7.2"
        const val Junit="junit:junit:4.12"
        const val TestRunner="androidx.test:runner:1.2.0"
        const val TestExt="androidx.test.ext:junit:1.1.2"
        const val EspressoCore="androidx.test.espresso:espresso-core:3.2.0"
        const val EspressoIntents="com.android.support.test.espresso:espresso-intents:3.0.1"
        const val KotlinCoroutineTest="org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.2"
    }

    object Mockito {
        const val Core="org.mockito:mockito-core:${Versions.Test.Mockito}"
        const val Android="org.mockito:mockito-android:${Versions.Test.Mockito}"
    }

    object Utils {
        const val Jsoup = "org.jsoup:jsoup:1.13.1"
        const val RssParser = "com.tickaroo.tikxml:retrofit-converter:0.8.15"
    }

    object TikXML {
        const val annotation = "com.tickaroo.tikxml:annotation:${Versions.Helpers.Tikxml}"
        const val apt = "com.tickaroo.tikxml:processor:${Versions.Helpers.Tikxml}"
        const val core = "com.tickaroo.tikxml:core:${Versions.Helpers.Tikxml}"
        const val htmlEscape = "com.tickaroo.tikxml:converter-htmlescape:${Versions.Helpers.Tikxml}"
        const val retrofit = "com.tickaroo.tikxml:retrofit-converter:${Versions.Helpers.Tikxml}"
    }
    
    object DevOnly {
        const val Leakcanary="com.squareup.leakcanary:leakcanary-android:2.1"
        const val LeakcanaryNoOp="com.squareup.leakcanary:leakcanary-android-no-op:2.1"
        const val Stetho="com.facebook.stetho:stetho:1.5.1"
        const val StethoOkhttp="com.facebook.stetho:stetho-okhttp3:1.5.1"
        const val Chuck = "com.github.ChuckerTeam.Chucker:library:3.2.0"
        const val ChuckNoOp = "com.github.ChuckerTeam.Chucker:library-no-op:3.2.0"
        const val Timber = "com.jakewharton.timber:timber:4.7.1"
    }
}