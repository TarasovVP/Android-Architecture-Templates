# MVVM with Compose Navigation, Ktor, SQLDelight and Koin

A fully Compose-based Android app: single `Activity`, `NavHost` navigation, `StateFlow` state in ViewModels, Ktor for networking, SQLDelight for storage and Koin for dependency injection. This is the Android-only stack that was later moved to Kotlin Multiplatform.

## Overview

- **Purpose:** show an MVVM app without Fragments, LiveData, Hilt, Room or Retrofit, using libraries that also work in Kotlin Multiplatform.
- **Approach:** MVVM with `data` / `domain` / `presentation` packages, `StateFlow` instead of `LiveData`, Compose Navigation instead of the Fragment-based Navigation component.
- **Place in the evolution:** the bridge between the Android-only MVVM branches and the [MVI branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvi_compose_navigation_implementation) and the KMM/KMP branches.

## Functionality

1. `MainActivity` sets the Compose content to `AppNavigation()`.
2. The `list` destination (`ListScreen`) has a **Start** button: the app requests forks of `octocat/Spoon-Knife` through Ktor, inserts them into SQLDelight and shows the stored list.
3. Selecting an item navigates to `details/{demoObjectId}` (`DetailsScreen`), which loads the item from the database and shows it with the owner avatar (Coil `AsyncImage`). Back navigation uses `popBackStack()`.

## Platforms

Android only (`minSdk 24`, `compileSdk 34`, Java 17).

## Architecture

| Layer | Contents |
|-------|----------|
| Presentation | `AppNavigation` (`NavHost`, string routes), `ListScreen`/`DetailsScreen`, `ListViewModel`/`DetailsViewModel` exposing `StateFlow`s (progress, error, data) |
| Domain | models, repository / use-case / mapper interfaces |
| Data | `ApiService` (Ktor `HttpClient`) with `NetworkResult` wrapper, `DemoObjectDao(Impl)` over SQLDelight queries, repository and mapper implementations |
| DI | `appModule` (Koin DSL): `HttpClient` with ContentNegotiation + Logging, SQLDelight `AndroidSqliteDriver`, mappers, repositories, use case, `viewModel { … }` |

Data flows as `Flow`s from the repositories through the use case into the ViewModel, which writes to `MutableStateFlow`s that Composables observe.

## Technology stack

| Area | Technology |
|------|------------|
| UI | Jetpack Compose (BOM 2024.04.00, Material 2), single Activity |
| Networking | Ktor client 2.3.7 (Android engine) |
| Image loading | Coil Compose 2.2 |
| Serialization | kotlinx.serialization (Ktor `ContentNegotiation`); Gson annotations remain on some models |
| Database | SQLDelight 1.5.5 (`AppDatabase.sq`) |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | `StateFlow` |
| Navigation | `androidx.navigation:navigation-compose` 2.7.7 (`NavHost`) |
| Dependency injection | Koin 3.5 (`koin-android`, `koin-androidx-compose`) |
| Testing | Template `ExampleUnitTest` / `ExampleInstrumentedTest` only |
| Build | Gradle (Kotlin DSL), AGP 8.3.0, Kotlin 1.9.23 |

## Project structure

```
app/src/main/
├── java/com/vnteam/architecturetemplates/
│   ├── AppApplication.kt            # starts Koin
│   ├── di/AppModule.kt
│   ├── data/                        # network (Ktor), database (SQLDelight DAO), repositoryimpl, mapperimpls
│   ├── domain/                      # models, repositories, mappers, usecase
│   └── presentation/                # MainActivity, navigation/AppNavigation, list, details, uimodels, usecaseimpl
└── sqldelight/…/AppDatabase.sq
```

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation.

```bash
git clone --branch mvvm_compose_navigation_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :app:installDebug   # JDK 17, emulator/device required
```

## What this branch demonstrates

- Compose Navigation with a `NavHost` and route arguments.
- `StateFlow`-based MVVM without Fragments.
- A Ktor + SQLDelight + Koin stack that is portable to Kotlin Multiplatform.

## Related articles

- [SQLDelight in Kotlin Multiplatform: Steps to Implementation](https://medium.com/@vptarasov/sqldelight-in-kotlin-multiplatform-steps-to-implementation-bc930029de69) — related topic (the database library used here).
- [KMP: Network Requests with Ktor](https://medium.com/@vptarasov/kmp-network-requests-with-ktor-498d0a7b544e) — related topic (the networking library used here).
- [KMP: Dependency Injection with Koin](https://medium.com/@vptarasov/kmp-dependency-injection-with-koin-d1f442f87cee) — related topic (the DI library used here).

The articles are written for KMP; they are listed here by technology, not as walkthroughs of this branch.

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [MVVM + Compose](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_implementation)
- Next: [MVI + Compose Navigation](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvi_compose_navigation_implementation)
