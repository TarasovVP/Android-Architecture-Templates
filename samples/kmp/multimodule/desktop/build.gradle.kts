import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    jvm("desktop") {
        tasks.withType<KotlinJvmCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }
    }
    sourceSets {
        val desktopMain by getting

        desktopMain.dependencies {
            implementation(projects.shared)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(libs.androidx.viewmodel.compose)
            implementation(libs.kotlinx.serialization)
            //Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            //Coil
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            //Navigation
            implementation(libs.navigation.compose)
            implementation(libs.koin.core)
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.vnteam.architecturetemplates.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.vnteam.architecturetemplates"
            packageVersion = "1.0.0"
        }
    }
}