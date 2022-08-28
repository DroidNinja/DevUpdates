plugins {
    id(Plugins.ANDROID_LIBRARY)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_PARCELIZE)
}

android {
    compileSdk = AndroidVersion.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AndroidVersion.MIN_SDK_VERSION
        targetSdk = AndroidVersion.TARGET_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    api(libs.google.material)
    api(libs.androidx.appcompat)
    api(libs.androidx.constraintlayout)
    api(libs.androidx.browser)
    api(libs.com.facebook.shimmer)
    api(libs.kotlin.coroutines.core)
    api(libs.kotlin.coroutines.android)
    api(libs.androidx.core)
    api(libs.androidx.lifecycle.lifecycle.extensions)
    api(libs.androidx.lifecycle.runtime)
    api(libs.androidx.lifecycle.lifecycle.common.java8)
    api(libs.timber)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
