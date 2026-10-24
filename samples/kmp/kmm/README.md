# KMM — Kotlin Multiplatform Mobile (Android + iOS)

The first multiplatform step: the MVI/Compose app from the Android branches is moved into a shared Kotlin module and reused on **Android and iOS**. Business logic, data layer, ViewModels and the Compose UI live in `shared/commonMain`; the platforms only provide an entry point and platform-specific drivers.

> **About the name.** *Kotlin Multiplatform Mobile (KMM)* was the name used by JetBrains for sharing code between Android and iOS. The brand was later dropped in favour of plain *Kotlin Multiplatform (KMP)*, which also covers desktop, web and server targets. This branch keeps the historical name; the broader target set arrives in the [KMP branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_implementation).

## Overview

- **Purpose:** show how little platform code remains when the architecture is already based on multiplatform-capable libraries (Ktor, SQLDelight, Koin, kotlinx.serialization, Compose Multiplatform).
- **Approach:** MVI (intents + view state + `StateFlow`) with `data` / `domain` / `presentation` packages in `shared`.
- **Place in the evolution:** follows the [MVI Compose branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvi_compose_navigation_implementation); precedes the full KMP project.

## Functionality

Same two-screen scenario as the Android branches: fetch the forks of `octocat/Spoon-Knife` from the GitHub API with Ktor, store them in SQLDelight, show the stored list, open a details screen with the owner avatar.

## Platforms

| Platform | Entry point |
|----------|-------------|
| Android | `app` module — `MainActivity` (`ComponentActivity`) and `AppApplication` |
| iOS | `iosApp` Xcode project — SwiftUI `ContentView` hosting the shared Compose UI; `MainViewController` in `shared/iosMain` |

Shared targets: `androidTarget`, `iosX64`, `iosArm64`, `iosSimulatorArm64`. There are no desktop or web targets in this branch.

## Architecture

| Layer | Location | Contents |
|-------|----------|----------|
| Presentation | `shared/commonMain/.../presentation` | Compose screens (`ListScreen`, `DetailsScreen`), `ListViewModel`/`DetailsViewModel`, intents and view states, `AppNavigation`, theme, resources |
| Domain | `shared/commonMain/.../domain` | models, repository interfaces, use cases, mappers |
| Data | `shared/commonMain/.../data` | Ktor `ApiService` + `NetworkResult`, SQLDelight DAO, repository implementations |
| Platform code | `androidMain`, `iosMain` | `DatabaseDriverFactory` (Android / Native SQLite drivers), Koin platform modules, iOS `MainViewController`/`KoinIOS` |

The `expect`/`actual` mechanism is used for the database driver factory (`DatabaseDriverFactory` in `commonMain` with `.android.kt` and `.ios.kt` implementations).

> The `app` module also contains a few leftover files from the earlier Android-only structure (`app/src/main/java/.../data|domain`). The shared module is the source of truth for the logic.

## Technology stack

| Area | Technology |
|------|------------|
| UI | Compose Multiplatform 1.6 (shared), SwiftUI shell on iOS |
| Networking | Ktor client 2.3 (Android / Darwin engines) |
| Image loading | Coil 3 (alpha) Compose + Ktor network |
| Serialization | kotlinx.serialization |
| Database | SQLDelight 1.5.5 |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | MVI with `StateFlow` |
| Navigation | JetBrains `navigation-compose` (multiplatform) |
| Dependency injection | Koin 3.5 |
| Testing | Template Android tests only |
| Build | Gradle (Kotlin DSL, version catalog), AGP 8.2, Kotlin 1.9.23 |

## Project structure

```
app/        Android application module (entry point)
shared/     Kotlin Multiplatform module: commonMain, androidMain, iosMain
iosApp/     Xcode project (SwiftUI wrapper)
gradle/libs.versions.toml
```

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation. iOS requires macOS with Xcode.

```bash
git clone --branch kmm_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :app:installDebug      # Android
open iosApp/architecturetemplates.xcodeproj   # iOS: run the scheme from Xcode
```

## What this branch demonstrates

- Moving UI, ViewModels and data code into `commonMain`.
- `expect`/`actual` for platform drivers.
- Hosting Compose Multiplatform inside a SwiftUI app.

## Related articles

- [SQLDelight in Kotlin Multiplatform: Steps to Implementation](https://medium.com/@vptarasov/sqldelight-in-kotlin-multiplatform-steps-to-implementation-bc930029de69) — related topic.
- [KMP: Network Requests with Ktor](https://medium.com/@vptarasov/kmp-network-requests-with-ktor-498d0a7b544e) — related topic.
- [KMP: Dependency Injection with Koin](https://medium.com/@vptarasov/kmp-dependency-injection-with-koin-d1f442f87cee) — related topic (links to the repository overview).
- [Kotlin Multiplatform — Now Supported by Google](https://medium.com/@vptarasov/kotlin-multiplatform-now-supported-by-google-2874dd2d69b7) — background.

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [MVI + Compose Navigation](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvi_compose_navigation_implementation)
- Next: [KMP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_implementation)
