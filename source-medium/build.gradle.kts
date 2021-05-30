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
    implementation(project(":core"))
    implementation(project(":network"))
    implementation(project(":services"))
    implementation(Dependencies.Utils.Jsoup)

    implementation(Dependencies.Di.Hilt)
    kapt(Dependencies.Compilers.Hilt)

    testImplementation(Dependencies.AndroidTest.Junit)
    androidTestImplementation(Dependencies.AndroidTest.TestExt)
    androidTestImplementation(Dependencies.AndroidTest.TestRunner)
    androidTestImplementation(Dependencies.AndroidTest.EspressoCore)
}