plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_PARCELIZE)
}

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

android {
    compileSdk = AndroidVersion.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AndroidVersion.MIN_SDK_VERSION
        targetSdk = AndroidVersion.TARGET_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    api(Dependencies.JetBrains.Coroutines)
    api(Dependencies.JetBrains.CoroutinesAndroid)
    api(Dependencies.Network.Retrofit)
    api(Dependencies.Network.GsonConverter)
    api(Dependencies.Network.LoggingInterceptor)
    debugApi(Dependencies.DevOnly.Chuck)
    releaseApi(Dependencies.DevOnly.ChuckNoOp)

    implementation(Dependencies.Di.Hilt)
    kapt(Dependencies.Compilers.Hilt)

    testImplementation(Dependencies.AndroidTest.Junit)
    androidTestImplementation(Dependencies.AndroidTest.TestExt)
    androidTestImplementation(Dependencies.AndroidTest.TestRunner)
    androidTestImplementation(Dependencies.AndroidTest.EspressoCore)
}