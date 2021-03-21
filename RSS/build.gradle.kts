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
    implementation(project(":core"))
    implementation(project(":network"))
    implementation(project(":services"))
    implementation(Dependencies.Utils.Jsoup)
    implementation(Dependencies.TikXML.annotation)
    implementation(Dependencies.TikXML.core)
    implementation(Dependencies.TikXML.retrofit)
    api(Dependencies.TikXML.htmlEscape)
    kapt(Dependencies.TikXML.apt)

    api(Dependencies.Di.Dagger)
    kapt(Dependencies.Compilers.Dagger)

    testImplementation(Dependencies.AndroidTest.Junit)
    androidTestImplementation(Dependencies.AndroidTest.TestRunner)
    androidTestImplementation(Dependencies.AndroidTest.EspressoCore)
}