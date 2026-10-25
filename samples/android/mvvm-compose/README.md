# Android MVVM + Compose

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `android-mvvm-compose` |
| Source branch | `mvvm_compose_implementation` (commit `609620a1`) |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVVM |
| UI | Jetpack Compose in Fragments |
| Platforms | Android |
| Key technologies | DI: Hilt; network: Retrofit + OkHttp; storage: Room; navigation: Navigation component |
| Gradle modules | `:app` |
| Tool versions | Gradle 8.4, AGP 8.3.0, Kotlin 1.9.23 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.mvvm.compose` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | none |

## Build, test, run

From this directory:

```sh
./gradlew assembleDebug    # build
./gradlew testDebugUnitTest    # test
./gradlew :app:installDebug    # android
```

Or from the repository root: `./scripts/build-sample android-mvvm-compose`, `./scripts/test-sample android-mvvm-compose`,
`./scripts/run-sample android-mvvm-compose <target>`.

## Environment variables

- none

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- None recorded.

The original README of the source branch follows unchanged.

<!-- catalog:original-readme -->

# MVVM with Jetpack Compose (Fragment-hosted)

The MVVM + Hilt + Room + Retrofit implementation with the UI rewritten in **Jetpack Compose**. Screens are `@Composable` functions, but they are still hosted by Fragments and navigated with the XML Navigation component — a migration-style setup.

## Overview

- **Purpose:** show the step from View-based UI to Compose without changing the rest of the architecture.
- **Approach:** the layered MVVM from the [MVVM branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_implementation) is kept; only the presentation layer changes.
- **Place in the evolution:** between the View-based MVVM branches and the fully Compose-based [navigation branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_navigation_implementation).

## Functionality

1. `MainActivity` hosts the Navigation graph (`navigation.xml`) with `ListFragment` and `DetailsFragment`.
2. Each fragment returns a `ComposeView` whose content is `ListScreen` / `DetailsScreen`.
3. **Start** loads forks of `octocat/Spoon-Knife` through Retrofit, stores them in Room and shows the stored list in a `LazyColumn` of cards.
4. Clicking an item navigates through the `NavController` (Safe Args, `demoObjectId`) to the details screen, which shows the item and the owner avatar (Coil `AsyncImage`).

## Platforms

Android only (`minSdk 24`, `compileSdk 34`, Java 17).

## Architecture

| Layer | Contents |
|-------|----------|
| Presentation | `ListFragment`/`DetailsFragment` (hosts), `ListScreen`/`DetailsScreen` (Composables), `ListViewModel`/`DetailsViewModel` with `LiveData`, UI models and mappers |
| Domain | models, repository and use-case interfaces, mapper interfaces |
| Data | Retrofit `ApiService`, Room database, repository and mapper implementations |

Composables receive the ViewModel (or plain values and lambdas) and convert `LiveData` to Compose state with `observeAsState()`. A stateless `ListContent` composable receives data and callbacks, which makes the UI easy to preview. Hilt provides ViewModels to the fragments (`@AndroidEntryPoint`, `by viewModels()`).

## Technology stack

| Area | Technology |
|------|------------|
| UI | Jetpack Compose (BOM 2024.04.00, Material 2) inside Fragments via `ComposeView` |
| Networking | Retrofit 2.9 + OkHttp logging interceptor |
| Image loading | Coil Compose 2.2 (`AsyncImage`) |
| Serialization | Gson 2.10 |
| Database | Room 2.6 (KSP) |
| Concurrency | Kotlin coroutines |
| State management | `LiveData` → `observeAsState()` |
| Navigation | Navigation component (fragments, XML graph, Safe Args) |
| Dependency injection | Hilt 2.48 |
| Testing | Template `ExampleUnitTest` / `ExampleInstrumentedTest` only |
| Build | Gradle 8.4 (Kotlin DSL), AGP 8.3.0, Kotlin 1.9.23 |

## Project structure

```
app/src/main/java/com/vnteam/architecturetemplates/
├── di/AppModule.kt
├── data/ · domain/
└── presentation/
    ├── MainActivity.kt
    ├── list/    ListFragment, ListScreen, ListViewModel
    ├── details/ DetailsFragment, DetailsScreen, DetailsViewModel
    └── uimodels/ · mapperimpls/ · usecaseimpl/
app/src/main/res/navigation/navigation.xml
```

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation.

```bash
git clone --branch mvvm_compose_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :app:installDebug   # JDK 17, emulator/device required
```

## What this branch demonstrates

- Incremental adoption of Compose inside an existing Fragment + Navigation app.
- Bridging `LiveData` to Compose state.
- Splitting a screen into a stateful entry composable and a stateless content composable.

## Related article

No dedicated article. See the [Medium profile](https://medium.com/@vptarasov).

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Related: [MVVM + Clean Architecture](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_clean_architecture_implementation)
- Next: [MVVM + Compose Navigation](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_navigation_implementation)
