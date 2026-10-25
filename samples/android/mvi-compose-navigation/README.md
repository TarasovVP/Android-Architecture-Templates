# Android MVI + Compose Navigation

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `android-mvi-compose-navigation` |
| Source branch | `mvi_compose_navigation_implementation` (commit `0dff2a85`) |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVI |
| UI | Jetpack Compose |
| Platforms | Android |
| Key technologies | DI: Koin; network: Ktor Client; storage: SQLDelight; navigation: Voyager |
| Gradle modules | `:app` |
| Tool versions | Gradle 8.4, AGP 8.3.0, Kotlin 1.9.23 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.mvi.compose.navigation` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | none |

## Build, test, run

From this directory:

```sh
./gradlew assembleDebug    # build
./gradlew testDebugUnitTest    # test
./gradlew :app:installDebug    # android
```

Or from the repository root: `./scripts/build-sample android-mvi-compose-navigation`, `./scripts/test-sample android-mvi-compose-navigation`,
`./scripts/run-sample android-mvi-compose-navigation <target>`.

## Environment variables

- none

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- None recorded.

The original README of the source branch follows unchanged.

<!-- catalog:original-readme -->

# MVI with Compose and Voyager Navigation

The Compose-based Android implementation reworked to **Model-View-Intent**: each screen has an immutable view state, a sealed set of intents and a ViewModel that reduces intents into new states exposed as a `StateFlow`. Navigation uses the Voyager library instead of `NavHost`.

## Overview

- **Purpose:** show unidirectional data flow (UDF) on top of the Ktor / SQLDelight / Koin stack.
- **Approach:** MVI per screen — `ListIntent`/`ListViewState`/`ListViewModel` and `DetailsIntent`/`DetailsViewState`/`DetailsViewModel`.
- **Difference from [MVVM + Compose Navigation](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_navigation_implementation):** several `StateFlow`s per ViewModel are replaced with one state object per screen; user actions go through `processIntent(...)`; navigation moves from `NavHost` string routes to Voyager `Screen` classes.

## Functionality

1. `MainActivity` renders `Navigator(NavigationScreen.ListContentScreen())`.
2. The list screen requests forks of `octocat/Spoon-Knife` through Ktor, stores them in SQLDelight and renders the stored list from `ListViewState`.
3. Selecting an item pushes `NavigationScreen.DetailsContentScreen(demoObjectId)`; the details screen loads the item and shows it with the owner avatar (Coil).

## Platforms

Android only (`minSdk 24`, `compileSdk 34`, Java 17).

## Architecture

```
View (Composable) ── Intent ──►  ViewModel ── new State ──►  View
        ▲                           │
        └──────── StateFlow ◄───────┘
                                    └─► use case ─► repositories ─► Ktor / SQLDelight
```

| Piece | Example |
|-------|---------|
| State | `ListViewState(demoObjects, isLoading, error)` — immutable data class |
| Intent | `ListIntent` / `DetailsIntent` sealed classes |
| ViewModel | `ListViewModel.processIntent(intent)`; state updates via `_state.value = state.value.copy(...)` |
| Navigation | `NavigationScreen` sealed class implementing Voyager `Screen` |
| Domain / Data | same layering as the MVVM Compose branch (models, use case, repositories, mappers, Ktor, SQLDelight) |

Observation: in the current code the intent set is minimal (e.g. a single `LoadDemoObjects` intent on the list screen), so the pattern is demonstrated in its basic form.

## Technology stack

| Area | Technology |
|------|------------|
| UI | Jetpack Compose (BOM 2024.04.00, Material 2) |
| Networking | Ktor client 2.3.7 |
| Image loading | Coil Compose 2.2 |
| Serialization | kotlinx.serialization |
| Database | SQLDelight 1.5.5 |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | MVI: `StateFlow<ViewState>` + intents |
| Navigation | Voyager 1.0.0 (`voyager-navigator`, `-transitions`, `-koin`) |
| Dependency injection | Koin 3.5 |
| Testing | Template `ExampleUnitTest` / `ExampleInstrumentedTest` only |
| Build | Gradle (Kotlin DSL), AGP 8.3.0, Kotlin 1.9.23 |

## Project structure

```
app/src/main/java/com/vnteam/architecturetemplates/
├── di/AppModule.kt · data/ · domain/
└── presentation/
    ├── MainActivity.kt
    ├── navigation/NavigationScreen.kt
    ├── list/     ListScreen, ListViewModel, ListIntent, ListViewState
    └── details/  DetailsScreen, DetailsViewModel, DetailsIntent, DetailsViewState
```

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation.

```bash
git clone --branch mvi_compose_navigation_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :app:installDebug   # JDK 17, emulator/device required
```

## What this branch demonstrates

- Single immutable state per screen and explicit intents.
- Voyager as an alternative to Jetpack Navigation (and a step towards multiplatform navigation).
- The same domain/data code reused unchanged from the MVVM branch, with only the presentation layer re-shaped.

## Related articles

No dedicated article. The shared stack is described in [KMP: Network Requests with Ktor](https://medium.com/@vptarasov/kmp-network-requests-with-ktor-498d0a7b544e) and [KMP: Dependency Injection with Koin](https://medium.com/@vptarasov/kmp-dependency-injection-with-koin-d1f442f87cee) (related topics, written for KMP).

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [MVVM + Compose Navigation](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_navigation_implementation)
- Next: [KMM](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmm_implementation), [KMP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_implementation)
