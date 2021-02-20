buildscript {
    repositories {
        google()
        jcenter()
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
        jcenter()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://oss.jfrog.org/libs-snapshot") }
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}