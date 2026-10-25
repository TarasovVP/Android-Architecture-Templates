# KMP with unit tests

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `kmp-tests` |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVI, multi-module |
| UI | Compose Multiplatform + Compose HTML (web) |
| Platforms | Android, iOS, Desktop, Web, Server |
| Key technologies | DI: Koin; network: Ktor Client; storage: SQLDelight + DataStore; navigation: Navigation Compose |
| Gradle modules | `:core`, `:shared`, `:mobile`, `:desktop`, `:web`, `:server`, `:secrets-plugin` |
| Tool versions | Gradle 8.7, AGP 8.5.2, Kotlin 2.1.10 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.kmp.tests` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | [Unit Testing in Kotlin Multiplatform](https://medium.com/@vptarasov/unit-testing-in-kotlin-multiplatform-fb3ca7d5c869) |

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

Or from the repository root: `./scripts/build-sample kmp-tests`, `./scripts/test-sample kmp-tests`,
`./scripts/run-sample kmp-tests <target>`.
- iOS: open `iosApp/` in Xcode (macOS only); it is not started from the command line.

## Environment variables

- `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (the server has built-in defaults; set them to point at your PostgreSQL)

## Secrets / `local.properties`

`local.properties` in the sample root is required (the `generateSecrets` task fails without it). Used key: `CLOUD_URL=<server base url>`.

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- `:shared:generateSecrets` fails without a `local.properties` file in the sample root (create one with `CLOUD_URL=...`).
- Running `:server:run` needs PostgreSQL.

# KMP with Unit Tests

Adds an automated test suite to the [multi-module KMP project](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_multimodules_implementation). Tests are written once in `shared/commonTest` with `kotlin.test`, Koin test support and hand-written fakes, and run on every target that supports unit tests. Code coverage is measured with Kover.

## Overview

- **Purpose:** show how to unit-test shared Kotlin Multiplatform code — repositories, use cases and ViewModels — without platform frameworks or mocking libraries.
- **Approach:** fakes + dependency injection. Production Koin modules are overridden in tests (`BaseKoinTest`, `TestModule`) so each layer is tested against fake collaborators.
- **Place in the evolution:** multi-module → **tests** → [custom plugins](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_custom_plugins) → [analysis tools](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_analyze_tools) → [CI/CD](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_ci_cd).

## Functionality

Application behaviour is unchanged from the multi-module branch (Ktor server + multiplatform clients: list, details, create/edit, delete, theme and language). This branch adds tests and a first, in-repository **secrets plugin** (`secrets-plugin` module) that generates a `Secrets` Kotlin object from `local.properties` so that server URLs and database settings are not committed.

## Platforms

Same as the multi-module branch: Android, iOS, Desktop, Web (Kotlin/JS) and the JVM server. `shared` declares `jvm`, `js` (with an explicit Node.js test run), Android and iOS targets; `commonTest` is compiled for all of them. Which targets are actually exercised on your machine depends on the host OS (iOS tests need macOS).

## Tests

All meaningful tests live in `shared/src/commonTest`:

| Type | Location | What is covered |
|------|----------|-----------------|
| Repository tests | `data/repositoryimpl` | `ApiRepositoryTest`, `DBRepositoryTest`, `PreferencesRepositoryTest` against fake API service, fake DAO and fake preferences factory |
| Use-case tests | `domain` | create, delete, get one, get from API, get from DB, insert, clear demo objects; dark-theme and language use cases |
| ViewModel tests | `presentation/viewmodels` | `ListViewModel`, `DetailsViewModel`, `CreateViewModel`, `AppViewModel` with fake use cases (`BaseViewModelTest` configures coroutine test dispatchers and Koin) |
| Fakes and fixtures | `fake/…`, `Utils.kt`, `di/TestModule.kt` | `FakeApiService`, `FakeDemoObjectDao`, `FakePreferencesFactory`, fake repositories and use cases, `FakeModels` |

`mobile`, `desktop` and `web` contain only the generated `ExampleUnitTest` (and `ExampleInstrumentedTest` in `mobile/androidTest`) placeholders. There are no UI tests and no server tests in this branch (a `ktor-server-tests` dependency is declared but unused).

Test libraries (from the version catalog): `kotlin-test`, `kotlinx-coroutines-test`, `koin-test`, Compose UI test manifest/JUnit4 for the Android instrumented variant.

## Code coverage

The `kotlinKover` plugin is applied to `shared`. The `kover { reports { filters { excludes … } } }` block excludes `@Composable` functions, generated SQLDelight classes, DI modules, resources, theme/components and platform factories so that the figure reflects logic code only.

## Technology stack

| Area | Technology |
|------|------------|
| UI | Compose Multiplatform, Compose HTML |
| Networking | Ktor 2.3.12 (client and server) |
| Serialization | kotlinx.serialization |
| Database | SQLDelight 2.0.2 |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | MVI with `StateFlow` |
| Dependency injection | Koin 4.0 |
| Testing | `kotlin.test`, `kotlinx-coroutines-test`, `koin-test`, hand-written fakes |
| Code coverage | Kover 0.9.1 |
| Secrets | In-repo `secrets-plugin` Gradle module (`generateSecrets` task, hooked to `preBuild`) and an equivalent `generateSecrets` task in `shared/build.gradle.kts` |
| Build | Gradle 8.7, AGP 8.5.2, Kotlin 2.1.10 |

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation.

1. Create `local.properties` in the repository root. Besides `sdk.dir`, the secrets task expects the keys used by the code (for example `CLOUD_URL` for the clients). Without the file `generateSecrets` fails with *"local.properties file not found!"*.
2. Run the tests:

```bash
git clone --branch kmp_with_tests https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :shared:jvmTest            # fastest: JVM
./gradlew :shared:allTests           # all configured targets (iOS requires macOS)
./gradlew :shared:koverHtmlReport    # coverage report, build/reports/kover/html
```

## What this branch demonstrates

- Testing business logic in `commonTest` once for all platforms.
- Fakes over mocks, with Koin modules swapped in tests.
- Coroutine and `StateFlow` testing with `runTest`.
- Measuring meaningful coverage by excluding UI and generated code.

## Related article

- [Unit Testing in Kotlin Multiplatform](https://medium.com/@vptarasov/unit-testing-in-kotlin-multiplatform-fb3ca7d5c869) — organising unit tests in the shared module with Koin and fakes.

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [Multi-module KMP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_multimodules_implementation)
- Next: [Custom plugins](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_custom_plugins)
