# Android MVP

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `android-mvp` |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVP |
| UI | XML Views + Fragments |
| Platforms | Android |
| Key technologies | DI: Dagger 2; network: Volley + RxJava; storage: Realm; navigation: FragmentManager |
| Gradle modules | `:app` |
| Tool versions | Gradle 7.5, AGP 7.4.1, Kotlin 1.8.0 |
| JDK | 17 |
| Android SDK | compileSdk 33, targetSdk 33 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.mvp` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | none |

## Build, test, run

From this directory:

```sh
./gradlew assembleDebug    # build
./gradlew testDebugUnitTest    # test
./gradlew :app:installDebug    # android
```

Or from the repository root: `./scripts/build-sample android-mvp`, `./scripts/test-sample android-mvp`,
`./scripts/run-sample android-mvp <target>`.

## Environment variables

- none

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- Uses Volley and Realm, kept on purpose as a historical stack.

# MVP with RxJava, Realm and Dagger

Model-View-Presenter implementation with single-activity/fragment navigation. Presenters own the screen logic, views are reduced to interfaces implemented by fragments, and asynchronous work is expressed with RxJava `Observable`s.

## Overview

- **Purpose:** show how moving logic out of the `Activity` into a `Presenter` behind a view contract improves separation and testability compared with the MVC branches.
- **Approach:** MVP with one contract interface per screen; manual dependency injection via Dagger 2.
- **Place in the evolution:** after the MVC branches, before the MVVM branches.

## Functionality

1. `MainActivity` hosts the `ListFragment`.
2. **Start** loads the forks of `octocat/Spoon-Knife` from the GitHub API through Volley.
3. The result is inserted into a Realm database and then read back and shown in a `RecyclerView`.
4. Selecting an item opens `DetailsFragment`, which loads the item from Realm by id and shows it with the owner avatar (Picasso).

## Platforms

Android only (`minSdk 24`, `compileSdk 33`).

## Architecture

| Role | Classes |
|------|---------|
| Model | `models/DemoObject`, `models/Owner` (Realm objects), `network/ValleyApiConnector`, `database/RealmDBConnector` |
| View | `ListFragment`, `DetailsFragment` implementing `ListViewContract` / `DetailsViewContract`; XML layouts with view binding |
| Presenter | `ListPresenter`, `DetailsPresenter` — attach/detach the view, call the connectors and push results to the view |
| DI | `AppComponent` + `AppModule` (Dagger 2) providing `RequestQueue`, `Realm`, connectors and presenters |

Flow on the list screen: the fragment calls `listPresenter.getDemoObjectsFromApi()` → the presenter subscribes to the Volley-backed `Observable` → results are delivered on the main thread → the presenter asks the view (through the contract) to insert them into the database and then to load them from it. Presenters hold a nullable view reference that is cleared in `onDestroyView`.

## Technology stack

| Area | Technology |
|------|------------|
| UI | XML layouts, `AppCompatActivity` + `Fragment`, `RecyclerView`, view binding |
| Networking | Volley 1.2.1 |
| Image loading | Picasso 2.8 |
| Serialization | Moshi 1.14 |
| Database | Realm 10.11 (`realm-android` plugin) |
| Concurrency | RxJava 2 / RxAndroid 3 |
| State management | Observables inside presenters; view updated through the contract |
| Navigation | Manual `FragmentManager` transactions (no Navigation component) |
| Dependency injection | Dagger 2.46 (kapt; `AppComponent`/`AppModule`) — not Hilt |
| Testing | Template `ExampleUnitTest` / `ExampleInstrumentedTest` only |
| Build | Gradle 7.5 (Groovy DSL), AGP 7.4.1, Kotlin 1.8.0 |

## Project structure

```
app/src/main/java/com/vnteam/architecturetemplates/
├── AppApplication.kt        # Realm.init, Dagger component
├── MainActivity.kt          # fragment host, constants
├── di/                      # AppComponent, AppModule
├── models/                  # DemoObject, Owner
├── network/                 # ValleyApiConnector
├── database/                # RealmDBConnector
├── list/                    # ListFragment, ListPresenter, ListViewContract, adapter
└── details/                 # DetailsFragment, DetailsPresenter, DetailsViewContract
```

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation.

```bash
git clone --branch mvp_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :app:installDebug   # emulator/device required
```

Requires Android Studio, Android SDK 33 and an Internet connection.

## What this branch demonstrates

- Presenter-centric logic with explicit view contracts.
- Reactive streams (RxJava) as the concurrency mechanism.
- Compile-time dependency injection with plain Dagger 2.
- Why presenters that talk to data classes directly are still tightly bound to infrastructure — a motivation for ViewModel + repository layers.

## Related article

No dedicated article. See the [Medium profile](https://medium.com/@vptarasov).

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [MVC with AsyncTask](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvc_async_task_implementation)
- Next: [MVVM](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_implementation)
