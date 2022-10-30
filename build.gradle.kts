
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.gms.googleServices) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.spotless)
}

apply(from = file("gradle/dependency-graph.gradle"))