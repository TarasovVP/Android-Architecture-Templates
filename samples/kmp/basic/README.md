# KMP (Android, iOS, Desktop, Web)

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `kmp-basic` |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVI |
| UI | Compose Multiplatform + Compose HTML |
| Platforms | Android, iOS, Desktop, Web |
| Key technologies | DI: Koin; network: Ktor Client; storage: SQLDelight + DataStore; navigation: Navigation Compose |
| Gradle modules | `:composeApp`, `:shared`, `:webApp` |
| Tool versions | Gradle 8.4, AGP 8.3.2, Kotlin 2.0.20 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.kmp.basic` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | [KMP: Dependency Injection with Koin](https://medium.com/@vptarasov/kmp-dependency-injection-with-koin-d1f442f87cee) |

## Build, test, run

From this directory:

```sh
./gradlew :composeApp:assembleDebug    # build
./gradlew :shared:testDebugUnitTest    # test
./gradlew :composeApp:installDebug    # android
./gradlew :composeApp:run    # desktop
./gradlew :composeApp:jsBrowserDevelopmentRun    # web
```

Or from the repository root: `./scripts/build-sample kmp-basic`, `./scripts/test-sample kmp-basic`,
`./scripts/run-sample kmp-basic <target>`.
- iOS: open `iosApp/` in Xcode (macOS only); it is not started from the command line.

## Environment variables

- none

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- iOS is only buildable on macOS with Xcode.

# KMP — Android, iOS, Desktop and Web

The full Kotlin Multiplatform version of the feature: one shared codebase (domain, data, ViewModels, Compose UI) running on **Android, iOS, Desktop (JVM) and Web (Kotlin/JS)**. It extends the [KMM branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmm_implementation) with desktop and web targets, user preferences and platform-specific entry points.

## Overview

- **Purpose:** demonstrate Kotlin Multiplatform beyond mobile — the same logic and UI on four platform families.
- **Approach:** MVI (intents + view states + `StateFlow`) in the `shared` module; UI in `composeApp` (Compose Multiplatform) plus a separate DOM-based web UI in `webApp`.
- **Place in the evolution:** KMM → **KMP** → [KMP with its own server](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_server_implementation).

## Functionality

Fetch the forks of `octocat/Spoon-Knife` from the GitHub API with Ktor, cache them in SQLDelight, show the list, open a details screen. Additional features compared with KMM: a splash screen, dark-theme and language settings persisted with DataStore preferences, and a *share link* action implemented per platform.

## Platforms

| Platform | Module / target | UI |
|----------|-----------------|----|
| Android | `composeApp` (`androidTarget`) | Compose Multiplatform |
| iOS | `composeApp` iOS framework (`iosX64`, `iosArm64`, `iosSimulatorArm64`) + `iosApp` Xcode project | Compose Multiplatform |
| Desktop (JVM) | `composeApp` (`jvm("desktop")`) | Compose Multiplatform |
| Web (Kotlin/JS) | `composeApp` (`js(IR)`, canvas via Skiko/Wasm runtime) and `webApp` (`js(IR)`, Compose HTML/DOM) | Compose canvas / Compose HTML |

Note on "Wasm": there is **no `wasmJs` Gradle target** in this branch. The browser build of `composeApp` is a Kotlin/JS target that uses the Skiko WebAssembly runtime (`onWasmReady { CanvasBasedWindow(...) }`).

## Architecture

| Module | Responsibility |
|--------|----------------|
| `shared` | `commonMain`: domain (models, repositories, use cases `AppUseCase`/`ListUseCase`/`DetailsUseCase`), data (Ktor `ApiService`, SQLDelight DAO, `Preferences`), presentation (ViewModels, intents, view states, UI models, string/dimension resources), Koin `AppModule`. Platform source sets provide database drivers, preference factories and DI entry points. |
| `composeApp` | Compose Multiplatform screens (`App`, `AppNavigation`, `ListScreen`, `DetailsScreen`, theme), Android `MainActivity`/`AppApplication`, desktop `main.kt`, iOS `MainViewController`, JS `main.kt` |
| `webApp` | DOM-based web UI with Compose HTML (`renderComposable`) and its own `ViewModelStoreOwner` |
| `iosApp` | SwiftUI host application |

`expect`/`actual` is used for `DatabaseDriverFactory`, `PreferencesFactory` and the `shareLink` function.

The `server/` and `app/` directories in this branch contain only leftover files (build output and old iOS sources) and are **not** part of `settings.gradle.kts` (`:composeApp`, `:shared`, `:webApp`).

## Technology stack

| Area | Technology |
|------|------------|
| UI | Compose Multiplatform 1.6.10 (Material 3), Compose HTML (web DOM) |
| Networking | Ktor client 2.3.12 (Android, Darwin, Java, JS engines) |
| Image loading | Coil 3 (alpha) Compose + Ktor network |
| Serialization | kotlinx.serialization |
| Database | SQLDelight 2.0.2 (Android/Native/JVM/web-worker JS drivers) |
| Preferences | AndroidX DataStore preferences |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | MVI with `StateFlow` |
| Navigation | JetBrains `navigation-compose` (Compose targets); URL-based routing in the DOM web app |
| Dependency injection | Koin 3.6 |
| Testing | Template Android tests only |
| Build | Gradle 8.4 (Kotlin DSL, version catalog), AGP 8.3.2, Kotlin 2.0.20 |

## Project structure

```
composeApp/  Compose Multiplatform UI + app entry points (android, desktop, ios, js)
shared/      commonMain + androidMain / iosMain / jvmMain / jsMain
webApp/      Compose HTML web application
iosApp/      SwiftUI host
gradle/libs.versions.toml
```

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation. JDK 17+ is required; iOS needs macOS and Xcode.

```bash
git clone --branch kmp_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :composeApp:installDebug            # Android
./gradlew :composeApp:run                     # Desktop (Compose desktop application)
./gradlew :composeApp:jsBrowserDevelopmentRun # Web, canvas UI
./gradlew :webApp:jsBrowserDevelopmentRun     # Web, Compose HTML UI
open iosApp/architecturetemplates.xcodeproj   # iOS
```

## What this branch demonstrates

- One codebase for mobile, desktop and web.
- `expect`/`actual` for databases, preferences and platform actions.
- Two different web strategies: canvas rendering of Compose and DOM rendering with Compose HTML.
- SQLDelight 2 with an asynchronous driver (`generateAsync`) so that the same schema works in the browser.

## Related articles

- [KMP: Network Requests with Ktor](https://medium.com/@vptarasov/kmp-network-requests-with-ktor-498d0a7b544e) — related topic.
- [KMP: Dependency Injection with Koin](https://medium.com/@vptarasov/kmp-dependency-injection-with-koin-d1f442f87cee) — related topic (links to the repository overview).
- [SQLDelight in Kotlin Multiplatform: Steps to Implementation](https://medium.com/@vptarasov/sqldelight-in-kotlin-multiplatform-steps-to-implementation-bc930029de69) — related topic.
- [Kotlin Multiplatform — Now Supported by Google](https://medium.com/@vptarasov/kotlin-multiplatform-now-supported-by-google-2874dd2d69b7) — background.

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [KMM](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmm_implementation)
- Next: [KMP with server](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_server_implementation)
