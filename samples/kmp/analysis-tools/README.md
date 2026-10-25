# KMP with analysis tools

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `kmp-analysis-tools` |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVI, multi-module |
| UI | Compose Multiplatform + Compose HTML (web) |
| Platforms | Android, iOS, Desktop, Web, Server |
| Key technologies | DI: Koin; network: Ktor Client; storage: SQLDelight + DataStore; navigation: Navigation Compose |
| Gradle modules | `:core`, `:shared`, `:composeUI`, `:mobile`, `:desktop`, `:web`, `:server`, `:kmp-secrets-plugin`, `:lint-checks`, `:custom-ktlint-rules` |
| Tool versions | Gradle 8.11.1, AGP 8.9.2, Kotlin 2.1.20 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.kmp.analysistools` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
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

Or from the repository root: `./scripts/build-sample kmp-analysis-tools`, `./scripts/test-sample kmp-analysis-tools`,
`./scripts/run-sample kmp-analysis-tools <target>`.
- iOS: open `iosApp/` in Xcode (macOS only); it is not started from the command line.

## Environment variables

- `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (the server has built-in defaults; set them to point at your PostgreSQL)
- `SONAR_TOKEN` and the Sonar project settings, only for `./gradlew sonar` (see the CI workflow in `.github/workflows`)

## Secrets / `local.properties`

`local.properties` (sample root or module) with `CLOUD_URL=<server base url>`, read by the `kmp-secrets-plugin`. No real value is committed.

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- `generateSecrets*` tasks fail without a `local.properties` file (create one with `CLOUD_URL=...`).
- `./gradlew sonar` needs a Sonar token and project settings. The workflow stored in this sample's `.github/` is part of the example and is not run by GitHub.
- The remote build cache points to `http://localhost:5071/cache/` and logs a connection error unless a cache node is running (not changed in this sample).

# KMP with Code Analysis Tools

Adds a **code-quality pipeline** to the [custom plugins branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_custom_plugins): ktlint and Detekt reports, Kover coverage, a SonarQube/SonarCloud analysis task that aggregates them, and a GitHub Actions workflow that runs the chain on every push and pull request. Build-performance tooling (build cache, build scans, a gradle-profiler scenario file) is also introduced here.

## Overview

- **Purpose:** show how to measure and track the quality of a multi-module KMP code base — style, static analysis, test coverage — in one place.
- **Approach:** per-module tools configured from the root build; results exported as XML and consumed by Sonar.
- **Place in the evolution:** custom plugins → **analysis tools** → [CI/CD](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_ci_cd).
- The application is the same Ktor server + multiplatform clients project. Structurally, the shared Compose screens, components and theme are extracted from `shared` into a new **`composeUI`** module that `mobile`, `desktop` and `web` consume.

## Analysis tooling

| Tool | Role | Where configured |
|------|------|------------------|
| ktlint | Code style (Gradle plugin 12.2.0) with the project's [custom rules](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_custom_plugins) from `:custom-ktlint-rules` | root `build.gradle.kts` (`subprojects { … }`), `.editorconfig` |
| Detekt | Static analysis (1.23.8), project config in `detekt.yml` on top of the defaults; `ignoreFailures = true` so findings are reported without failing the build | root `build.gradle.kts`, `detekt.yml` |
| Kover | Test coverage (0.9.1); XML reports per module; exclusions for composables, DI, resources, generated code in `shared` | root and `shared/build.gradle.kts` |
| SonarQube plugin | `org.sonarqube` 6.1.0 — `sonar` task depends on every module's `koverXmlReport` and on `detekt`; passes `build/reports/kover/report.xml` as coverage and `build/reports/detekt/detekt.xml` as external issues | root `build.gradle.kts` (`sonarqube { … }`) |
| Pre-commit hook | Formats, checks and runs Detekt on staged Kotlin files (`installGitHook`) | `pre-commit` |
| Custom tooling | `kmp-secrets-plugin`, `custom-ktlint-rules`, `lint-checks` (see the previous branch) | modules of the same names |

### Build performance tooling

- Gradle build cache enabled (`org.gradle.caching=true`); `settings.gradle.kts` also declares a remote HTTP build cache on `localhost` (push enabled, **disabled by default**) and the `com.gradle.enterprise` plugin with build scans.
- `benchmark.scenarios` — gradle-profiler scenarios (`clean` and `incremental` `assembleDebug`) for measuring build time.

### GitHub Actions: `.github/workflows/sonar.yml`

Triggered on pushes to `main` / `kmp_with_analyze_tools` and on pull requests. It runs on `ubuntu-latest` with JDK 17 and:

1. restores Sonar and Gradle caches;
2. creates a `local.properties` for the secrets plugin;
3. `./gradlew detekt --continue` (non-blocking);
4. `./gradlew koverXmlReport`;
5. `./gradlew sonar` with the project key, organisation, host and token taken from repository secrets (`-x assembleDebug -x packageDebug -x validateSigningDebug`).

Unit tests, benchmarks, release builds and deployment are **not** part of this workflow; see the [CI/CD branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_ci_cd) for those.

## Platforms

Android, iOS (`mobile`), Desktop (`desktop`), Web (Kotlin/JS, `web`) and a JVM `server`.

## Project structure

```
core/  shared/  composeUI/        libraries (models · logic · shared Compose UI)
mobile/  desktop/  web/  server/  applications
kmp-secrets-plugin/  custom-ktlint-rules/  lint-checks/   custom tooling
detekt.yml  .editorconfig  pre-commit  benchmark.scenarios  .github/workflows/sonar.yml
```

## Technology stack

| Area | Technology |
|------|------------|
| UI | Compose Multiplatform, Compose HTML (web) |
| Networking | Ktor 2.3.12 |
| Serialization | kotlinx.serialization |
| Database | SQLDelight 2.0.2 |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | MVI with `StateFlow` |
| Dependency injection | Koin 4.0 |
| Testing | `kotlin.test`, `kotlinx-coroutines-test`, `koin-test` (tests in `shared/commonTest`) |
| Code quality | ktlint, Detekt, Kover, SonarQube/SonarCloud |
| CI/CD | GitHub Actions (analysis only) |
| Build | Gradle 8.11.1, AGP 8.9.2, Kotlin 2.1.20, build cache + build scans |

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation. Running `sonar` needs a Sonar server/organisation and a token.

```bash
git clone --branch kmp_with_analyze_tools https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
# create local.properties (sdk.dir + keys read by the secrets plugin, e.g. CLOUD_URL)

./gradlew ktlintCheck
./gradlew detekt                       # report: <module>/build/reports/detekt/
./gradlew koverXmlReport               # report: <module>/build/reports/kover/report.xml
./gradlew sonar \
  -Dsonar.projectKey=<key> -Dsonar.organization=<org> \
  -Dsonar.host.url=<url> -Dsonar.token=<token>
```

## What this branch demonstrates

- Combining ktlint, Detekt and Kover results in a single SonarQube view for a KMP project.
- Registering the Sonar plugin globally so that `sonar` depends on the report tasks.
- Extracting shared UI into its own module.
- Using build cache and build scans to keep a multi-module build fast.

## Related articles

- [Code Quality Control in KMP with SonarQube](https://medium.com/@vptarasov/code-quality-control-in-kmp-with-sonarqube-1237ec4f3a4b) — SonarQube, Kover and Detekt in a KMP project.
- [Ktlint in KMP: Setup, Configuration, and Custom Rules](https://medium.com/@vptarasov/ktlint-in-kmp-setup-configuration-and-custom-rules-4b9f7ab86347) — ktlint setup and custom rules.

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [Custom plugins](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_custom_plugins)
- Next: [CI/CD](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_ci_cd)
