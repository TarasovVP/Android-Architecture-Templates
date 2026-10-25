# Android MVVM

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `android-mvvm` |
| Source branch | `mvvm_implementation` (commit `be3505bc`) |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVVM |
| UI | XML Views + Fragments |
| Platforms | Android |
| Key technologies | DI: Hilt; network: Retrofit + OkHttp; storage: Room; navigation: Navigation component |
| Gradle modules | `:app` |
| Tool versions | Gradle 8.4, AGP 8.3.0, Kotlin 1.9.23 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.mvvm` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | none |

## Build, test, run

From this directory:

```sh
./gradlew assembleDebug    # build
./gradlew testDebugUnitTest    # test
./gradlew :app:installDebug    # android
```

Or from the repository root: `./scripts/build-sample android-mvvm`, `./scripts/test-sample android-mvvm`,
`./scripts/run-sample android-mvvm <target>`.

## Environment variables

- none

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- None recorded.

The original README of the source branch follows unchanged.

<!-- catalog:original-readme -->

# MVVM with Hilt, Room and Retrofit

The first "modern Android" implementation in the repository: MVVM with Jetpack `ViewModel` and `LiveData`, a repository layer, Hilt for dependency injection, Retrofit for networking, Room for caching, Kotlin coroutines for concurrency and the Navigation component with Fragments for screens.

## Overview

- **Purpose:** show the standard Jetpack-based architecture for a View-based (XML) Android app.
- **Approach:** MVVM with `data` / `domain` / `presentation` packages inside a single `app` module, use cases and mapper classes between layers.
- **Place in the evolution:** replaces the MVP branch. Variations built on top of the same idea: [Clean Architecture naming](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_clean_architecture_implementation) and [Compose](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_implementation).

## Functionality

1. `MainActivity` hosts a navigation graph with two fragment destinations.
2. On the list screen **Start** loads the forks of `octocat/Spoon-Knife` from the GitHub API (Retrofit), inserts them into the Room database and then displays the stored list in a `RecyclerView`.
3. Selecting an item navigates (Safe Args, `demoObjectId`) to the details screen, which reads the item from Room and shows it with the owner avatar (Coil).

## Platforms

Android only (`minSdk 24`, `compileSdk 34`, Java 17).

## Architecture

```
presentation  ─►  domain  ◄─  data
(Fragments,       (models, repository     (Retrofit ApiService,
 ViewModels,       interfaces, use case    Room DAO/entities,
 UI models)        interfaces, mappers)    repository impls, mapper impls)
```

| Layer | Responsibility | Examples |
|-------|----------------|----------|
| Presentation | Render state, forward user actions | `ListFragment`, `DetailsFragment`, `ListViewModel`, `DetailsViewModel` (`@HiltViewModel`, expose `MutableLiveData`), `DemoObjectUI` |
| Domain | Contracts and business models | `DemoObject`, `ApiRepository`, `DBRepository`, `DemoObjectUseCase`, mapper interfaces |
| Data | Sources of data | `ApiService` (Retrofit), `AppDatabase`/`DemoObjectDao` (Room), `ApiRepositoryImpl`, `DBRepositoryImpl`, response/DB mappers |

`AppModule` (Hilt, `SingletonComponent`) wires the OkHttp client with a logging interceptor, Retrofit with the Gson converter, Room, mappers, repositories and the use case. Note that `DemoObjectUseCaseImpl` lives in `presentation/usecaseimpl`, and domain-to-UI mapping happens in the ViewModels.

## Technology stack

| Area | Technology |
|------|------------|
| UI | XML layouts, `AppCompatActivity` + Fragments, `RecyclerView`, view binding |
| Networking | Retrofit 2.9 + OkHttp logging interceptor |
| Image loading | Coil 2.2 |
| Serialization | Gson 2.10 |
| Database | Room 2.6 (KSP) |
| Concurrency | Kotlin coroutines (`viewModelScope`) |
| State management | `LiveData` / `MutableLiveData` |
| Navigation | Navigation component with Safe Args |
| Dependency injection | Hilt 2.48 (KSP) |
| Testing | Template `ExampleUnitTest` / `ExampleInstrumentedTest`; `hilt-android-testing` is declared |
| Build | Gradle 8.4 (Kotlin DSL), AGP 8.3.0, Kotlin 1.9.23 |

## Project structure

```
app/src/main/java/com/vnteam/architecturetemplates/
├── AppApplication.kt            # @HiltAndroidApp
├── di/AppModule.kt              # Hilt bindings
├── data/                        # network, database, mapperimpls, repositoryimpl
├── domain/                      # models, repositories, mappers, usecase
└── presentation/                # MainActivity, list, details, uimodels, mapperimpls, usecaseimpl
app/src/main/res/navigation/navigation.xml
```

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation.

```bash
git clone --branch mvvm_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :app:installDebug   # JDK 17, emulator/device required
```

## What this branch demonstrates

- `ViewModel` + `LiveData` as the presentation state holder.
- Repository and use-case abstractions with explicit mapping between response, database, domain and UI models.
- Hilt-based DI and Room-backed offline cache.
- A coroutine-based replacement for the RxJava code of the MVP branch.

## Related article

No dedicated article for this branch. See the [Medium profile](https://medium.com/@vptarasov).

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [MVP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvp_implementation)
- Related: [MVVM + Clean Architecture](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_clean_architecture_implementation), [MVVM + Compose](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_implementation)
