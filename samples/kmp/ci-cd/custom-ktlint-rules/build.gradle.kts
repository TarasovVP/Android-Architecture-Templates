plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    runtimeOnly(libs.slf4j)
    implementation(libs.ktlint.rule.engine)
    implementation(libs.ktlint.ruleset.standard)
    implementation(libs.ktlint.test)
    implementation(libs.kotlin.test)
}
