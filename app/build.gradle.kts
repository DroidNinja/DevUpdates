plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_PARCELIZE)
    id("kotlin-android")
}

val javaVersion: JavaVersion by extra { JavaVersion.VERSION_1_8 }

android {
    compileSdkVersion(AndroidVersion.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId(AndroidVersion.APPLICATION_ID)
        minSdkVersion(AndroidVersion.MIN_SDK_VERSION)
        targetSdkVersion(AndroidVersion.TARGET_SDK_VERSION)
        versionCode(AndroidVersion.VERSION_CODE)
        versionName(AndroidVersion.VERSION_NAME)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = properties["releaseKeyStorePass"].toString()
            storeFile = rootProject.file("app/releaseKeyStore.jks")
            storePassword = properties["releaseKeyStorePass"].toString()
            keyPassword = properties["releaseKeyStorePass"].toString()
        }
        getByName("debug") {
            keyAlias = properties["debugKeyStorePass"].toString()
            storeFile = rootProject.file("debugKeyStore.jks")
            storePassword = properties["debugKeyStorePass"].toString()
            keyPassword = properties["debugKeyStorePass"].toString()
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

    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }

    buildFeatures{
        viewBinding = true
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":network"))
    implementation(project(":source-github"))
    implementation(project(":source-medium"))
    implementation(project(":source-androidweekly"))
    implementation(project(":source-rss"))
    implementation(project(":services"))
    debugImplementation(project(":devik"))
    releaseImplementation(project(":devik-noop"))
    implementation(Dependencies.AndroidX.Material)
    implementation(Dependencies.Di.Dagger)
    implementation(Dependencies.AndroidX.AppCompat)
    implementation(Dependencies.AndroidX.ConstraintLayout)
    implementation(Dependencies.AndroidX.Fragment)
    implementation(Dependencies.Ktx.Fragment)
    implementation(Dependencies.AndroidX.SwipeRefreshLayout)

    implementation(Dependencies.Compose.UI)
    implementation(Dependencies.Compose.ActivityCompose)
    implementation(Dependencies.Compose.MaterialCompose)
    implementation(Dependencies.Compose.MaterialIconsCompose)
    implementation(Dependencies.Compose.Foundation)
    implementation(Dependencies.Compose.FoundationLayout)
    implementation(Dependencies.Compose.Animation)
    implementation(Dependencies.Compose.Runtime)
    implementation(Dependencies.Compose.RuntimeLivedata)
    implementation(Dependencies.Compose.Navigation)
    implementation(Dependencies.Compose.Tooling)
    implementation(Dependencies.Compose.ConstraintLayout)

    implementation(Dependencies.AndroidX.Room)
    kapt(Dependencies.Compilers.Room)

    kapt(Dependencies.Compilers.Dagger)

    testImplementation(Dependencies.AndroidTest.Junit)
    androidTestImplementation(Dependencies.AndroidTest.TestExt)
    androidTestImplementation(Dependencies.AndroidTest.TestRunner)
    androidTestImplementation(Dependencies.AndroidTest.EspressoCore)
}
