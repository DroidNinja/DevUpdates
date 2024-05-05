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
    namespace = "com.dev.kotlinweekly"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":network"))
    implementation(project(":service-api"))
    implementation(libs.jsoup)

    implementation(libs.com.squareup.moshi)
    ksp(libs.com.squareup.moshi.kotlin.codegen)

    implementation(libs.hilt.library)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso.core)
}