plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    compileSdk = AndroidVersion.COMPILE_SDK_VERSION
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        compileOptions {
            // Up to Java 11 APIs are available through desugaring
            // https://developer.android.com/studio/write/java11-minimal-support-table
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }

    buildFeatures {
        viewBinding = true
    }
    namespace = "com.dev.core"
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
    ksp(libs.hilt.compiler)
    ksp(libs.dagger.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
