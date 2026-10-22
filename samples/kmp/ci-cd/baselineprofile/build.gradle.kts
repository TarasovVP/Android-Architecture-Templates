import java.util.TreeSet

plugins {
    id(libs.plugins.androidTest.get().pluginId)
    kotlin("android")
    alias(libs.plugins.baselineprofile)
}

android {
    namespace = "com.vnteam.baselineprofile"
    compileSdk = libs.versions.compileSdk.get().toInt()

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] =
            "EMULATOR,DEBUGGABLE,LOW-BATTERY"
    }

    targetProjectPath = ":mobile"

}

baselineProfile {
    useConnectedDevices = true
}

dependencies {
    implementation(libs.androidx.junit)
    implementation(libs.androidx.espresso.core)
    implementation(libs.androidx.uiautomator)
    implementation(libs.androidx.benchmark.macro.junit4)
}

androidComponents {
    onVariants { v ->
        val artifactsLoader = v.artifacts.getBuiltArtifactsLoader()
        v.instrumentationRunnerArguments.put(
            "targetAppId",
            v.testedApks.map { artifactsLoader.load(it)?.applicationId }
        )
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.jvmVersion.get()))
    }
}


tasks.register("mergeNonMinifiedReleaseBaselineProfile") {

    group = "baseline-profile"
    description =
        "Merge *startup-prof*.txt into src/main/baseline-prof.txt"

    dependsOn("collectNonMinifiedReleaseBaselineProfile")

    doLast {
        val rawDir = file(
            "${layout.buildDirectory}/intermediates/baselineprofiles/nonMinifiedRelease"
        )
        val outFile = file("src/main/baseline-prof.txt")

        val lines = TreeSet<String>()
        rawDir
            .walkTopDown()
            .filter { it.name.endsWith(".txt") }
            .forEach { lines += it.readLines() }

        check(lines.isNotEmpty()) {
            "No raw profiles found in $rawDir"
        }

        outFile.parentFile.mkdirs()
        outFile.writeText(lines.joinToString("\n"))

        println("✅ baseline-prof.txt updated, lines: ${lines.size}")
    }
}

tasks.register("updateBaselineProfile") {
    group = "baseline-profile"
    description = "collect + merge → baseline-prof.txt"
    dependsOn("mergeNonMinifiedReleaseBaselineProfile")
}
