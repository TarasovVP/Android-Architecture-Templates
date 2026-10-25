# Android MVC (AsyncTask)

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `android-mvc-async-task` |
| Source branch | `mvc_async_task_implementation` (commit `6c02c716`) |
| Status | Historical - kept to show how Android evolved; not recommended for new projects. |
| Architecture | MVC |
| UI | XML Views |
| Platforms | Android |
| Key technologies | DI: None (ButterKnife view binding); network: OkHttp + Moshi (AsyncTask); storage: ORMLite; navigation: Activities (Intents) |
| Gradle modules | `:app` |
| Tool versions | Gradle 7.5, AGP 7.4.1, Kotlin 1.8.0 |
| JDK | 17 |
| Android SDK | compileSdk 33, targetSdk 33 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.mvc.asynctask` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | none |

## Build, test, run

From this directory:

```sh
./gradlew assembleDebug    # build
./gradlew testDebugUnitTest    # test
./gradlew :app:installDebug    # android
```

Or from the repository root: `./scripts/build-sample android-mvc-async-task`, `./scripts/test-sample android-mvc-async-task`,
`./scripts/run-sample android-mvc-async-task <target>`.

## Environment variables

- none

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- Historical sample: uses the deprecated AsyncTask on purpose.

The original README of the source branch follows unchanged.

<!-- catalog:original-readme -->

# MVC with AsyncTask

The second step of the Android line: the same MVC structure as the [native MVC branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvc_native_implementation), but with a few popular libraries of the time (OkHttp, Moshi, ORMLite, ButterKnife) and `AsyncTask` instead of `Handler` + `Thread` for background work.

> **Status: historical / educational.** `android.os.AsyncTask` is **deprecated** (API 30) and should not be used in new code. The branch is preserved to show how Android concurrency evolved towards RxJava and Kotlin coroutines in later branches.

## Overview

- **Purpose:** show the effect of replacing manual threading and raw platform APIs with libraries, while keeping the architecture unchanged.
- **Approach:** MVC; `Activity` classes are view + controller, connector classes form the data layer.
- **Difference from native MVC:** callbacks (`(result) -> Unit`) replace `Handler` messages; OkHttp, Moshi and ORMLite replace `HttpURLConnection`, `org.json` and raw SQLite; ButterKnife replaces `findViewById`.

## Functionality

1. The first screen shows a **Start** button and a progress bar.
2. **Start** requests the forks of `octocat/Spoon-Knife` from the GitHub API with OkHttp inside an `AsyncTask`.
3. The response is parsed with Moshi, converted into ORMLite entities and inserted into the local database (also in an `AsyncTask`), then read back and displayed in a `ListView`.
4. Tapping an item opens a details screen with the item data and the owner avatar, loaded as a `Bitmap` in an `AsyncTask`.

## Platforms

Android only (`minSdk 24`, `compileSdk 33`).

## Architecture

| Role | Classes |
|------|---------|
| Model | `DemoObject`, `Owner`, `DemoObjectDB` (ORMLite entity) |
| View | XML layouts bound with ButterKnife (`@BindView`, `@OnClick`) |
| Controller | `MainActivity`, `DetailsActivity` |
| Data access | `OkHttpClientConnector` (network + image), `OrmLiteSqliteDBConnector` (database), `JsonConverter` (Moshi) |

Asynchronous results are delivered through lambda callbacks passed into the connectors. Controllers still know about every data-layer class, so testability and separation of concerns remain limited.

## Technology stack

| Area | Technology |
|------|------------|
| UI | XML layouts, `AppCompatActivity`, `ListView`, ButterKnife 10.2.3 (kapt) |
| Networking | OkHttp 4.11 for the list request; `HttpURLConnection` for the avatar image |
| Image loading | `BitmapFactory.decodeStream` |
| Serialization | Moshi 1.14 (Kotlin adapter) |
| Database | ORMLite 5.5 (`OrmLiteSqliteOpenHelper`) |
| Concurrency | `android.os.AsyncTask` (deprecated) |
| State management | None |
| Navigation | Explicit `Intent` between two activities |
| Dependency injection | None |
| Testing | Template `ExampleUnitTest` / `ExampleInstrumentedTest` only |
| Build | Gradle (Groovy DSL), Android Gradle Plugin 7.4.1, Kotlin 1.8.0 |

## Project structure

```
app/src/main/java/com/vnteam/architecturetemplates/
├── MainActivity.kt               # list screen controller
├── DetailsActivity.kt            # details screen controller
├── OkHttpClientConnector.kt      # OkHttp + AsyncTask network calls
├── OrmLiteSqliteDBConnector.kt   # ORMLite helper, async insert
├── JsonConverter.kt              # Moshi parsing, model mapping
├── DemoObject.kt / Owner.kt      # domain-like models
└── DemoObjectDB.kt               # ORMLite entity
```

## How to run

> The commands below are derived from the project configuration. They were **not executed** while preparing this documentation.

```bash
git clone --branch mvc_async_task_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :app:installDebug   # emulator/device required
```

Requires Android Studio, Android SDK 33 and an Internet connection.

## What this branch demonstrates

- The same MVC design with libraries instead of platform primitives.
- How `AsyncTask` structured background work — and why it was retired.
- A concrete "before" for the Rx-based and coroutine-based branches that follow.

## Related article

No dedicated article. See the [Medium profile](https://medium.com/@vptarasov).

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [MVC with native APIs](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvc_native_implementation)
- Next: [MVP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvp_implementation)
