buildscript {
    repositories {
        google()
        mavenCentral()
        maven("http://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath(Dependencies.ClassPath.Gradle)
        classpath(Dependencies.ClassPath.Kotlin)
        classpath(Dependencies.ClassPath.Hilt)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://oss.jfrog.org/libs-snapshot") }
        maven("http://oss.sonatype.org/content/repositories/snapshots")
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}