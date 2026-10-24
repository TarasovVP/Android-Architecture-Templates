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
