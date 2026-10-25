package com.vnteam

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import java.util.Properties

class SecretsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("generateSecrets") {
            val localProperties = project.rootProject.file("local.properties")
            val kotlinSrcDir = project.file("${project.projectDir}/src/commonMain/kotlin")
            val configDir = project.file("$kotlinSrcDir/secrets")
            val configFile = project.file("$configDir/Secrets.kt")
            doLast {
                if (!localProperties.exists()) {
                    throw GradleException("local.properties file not found!")
                }
                val properties = Properties().apply {
                    load(localProperties.inputStream())
                }
                if (!configDir.exists()) {
                    configDir.mkdirs()
                }
                fun isValidKey(key: String): Boolean {
                    return key.matches(Regex("^[a-zA-Z_][a-zA-Z0-9_]*$"))
                }

                val packagePath =
                    configDir.relativeTo(project.file("${project.projectDir}/src/commonMain/kotlin"))
                val packageName = packagePath.toString().replace("/", ".").replace("\\", ".")
                val configContent = buildString {
                    appendLine("package $packageName")
                    appendLine()
                    appendLine("object Properties {")

                    properties.forEach { (keyAny, value) ->
                        val key = keyAny.toString()
                        if (isValidKey(key)) {
                            appendLine("    val $key = \"$value\"")
                        }
                    }

                    appendLine("}")
                }

                configFile.writeText(configContent)
            }
        }
        project.tasks.named("preBuild") {
            dependsOn("generateSecrets")
        }
    }
}