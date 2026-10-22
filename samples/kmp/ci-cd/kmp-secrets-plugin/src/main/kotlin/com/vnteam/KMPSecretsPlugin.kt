package com.vnteam

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.util.Locale

class KMPSecretsPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension =
            project.extensions.create(
                Constants.SECRETS_EXTENSION_NAME,
                SecretsPluginExtension::class.java,
                project,
            )

        project.plugins.withId(Constants.KOTLIN_MULTIPLATFORM) {
            val kmpExtension =
                project.extensions
                    .getByType(KotlinMultiplatformExtension::class.java)

            configureSecretsGeneration(project, kmpExtension, extension)
        }
    }

    private fun configureSecretsGeneration(
        project: Project,
        kmpExtension: KotlinMultiplatformExtension,
        extension: SecretsPluginExtension,
    ) {
        kmpExtension.targets.configureEach {
            compilations.configureEach {
                val generateSecretsTaskName =
                    "${Constants.GENERATE_SECRETS_TASK}${
                        target.name.replaceFirstChar {
                            if (it.isLowerCase()) {
                                it.titlecase(
                                    Locale.getDefault(),
                                )
                            } else {
                                it.toString()
                            }
                        }
                    }${
                        name.replaceFirstChar {
                            if (it.isLowerCase()) {
                                it.titlecase(Locale.getDefault())
                            } else {
                                it.toString()
                            }
                        }
                    }"
                val generateSecretsTask =
                    project.tasks.register(generateSecretsTaskName) {
                        doFirst {
                            val generator = SecretsGenerator(project, extension)
                            val generatedFile = generator.generateSecrets()
                            GitIgnoreUpdater(project).addToGitIgnore(generatedFile)
                        }
                    }
                compileKotlinTask.dependsOn(generateSecretsTask)
            }
        }
    }
}
