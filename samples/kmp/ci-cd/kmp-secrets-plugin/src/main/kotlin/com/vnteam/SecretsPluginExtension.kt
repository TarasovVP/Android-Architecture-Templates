package com.vnteam

import org.gradle.api.Project

open class SecretsPluginExtension(
    project: Project,
) {
    var outputDir: String = "${project.projectDir}/${Constants.DEFAULT_OUTPUT_DIR}"
}
