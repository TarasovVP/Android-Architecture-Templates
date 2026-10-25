# Android MVC (native APIs)

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `android-mvc-native` |
| Status | Historical - kept to show how Android evolved; not recommended for new projects. |
| Architecture | MVC |
| UI | XML Views |
| Platforms | Android |
| Key technologies | DI: None; network: HttpURLConnection + Handler/Thread; storage: SQLiteDatabase; navigation: Activities (Intents) |
| Gradle modules | `:app` |
| Tool versions | Gradle 7.5, AGP 7.4.1, Kotlin 1.8.0 |
| JDK | 17 |
| Android SDK | compileSdk 33, targetSdk 33 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.mvc.native` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | none |

## Build, test, run

From this directory:

```sh
./gradlew assembleDebug    # build
./gradlew testDebugUnitTest    # test
./gradlew :app:installDebug    # android
```

Or from the repository root: `./scripts/build-sample android-mvc-native`, `./scripts/test-sample android-mvc-native`,
`./scripts/run-sample android-mvc-native <target>`.

## Environment variables

- none

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- Historical sample: platform APIs only, no tests of its own.

# MVC with Native Android APIs

The baseline of the repository: a two-screen Android app built with the Model-View-Controller pattern and only platform APIs (`HttpURLConnection`, `SQLiteDatabase`, `Handler` + `Thread`, `org.json`). No third-party libraries are used for networking, persistence, JSON or threading.

> **Status: historical / educational.** This branch is kept to show how the same feature looks without modern libraries and to provide a starting point for the architecture evolution described in the [repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md). It is not a recommended way to build new apps.

## Overview

- **Purpose:** demonstrate the feature scenario with the smallest possible dependency set.
- **Approach:** MVC where `Activity` classes act as view and controller at the same time; helper classes act as the model/data layer.
- **Place in the evolution:** first step of the Android line. Next: [MVC with AsyncTask](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvc_async_task_implementation).

## Functionality

1. The first screen shows a **Start** button and a progress bar.
2. Tapping **Start** requests the list of forks of `octocat/Spoon-Knife` from the GitHub REST API (`https://api.github.com/repos/octocat/Spoon-Knife/forks`).
3. The JSON response is parsed, written to a local SQLite table and then shown as a list of names.
4. Tapping a list item opens a details screen with the full name, owner login and the owner avatar (downloaded separately and decoded with `BitmapFactory`). A **Back** button returns to the list.

## Platforms

Android only (`minSdk 24`, `compileSdk 33`, Kotlin, Java 8 bytecode).

## Architecture

| Role | Classes |
|------|---------|
| Model | `DemoObject`, `Owner` (data classes), `JsonConverter` (JSON → objects), `SQLiteDBConnector` (storage) |
| View | `activity_main` / `activity_details` XML layouts rendered by the activities |
| Controller | `MainActivity`, `DetailsActivity` — handle clicks, start work and update views |
| Infrastructure | `HttpUrlConnector` — network calls on a background `Thread` |

Communication between background threads and the UI uses `android.os.Handler` with `Message` objects (`SUCCESS_HTTP_CONNECTION`, `SUCCESS_SQLITE_CONNECTION`, `ERROR`, …). The activities are therefore large and tightly coupled to the data layer — this is the main limitation that the following branches address.

## Technology stack

| Area | Technology |
|------|------------|
| UI | XML layouts, `AppCompatActivity`, `ListView` |
| Networking | `java.net.HttpURLConnection` |
| Image loading | `BitmapFactory.decodeStream` over `HttpURLConnection` |
| Serialization | `org.json.JSONObject` |
| Database | `android.database.sqlite.SQLiteDatabase` (`SQLiteOpenHelper`) |
| Concurrency | `Thread` + `android.os.Handler` |
| State management | None (data passed through `Handler` messages and `Intent` extras) |
| Navigation | Explicit `Intent` between two activities |
| Dependency injection | None |
| Testing | Template `ExampleUnitTest` / `ExampleInstrumentedTest` only |
| Build | Gradle 7.5 (Groovy DSL), Android Gradle Plugin 7.4.x |

## Project structure

```
app/src/main/java/com/vnteam/architecturetemplates/
├── MainActivity.kt        # list screen + controller logic
├── DetailsActivity.kt     # details screen
├── HttpUrlConnector.kt    # HTTP requests (data + image)
├── JsonConverter.kt       # org.json parsing
├── SQLiteDBConnector.kt   # SQLiteOpenHelper + inserts/reads
├── DemoObject.kt          # model
└── Owner.kt               # model
```

## How to run

> The commands below are derived from the project configuration. They were **not executed** while preparing this documentation.

1. Install Android Studio with a JDK and Android SDK 33.
2. Open the project and run the `app` configuration, or from a terminal:

```bash
git clone --branch mvc_native_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :app:installDebug   # with an emulator/device connected
```

An Internet connection is required for the GitHub API call.

## What this branch demonstrates

- The feature implemented with platform APIs only.
- How MVC maps onto Android, where the controller and the view are usually merged in an `Activity`.
- Manual threading with `Thread` + `Handler` and the amount of plumbing it requires.
- A reference point for measuring what later branches improve.

## Related article

No dedicated article. For the broader context see the [Medium profile](https://medium.com/@vptarasov).

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Next step: [MVC with AsyncTask](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvc_async_task_implementation)
- Then: [MVP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvp_implementation)
