rootProject.name = "Android_Architecture_Templates"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        //mavenLocal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
    }
}

include(":core")
include(":shared")
include(":mobile")
include(":desktop")
include(":web")
include(":server")
include(":kmp-secrets-plugin")
include(":lint-checks")
include(":custom-ktlint-rules")
