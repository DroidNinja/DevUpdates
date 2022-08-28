plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.HILT)
    id(Plugins.GOOGLE_SERVICES)
    id(Plugins.CRASHLYTICS)
}

android {
    compileSdk = AndroidVersion.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = AndroidVersion.APPLICATION_ID
        minSdk = AndroidVersion.MIN_SDK_VERSION
        targetSdk = AndroidVersion.TARGET_SDK_VERSION
        versionCode = AndroidVersion.VERSION_CODE
        versionName = AndroidVersion.VERSION_NAME
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = rootProject.properties["releaseKeyStorePass"].toString()
            storeFile = rootProject.file("app/releaseKeyStore.jks")
            storePassword = rootProject.properties["releaseKeyStorePass"].toString()
            keyPassword = rootProject.properties["releaseKeyStorePass"].toString()
        }
        getByName("debug") {
            keyAlias = rootProject.properties["debugKeyStorePass"].toString()
            storeFile = rootProject.file("debugKeyStore.jks")
            storePassword = rootProject.properties["debugKeyStorePass"].toString()
            keyPassword = rootProject.properties["debugKeyStorePass"].toString()
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
            isDebuggable = false
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-dev"
        }
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":network"))
    implementation(project(":source-github"))
    implementation(project(":source-medium"))
    implementation(project(":source-androidweekly"))
    implementation(project(":source-kotlinweekly"))
    implementation(project(":source-rss"))
    implementation(project(":services"))
//    debugImplementation(project(":devik"))
//    releaseImplementation(project(":devik-noop"))
    implementation(libs.google.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.fragment.fragment.ktx)
    implementation(libs.androidx.swiperefreshlayout)

    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.analytics)
    implementation(libs.google.crashlytics)

    implementation(libs.androidx.work.runtime)
    implementation(libs.androidx.startup.startup.runtime)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    implementation(libs.hilt.library)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
