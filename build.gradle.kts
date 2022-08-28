buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath(libs.android.pluginGradle)
        classpath(libs.kotlin.pluginGradle)
        classpath(libs.hilt.pluginGradle)
        classpath(libs.google.gmsGoogleServices)
        classpath(libs.google.crashlyticsGradle)
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.41.0"
    id("nl.littlerobots.version-catalog-update") version "0.5.3"
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://oss.jfrog.org/libs-snapshot") }
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}

apply(from = file("gradle/dependency-graph.gradle"))