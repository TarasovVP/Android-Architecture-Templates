import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    androidTarget {
        tasks.withType<KotlinJvmCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_1_8)
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
            api(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.runtime)
            implementation(libs.stately.common)
            implementation(libs.kotlinx.serialization)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.viewmodel.compose)
            //Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            //SQLDelight
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
        }
        androidMain.dependencies {
            implementation(libs.androidx.multidex)
            implementation(libs.ktor.client.android)
            implementation(libs.sqldelight.android.driver)
            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            //Datastore
            implementation(libs.androidx.datastore.preferences)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
            //Datastore
            implementation(libs.androidx.datastore.preferences)
        }
        jvmMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.ktor.client.java)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.sqldelight.java.driver)
            implementation(libs.slf4j)
            //Datastore
            implementation(libs.androidx.datastore.preferences)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
            implementation(libs.web.worker.driver)
            implementation(npm("@cashapp/sqldelight-sqljs-worker", "2.0.2"))
            implementation(npm("sql.js", "1.6.2"))
            implementation(devNpm("copy-webpack-plugin", "9.1.0"))
        }
    }
}

android {
    namespace = "com.vnteam.architecturetemplates.shared"
    compileSdk = libs.versions.compileSdk.get().toInt()
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")
    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        multiDexEnabled = true
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.vnteam.architecturetemplates")
            generateAsync.set(true)
            version = 2
        }
    }
}


