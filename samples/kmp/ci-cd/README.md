# KMP with CI/CD

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `kmp-ci-cd` |
| Status | Latest - the most current and complete state. |
| Architecture | MVI, multi-module |
| UI | Compose Multiplatform + Compose HTML (web) |
| Platforms | Android, iOS, Desktop, Web, Server |
| Key technologies | DI: Koin; network: Ktor Client; storage: SQLDelight + DataStore; navigation: Navigation Compose |
| Gradle modules | `:core`, `:shared`, `:composeUI`, `:mobile`, `:desktop`, `:web`, `:server`, `:kmp-secrets-plugin`, `:lint-checks`, `:custom-ktlint-rules`, `:benchmark`, `:baselineprofile` |
| Tool versions | Gradle 8.11.1, AGP 8.9.2, Kotlin 2.2.0 |
| JDK | 21 |
| Android SDK | compileSdk 36, targetSdk 35 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.kmp.cicd` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | [Code Quality Control in KMP with SonarQube](https://medium.com/@vptarasov/code-quality-control-in-kmp-with-sonarqube-1237ec4f3a4b) |

## Build, test, run

From this directory:

```sh
./gradlew :mobile:assembleDebug    # build
./gradlew :shared:jvmTest    # test
./gradlew :mobile:installDebug    # android
./gradlew :desktop:run    # desktop
./gradlew :web:jsBrowserDevelopmentRun    # web
./gradlew :server:run    # server
```

Or from the repository root: `./scripts/build-sample kmp-ci-cd`, `./scripts/test-sample kmp-ci-cd`,
`./scripts/run-sample kmp-ci-cd <target>`.
- iOS: open `iosApp/` in Xcode (macOS only); it is not started from the command line.

## Environment variables

- `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (the server has built-in defaults; set them to point at your PostgreSQL)
- `STORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD` (release signing; also accepted from `mobile/local.properties` together with `STORE_FILE`). They are read while the `:mobile` project is configured, so even a debug build needs some value set (any placeholder works for debug builds)
- `SONAR_TOKEN` and the Sonar project settings, only for `./gradlew sonar` (see the CI workflow in `.github/workflows`)

## Secrets / `local.properties`

`local.properties` (sample root or module) with `CLOUD_URL=<server base url>`, read by the `kmp-secrets-plugin`. No real value is committed.

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- Needs JDK 21. `STORE_PASSWORD`, `KEY_ALIAS` and `KEY_PASSWORD` must be set (any placeholder is enough for debug builds) because release signing is configured while the project is configured.
- `generateSecrets*` tasks need a `local.properties` file with `CLOUD_URL=...`.
- `:shared:jvmTest`: 57 of 58 tests pass offline; `DeleteDemoObjectUseCaseTest.testDeleteDemoObject` calls the real server behind `CLOUD_URL` and fails with `ConnectException` when it is unreachable.
- Jenkins, Fastlane, Sonar and baseline-profile generation need external infrastructure and were not run.
- Remote build cache is opt-in here (`-PremoteCache`).

# KMP with CI/CD, Benchmarks and Baseline Profiles

The most complete stage of the KMP line. On top of the [analysis tools branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_analyze_tools) it adds **two CI/CD pipelines** (GitHub Actions and Jenkins + Fastlane), **performance measurement** (kotlinx.benchmark micro-benchmarks, Macrobenchmark startup tests, Baseline Profiles) and **dependency analysis**. The application and module layout are the same as before; the toolchain is newer (Kotlin 2.2.0, Compose Multiplatform 1.9.0, Ktor 3.2.3).

## Overview

- **Purpose:** show an end-to-end delivery setup for a KMP project: quality gates, tests, performance regression checks and a release artefact.
- **Approach:** the pipelines are described as code in the repository (`.github/workflows/sonar.yml`, `Jenkinsfile`, `fastlane/Fastfile`, `jenkins/`).
- **Place in the evolution:** analysis tools → **CI/CD**. The current `master` branch has the same content as this branch.

## Pipelines

### GitHub Actions — `.github/workflows/sonar.yml` (workflow "Build")

Runs on `macos-13` with JDK 21 (Temurin) and the Android SDK. Triggers: pushes to `main` and `kmp_with_analyze_tools`, and pull requests (`opened`, `synchronize`, `reopened`).

| # | Stage | Command / action |
|---|-------|------------------|
| 1 | Setup | checkout, JDK 21, Android SDK + licences, Sonar and Gradle caches |
| 2 | Configuration | writes `local.properties` (`CLOUD_URL`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD`) and decodes the Android keystore from repository secrets |
| 3 | Static analysis | `./gradlew detekt --continue` (failures do not stop the job) |
| 4 | Tests | `./gradlew allTests` — all KMP test tasks available on the runner |
| 5 | Coverage | `./gradlew koverXmlReport` |
| 6 | Micro-benchmarks | `./gradlew :shared:jvmBenchmark` (kotlinx.benchmark, JSON report uploaded as an artefact) |
| 7 | Regression check (PRs) | compares the report with the stored `main` baseline and fails when a benchmark deviates by more than 10 % |
| 8 | Baseline profile | Android emulator (API 33) runs `:baselineprofile:updateBaselineProfile`; the updated `baseline-prof.txt` is committed by `github-actions[bot]` |
| 9 | Sonar | `./gradlew sonar` using Detekt and Kover reports |

Notes to verify against the intended behaviour: the trigger filter names `main` / `kmp_with_analyze_tools`, while the baseline-promotion and "commit & push" steps check for `main`, `master` and `kmp_with_ci_cd`; the repository's default branch is `master`.

### Jenkins + Fastlane — `Jenkinsfile`

Declarative pipeline for building a release Android App Bundle:

1. **Checkout** the repository.
2. **Create `local.properties`** (`shared/local.properties`) from Jenkins credentials.
3. **Prepare signing** — keystore file and passwords from credentials into `mobile/`.
4. **Dependencies** — `./gradlew dependencies`.
5. **Ruby & Fastlane** — `bundle install` (`Gemfile` declares `fastlane`).
6. **Build AAB** — `bundle exec fastlane buildRelease` (`fastlane/Fastfile`: Gradle `bundle` task, `Release`).
7. **Archive** `**/build/outputs/**/*.aab`.

`jenkins/Dockerfile` (based on `jenkins/jenkins:lts-jdk21`, with Android command-line tools, platform 34, build-tools 35.0.0, Ruby and Fastlane) and `jenkins/docker-compose.yml` start a local Jenkins on ports 8080 / 50000 with a persistent volume.

## Performance and build tooling

| Component | Purpose |
|-----------|---------|
| `shared` micro-benchmarks | kotlinx.benchmark + allopen plugin; JVM benchmark task used in CI |
| `benchmark` module | Android Macrobenchmark (`StartupBenchmarks`): cold start without compilation vs. with baseline profiles, `StartupTimingMetric`, 10 iterations |
| `baselineprofile` module | `BaselineProfileGenerator` (startup profile) targeting `:mobile`; the profile is consumed by `mobile` (`baselineProfile(project(":baselineprofile"))`, `profileinstaller`) |
| Dependency analysis | `com.autonomousapps.dependency-analysis` applied to subprojects |
| Build cache and scans | `org.gradle.caching=true`, optional remote HTTP cache (disabled by default), `com.gradle.enterprise` build scans |
| `benchmark.scenarios` | gradle-profiler scenarios for clean/incremental `assembleDebug` |
| Quality tooling | ktlint (with custom rules), Detekt, Kover, SonarQube, pre-commit hook — as in the previous branches |

## Platforms

Android, iOS (`mobile`), Desktop (`desktop`), Web (Kotlin/JS, `web`) and a JVM `server`. Pipelines only produce an **Android** release bundle; iOS, desktop, web and server artefacts are not built by Jenkins, and the GitHub workflow runs tests, analysis and benchmarks rather than packaging.

## Technology stack

| Area | Technology |
|------|------------|
| UI | Compose Multiplatform 1.9.0, Compose HTML (web) |
| Networking | Ktor 3.2.3 (client and server) |
| Serialization | kotlinx.serialization 1.9.0 |
| Database | SQLDelight 2.0.2 |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | MVI with `StateFlow` |
| Dependency injection | Koin 4.0 |
| Testing | `kotlin.test`, `kotlinx-coroutines-test`, `koin-test`; Macrobenchmark / Baseline Profile instrumented tests |
| Code quality | ktlint, Detekt, Kover, SonarQube, dependency-analysis |
| CI/CD | GitHub Actions, Jenkins, Fastlane |
| Build | Gradle 8.11.1, AGP 8.9.2, Kotlin 2.2.0, `compileSdk 36` |

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation. Pipelines additionally need repository / Jenkins secrets (Sonar token, keystore, `CLOUD_URL`, database settings).

```bash
git clone --branch kmp_with_ci_cd https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
# create local.properties (sdk.dir + CLOUD_URL etc.)

./gradlew allTests detekt koverXmlReport     # what the workflow runs, minus Sonar
./gradlew :shared:jvmBenchmark               # micro-benchmarks
./gradlew :baselineprofile:updateBaselineProfile   # needs an emulator/device
bundle install && bundle exec fastlane buildRelease   # needs signing values in mobile/local.properties and a keystore

docker compose -f jenkins/docker-compose.yml up --build   # local Jenkins on :8080
```

## What this branch demonstrates

- A reproducible CI pipeline with analysis, tests, coverage, benchmarks and a regression gate.
- Release automation with Jenkins and Fastlane, with secrets injected from the CI system.
- Startup optimisation with Baseline Profiles and Macrobenchmark.
- How all tooling from earlier branches comes together in one multi-module KMP project.

## Related articles

- [Code Quality Control in KMP with SonarQube](https://medium.com/@vptarasov/code-quality-control-in-kmp-with-sonarqube-1237ec4f3a4b) — the analysis part of the workflow.
- [Automating Work with Secrets in Kotlin Multiplatform](https://medium.com/@vptarasov/automating-work-with-secrets-in-kotlin-multiplatform-a2b4c587180b) — how the pipelines supply `local.properties` values.
- No article covers Jenkins / Fastlane, benchmarks or baseline profiles yet.

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [Analysis tools](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_analyze_tools)
- Earlier in the line: [Custom plugins](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_custom_plugins), [Tests](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_tests)
