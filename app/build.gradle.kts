plugins {
    id(Plugins.ANDROID_APPLICATION)
    kotlin(Plugins.KOTLIN_ANDROID)
    kotlin(Plugins.KOTLIN_KAPT)
    id(Plugins.KOTLIN_PARCELIZE)
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
    }

    signingConfigs {
        create("release") {
            keyAlias = properties["releaseKeyStorePass"].toString()
            storeFile = rootProject.file(".keystores/releaseKeyStore.jks")
            storePassword = properties["releaseKeyStorePass"].toString()
            keyPassword = properties["releaseKeyStorePass"].toString()
        }
        getByName("debug") {
            keyAlias = properties["debugKeyStorePass"].toString()
            storeFile = rootProject.file(".keystores/debugKeyStore.jks")
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
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":network"))
    implementation(project(":services"))
    implementation(Dependencies.AndroidX.Material)
    implementation(Dependencies.Di.Dagger)
    implementation(Dependencies.AndroidX.AppCompat)
    implementation(Dependencies.AndroidX.ConstraintLayout)
    implementation(Dependencies.AndroidX.Fragment)
    implementation(Dependencies.Ktx.Fragment)

    kapt(Dependencies.Compilers.Dagger)

    testImplementation(Dependencies.AndroidTest.Junit)
    androidTestImplementation(Dependencies.AndroidTest.TestRunner)
    androidTestImplementation(Dependencies.AndroidTest.EspressoCore)
}

