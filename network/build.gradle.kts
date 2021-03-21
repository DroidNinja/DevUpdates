plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_PARCELIZE)
}

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

android {
    compileSdkVersion(AndroidVersion.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(AndroidVersion.MIN_SDK_VERSION)
        targetSdkVersion(AndroidVersion.TARGET_SDK_VERSION)
        versionCode(AndroidVersion.VERSION_CODE)
        versionName(AndroidVersion.VERSION_NAME)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
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
    api(Dependencies.Di.Dagger)
    kapt(Dependencies.Compilers.Dagger)

    testImplementation(Dependencies.AndroidTest.Junit)
    androidTestImplementation(Dependencies.AndroidTest.TestExt)
    androidTestImplementation(Dependencies.AndroidTest.TestRunner)
    androidTestImplementation(Dependencies.AndroidTest.EspressoCore)
}