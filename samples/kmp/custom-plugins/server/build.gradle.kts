import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqlDelight)
    application
}

group = "com.vnteam.architecturetemplates"
version = "1.0.0"

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(projects.core)
                implementation(libs.logback)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.ktor.server.tests)
                // Koin
                implementation(libs.koin.core)
                implementation(libs.koin.ktor)
                // Ktor
                implementation(libs.ktor.server.core)
                implementation(libs.ktor.server.netty)
                implementation(libs.ktor.server.config.yaml)
                implementation(libs.ktor.server.cors)
                implementation(libs.ktor.server.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
                // DB
                implementation(libs.postgresql)
                implementation(libs.hikari.cp)
                implementation(libs.jdbc.driver)
                implementation(libs.postgres.socket.factory)
            }
        }
    }
}

application {
    mainClass.set("com.vnteam.architecturetemplates.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

sqldelight {
    databases {
        create("ServerDatabase") {
            packageName = "com.vnteam.architecturetemplates"
            srcDirs("src/jvmMain/sqldelight")
            dialect("app.cash.sqldelight:postgresql-dialect:2.0.2")
        }
    }
}

tasks {
    register<Jar>("fatJar") {
        archiveBaseName.set("ktor-server")
        manifest {
            attributes["Main-Class"] = "com.vnteam.architecturetemplates.ApplicationKt"
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
        from(sourceSets.main.get().output)
    }
}

val kmpExtension = extensions.getByType(KotlinMultiplatformExtension::class.java)

// Iterate over all KMP targets (iosArm64, iosSimulatorArm64, jvm, js, etc.)
kmpExtension.targets.configureEach {

    // Iterate over all compilations (main, test, debug, release, etc.)
    compilations.configureEach {

        // For example, "generateSecretsIosSimulatorArm64Main"
        val generateSecretsTaskName =
            buildString {
                append("generateSecrets")
                // Capitalize target name (first letter)
                append(target.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
                // Capitalize compilation name (first letter)
                append(name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
            }

        // Register the task that generates Secrets.kt
        val generateSecretsTask =
            tasks.register(generateSecretsTaskName) {

                group = "codegen"
                description = "Generate secrets for ${target.name}:$name"

                doFirst {
                    // 1) Find local.properties
                    val moduleProps = file("local.properties")
                    val globalProps = rootProject.file("local.properties")
                    val propsFile =
                        when {
                            moduleProps.exists() -> moduleProps
                            globalProps.exists() -> globalProps
                            else -> error("❌ No local.properties found for module: $name")
                        }
                    if (!propsFile.exists()) {
                        error("local.properties file not found at ${propsFile.absolutePath}")
                    }

                    // 2) Load .properties
                    val properties =
                        Properties().apply {
                            load(propsFile.inputStream())
                        }

                    // 3) Generate Secrets.kt in "src/commonMain/kotlin/secrets/Secrets.kt"
                    val srcDir = File("$projectDir/src/commonMain/kotlin")
                    val secretsPackageDir = File(srcDir, "secrets")
                    val secretsFile = File(secretsPackageDir, "Secrets.kt")

                    if (!secretsPackageDir.exists()) {
                        secretsPackageDir.mkdirs()
                    }

                    val content =
                        buildString {
                            appendLine("package secrets")
                            appendLine()
                            appendLine("object Properties {")
                            properties.forEach { (k, v) ->
                                val key = k.toString()
                                if (Regex("^[a-zA-Z_][a-zA-Z0-9_]*$").matches(key)) {
                                    appendLine("    val $key = \"$v\"")
                                }
                            }
                            appendLine("}")
                        }
                    secretsFile.writeText(content)

                    // 4) Add secretsFile to .gitignore
                    val gitIgnoreFile = file(".gitignore")
                    val relativePath = secretsFile.relativeTo(projectDir).path.replace("\\", "/")

                    if (!gitIgnoreFile.exists()) {
                        gitIgnoreFile.writeText("# Auto-generated .gitignore\n$relativePath\n")
                    } else {
                        val existing = gitIgnoreFile.readText()
                        if (!existing.contains(relativePath)) {
                            gitIgnoreFile.appendText("\n$relativePath\n")
                        }
                    }
                }
            }

        // 5) Link it to the compileKotlin task
        compileKotlinTask.dependsOn(generateSecretsTask)
    }
}
