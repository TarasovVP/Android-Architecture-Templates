import java.net.URI

rootProject.name = "Android_Architecture_Templates"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    plugins {
        id("com.gradle.enterprise") version "3.16.1"
    }
    repositories {
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
plugins {
    id("com.gradle.enterprise")
}
gradleEnterprise {
    buildScan {
        isUploadInBackground = false

        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

buildCache {
    remote(HttpBuildCache::class) {
        url = URI("http://localhost:5071/cache/")
        isPush = true
        isAllowInsecureProtocol = true
    }
}

include(":core")
include(":shared")
include(":composeUI")
include(":mobile")
include(":desktop")
include(":web")
include(":server")
include(":kmp-secrets-plugin")
include(":lint-checks")
include(":custom-ktlint-rules")
