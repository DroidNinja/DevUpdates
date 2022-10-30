pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://oss.jfrog.org/libs-snapshot") }
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://oss.jfrog.org/libs-snapshot") }
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
}

include(":network")
include(":core")
include(":services")
rootProject.name="DevUpdates"
include(":app")
include(":source-medium")
include(":source-github")
include(":source-rss")
include(":devik")
include(":devik-noop")
include(":source-androidweekly")
include(":source-kotlinweekly")
include(":service-api")
