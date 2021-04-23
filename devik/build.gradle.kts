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
    debugImplementation(Dependencies.DevOnly.Chuck)
    releaseImplementation(Dependencies.DevOnly.ChuckNoOp)
    implementation(Dependencies.DevOnly.Stetho)
    implementation(Dependencies.DevOnly.StethoOkhttp)
    debugImplementation(Dependencies.DevOnly.Flipper)
    debugImplementation(Dependencies.DevOnly.FlipperSoLoader)
    implementation(Dependencies.DevOnly.FlipperNetwork)
    implementation(Dependencies.DevOnly.StethoOkhttp)
    implementation(Dependencies.DevOnly.Leakcanary)
    implementation(Dependencies.Di.Dagger)
    kapt(Dependencies.Compilers.Dagger)

    testImplementation(Dependencies.AndroidTest.Junit)
    androidTestImplementation(Dependencies.AndroidTest.TestExt)
    androidTestImplementation(Dependencies.AndroidTest.TestRunner)
    androidTestImplementation(Dependencies.AndroidTest.EspressoCore)
}