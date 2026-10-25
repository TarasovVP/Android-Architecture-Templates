import com.android.build.gradle.internal.tasks.factory.dependsOn
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.sqlDelight) apply false
    alias(libs.plugins.kotlinKover) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply true
    alias(libs.plugins.sonarqube) apply true
    alias(libs.plugins.dependency.analysis) apply false
}


subprojects {
    // dependency-analysis
    apply(plugin = "com.autonomousapps.dependency-analysis")
    if (this.name == "shared") {
        val soleSecretsTask = "generateSecretsMetadataMain"
        tasks.matching { it.name.startsWith("generateSecrets") && it.name != soleSecretsTask }
            .configureEach { enabled = false }

        tasks.matching { it.name.matches(Regex("compile.*Kotlin.*")) }
            .configureEach { dependsOn(soleSecretsTask) }
    }
    // kover
    apply(plugin = "org.jetbrains.kotlinx.kover")
    // ktlint
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
    dependencies {
        add("ktlint", project(":custom-ktlint-rules"))
    }
    // detekt
    apply(plugin = "io.gitlab.arturbosch.detekt")
    plugins.withId("io.gitlab.arturbosch.detekt") {
        configure<io.gitlab.arturbosch.detekt.extensions.DetektExtension> {
            source.setFrom(
                files(
                    "src/main/kotlin",
                    "src/commonMain/kotlin",
                    "src/jvmMain/kotlin",
                    "src/androidMain/kotlin",
                    "src/iosMain/kotlin",
                    "src/nativeMain/kotlin",
                    "src/desktopMain/kotlin",
                    "src/jsMain/kotlin",
                )
            )
            ignoreFailures = true
            config.setFrom(rootProject.file("detekt.yml"))
            buildUponDefaultConfig = true
        }
    }
}

sonarqube {
    properties {
        // kover reports
        val koverReports = allprojects.mapNotNull { project ->
            val reportPath = "${project.projectDir}/build/reports/kover/report.xml"
            if (File(reportPath).exists()) reportPath else null
        }
            .joinToString(",")
        property("sonar.coverage.jacoco.xmlReportPaths", koverReports)
        // detekt reports
        val detektReports = allprojects.mapNotNull { project ->
            val reportPath = "${project.projectDir}/build/reports/detekt/detekt.xml"
            if (File(reportPath).exists()) reportPath else null
        }
            .joinToString(",")
        property("sonar.kotlin.detekt.reportPaths", detektReports)
    }
}


tasks.named("sonar") {
    dependsOn(provider {
        subprojects.map { it.tasks.named("koverXmlReport") }
    }, "detekt")
}

val installGitHook = tasks.register("installGitHook", Copy::class) {
    group = "git hooks"
    description = "Installs the pre-commit Git hook script"
    from("$rootDir/pre-commit")
    into("$rootDir/.git/hooks")
    fileMode = "755".toInt(8)
}

project.pluginManager.withPlugin("org.jetbrains.kotlin.multiplatform") {
    val kmpExtension = project.extensions.getByType<KotlinMultiplatformExtension>()
    kmpExtension.targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.dependsOn(installGitHook)
        }
    }
}


