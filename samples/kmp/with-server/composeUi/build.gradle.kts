plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.composeMultiplatform)
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
            implementation(libs.kotlinx.serialization)
            implementation(projects.shared)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(libs.androidx.viewmodel.compose)
            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            // Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
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
    packageOfResClass = "com.vnteam.architecturetemplates"
    generateResClass = always
}