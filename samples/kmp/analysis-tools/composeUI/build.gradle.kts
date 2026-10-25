plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    task("testClasses")
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js(IR) {
        useCommonJs()
        browser()
    }
    jvm()
    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)
            // Compose
            implementation(compose.runtime)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.components.resources)
            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            // Navigation
            implementation(libs.navigation.compose)
        }
    }
}

android {
    namespace = "com.vnteam.architecturetemplates.composeUi"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        multiDexEnabled = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.vnteam.architecturetemplates.resources"
    generateResClass = always
}
