import java.util.Properties

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.sqlDelight)
    application
}

group = "com.vnteam.architecturetemplates"
version = "1.0.0"
application {
    mainClass.set("com.vnteam.architecturetemplates.ApplicationKt")
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(projects.core)
    implementation(libs.logback)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.ktor.server.tests)
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

sqldelight {
    databases {
        create("ServerDatabase") {
            packageName = "com.vnteam.architecturetemplates"
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

tasks.register("generateSecrets") {
    val localProperties = file(rootProject.file("local.properties"))
    val kotlinSrcDir = project.file("${project.projectDir}/src/main/kotlin")
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
        val packagePath = configDir.relativeTo(project.file("${project.projectDir}/src/main/kotlin"))
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
tasks.named("compileJava") {
    dependsOn("generateSecrets")
}
