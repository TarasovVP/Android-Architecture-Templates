plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    compileOnly("com.android.tools.lint:lint-api:31.9.1")
    compileOnly("com.android.tools.lint:lint-checks:31.9.1")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
