import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    js(IR) {
        browser {
            commonWebpackConfig {
                outputFileName = "webApp.js"
            }

        }
        binaries.executable()
    }
    task("testClasses")
    sourceSets {
        jsMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.androidx.viewmodel.compose)
            //Compose
            implementation(compose.html.core)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            //Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(npm("sql.js", "1.6.2"))
            implementation(devNpm("copy-webpack-plugin", "6.4.1"))

            implementation(project(":shared"))
        }
    }
}

rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin::class.java) {
    rootProject.the<YarnRootExtension>().yarnLockMismatchReport =
        YarnLockMismatchReport.WARNING
    rootProject.the<YarnRootExtension>().reportNewYarnLock = false
    rootProject.the<YarnRootExtension>().yarnLockAutoReplace = false
}