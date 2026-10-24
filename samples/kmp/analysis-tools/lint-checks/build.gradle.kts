plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    compileOnly(libs.lint.api)
    compileOnly(libs.lint.checks)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
