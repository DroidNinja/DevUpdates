plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.kapt)
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
    namespace = "com.dev.rss"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":network"))
    implementation(project(":service-api"))
    implementation(libs.tikxml.annotation)
    implementation(libs.tikxml.core)
    implementation(libs.tikxml.retrofit.converter)
    api(libs.tikxml.converter.htmlescape)
    kapt(libs.tikxml.processor)

    implementation(libs.jsoup)

    implementation(libs.hilt.library)
    ksp(libs.hilt.compiler)

    implementation(libs.com.squareup.moshi)
    ksp(libs.com.squareup.moshi.kotlin.codegen)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso.core)
}