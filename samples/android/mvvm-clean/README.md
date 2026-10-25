# Android MVVM + Clean Architecture

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `android-mvvm-clean` |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVVM + Clean Architecture |
| UI | XML Views + Fragments |
| Platforms | Android |
| Key technologies | DI: Hilt; network: Retrofit + OkHttp; storage: Room; navigation: Navigation component |
| Gradle modules | `:app` |
| Tool versions | Gradle 8.4, AGP 8.3.0, Kotlin 1.9.23 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.mvvm.clean` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | none |

## Build, test, run

From this directory:

```sh
./gradlew assembleDebug    # build
./gradlew testDebugUnitTest    # test
./gradlew :app:installDebug    # android
```

Or from the repository root: `./scripts/build-sample android-mvvm-clean`, `./scripts/test-sample android-mvvm-clean`,
`./scripts/run-sample android-mvvm-clean <target>`.

## Environment variables

- none

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- None recorded.

# MVVM with Clean Architecture

An MVVM implementation organised in Clean Architecture layers (`presentation`, `domain`, `data`) with explicit repository and use-case abstractions, model mappers between layers, Hilt, Retrofit and Room.

## Overview

- **Purpose:** present the layered, dependency-inverted structure on a concrete domain entity — a GitHub **fork** (`Fork`, `ForkUI`, `ForkDB`, `ForkResponse`, `ForkUseCase`, …).
- **Approach:** the `domain` layer defines models, repository interfaces, mapper interfaces and the use-case contract; `data` implements them; `presentation` consumes them through ViewModels.
- **Difference from the plain [MVVM branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_implementation):** in the current code both branches share the same layering, libraries and flow. This branch differs mainly by **domain naming** (`Fork*` instead of the generic `DemoObject*`) and a few resource changes. Read it as the "named" variant of the same architecture, not as a structurally different one.

## Functionality

1. `MainActivity` hosts a navigation graph with the list and details fragments.
2. **Start** on the list screen loads the forks of `octocat/Spoon-Knife` from the GitHub API (Retrofit), stores them in Room and displays the stored list.
3. Selecting an item opens the details screen with data from Room and the owner avatar (Coil).

## Platforms

Android only (`minSdk 24`, `compileSdk 34`, Java 17).

## Architecture

| Layer | Contents |
|-------|----------|
| `domain` | `Fork`, `Owner`; `ApiRepository`, `DBRepository`; `ForkUseCase`; `BaseMapper` and mapper interfaces (`ForkResponseMapper`, `ForkDBMapper`, `ForkUIMapper`, …) |
| `data` | `ApiService` (Retrofit), `ForkResponse`/`OwnerResponse`; `AppDatabase`, `ForkDao`, `ForkDB`/`OwnerDB` (Room); `ApiRepositoryImpl`, `DBRepositoryImpl`; mapper implementations |
| `presentation` | `MainActivity`, `ListFragment`/`DetailsFragment`, `ListViewModel`/`DetailsViewModel`, `ForkAdapter`, `ForkUI`/`OwnerUI`, UI mappers, `ForkUseCaseImpl` |
| `di` | `AppModule` (Hilt) — OkHttp, Retrofit, Room, mappers, repositories, use case |

Dependencies point towards `domain`: ViewModels depend on the use-case interface, repositories are exposed as interfaces. Two deviations from a strict Clean Architecture are visible in the code: the use-case **implementation** sits in `presentation/usecaseimpl`, and the application is a single Gradle module, so layer boundaries are enforced by package convention rather than by the build.

## Technology stack

| Area | Technology |
|------|------------|
| UI | XML layouts, `AppCompatActivity` + Fragments, `RecyclerView`, view binding |
| Networking | Retrofit 2.9 + OkHttp logging interceptor |
| Image loading | Coil 2.2 |
| Serialization | Gson 2.10 |
| Database | Room 2.6 (KSP) |
| Concurrency | Kotlin coroutines (`viewModelScope`) |
| State management | `LiveData` |
| Navigation | Navigation component with Safe Args |
| Dependency injection | Hilt 2.48 (KSP) |
| Testing | Template `ExampleUnitTest` / `ExampleInstrumentedTest` only |
| Build | Gradle 8.4 (Kotlin DSL), AGP 8.3.0, Kotlin 1.9.23 |

## Project structure

```
app/src/main/java/com/vnteam/architecturetemplates/
├── AppApplication.kt
├── di/AppModule.kt
├── domain/        # models, repositories, mappers, usecase
├── data/          # network, database, mapperimpls, repositoryimpl
└── presentation/  # activity, list, details, uimodels, mapperimpls, usecaseimpl
```

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation.

```bash
git clone --branch mvvm_clean_architecture_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :app:installDebug   # JDK 17, emulator/device required
```

## What this branch demonstrates

- Layer separation and dependency direction in Clean Architecture.
- Separate models per layer (response, database, domain, UI) and the mappers connecting them.
- Repository and use-case contracts that keep ViewModels independent of Retrofit and Room.
- The practical cost of the approach: many small classes for a two-screen feature.

## Related article

No dedicated article. See the [Medium profile](https://medium.com/@vptarasov).

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Compare with: [MVVM](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_implementation)
- Next: [MVVM + Compose](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_implementation)
