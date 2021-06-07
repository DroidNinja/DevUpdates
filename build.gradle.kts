buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath(Dependencies.ClassPath.Gradle)
        classpath(Dependencies.ClassPath.Kotlin)
        classpath(Dependencies.ClassPath.Hilt)
        classpath(Dependencies.ClassPath.Firebase)
        classpath(Dependencies.ClassPath.Crashlytics)
    }
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