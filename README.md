# Android Architecture Templates

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

A catalog of Android and Kotlin Multiplatform (KMP) implementations of **one small
scenario**: fetch a list from an API, cache it in a local database, show it, open a details
screen. The scenario stays the same; each sample changes one technical decision at a time -
architecture pattern, UI toolkit, networking, persistence, DI, navigation, platforms, module
structure, tests, code analysis and CI/CD - so the differences between samples show the effect
of that decision.

Everything lives in one branch. You can browse, diff and run any sample **without switching
Git branches**.

## Contents

- [Quick start](#quick-start)
- [How the repository is organised](#how-the-repository-is-organised)
- [Why there is no single Gradle build](#why-there-is-no-single-gradle-build)
- [All samples](#all-samples)
- [Where to start](#where-to-start)
- [Open a sample in Android Studio](#open-a-sample-in-android-studio)
- [Build, test and run](#build-test-and-run)
- [Environment requirements](#environment-requirements)
- [Comparing samples](#comparing-samples)
- [How the project evolved](#how-the-project-evolved)
- [Articles](#articles)
- [Status and limitations](#status-and-limitations)
- [Author](#author)

## Quick start

```sh
./scripts/list-samples
./scripts/sample-info android-mvvm
./scripts/build-sample android-mvvm
./scripts/run-sample android-mvvm android
```

The scripts work from any directory, use each sample's own Gradle wrapper and do not need a
globally installed Gradle. They never install a JDK, Android SDK, Node.js, Docker or Xcode;
when something is missing they tell you what to install.

## How the repository is organised

```
catalog/samples.json      data source for the scripts (one entry per sample)
scripts/                  list-samples, sample-info, build-sample, test-sample, run-sample
samples/android/*         8 Android-only samples
samples/kmp/*             9 Kotlin Multiplatform samples
.github/workflows/        CI matrix, one job per sample
```

Every folder in `samples/` is an **independent Gradle build** with its own Gradle wrapper,
`settings.gradle(.kts)`, version catalog, README and `sample.json` metadata. Samples do not
depend on each other and share no code.

## Why there is no single Gradle build

The samples were written over several years and use incompatible generations of tools:
Gradle 7.5, 8.4, 8.7 and 8.11.1; Kotlin from 1.8.0 to 2.2.0; several Android Gradle Plugin
versions; JDK 17 and 21. A root `settings.gradle` that included them all would force one set
of versions, make syncing very slow, and break the historical samples. So there is deliberately
**no root `settings.gradle(.kts)`, no `include` and no `includeBuild`**. Open and build samples
one at a time.

## All samples

Status: **Historical** - kept to show how Android evolved, not recommended for new projects;
**Reference** - a complete, self-contained implementation of an approach; **Latest** - the most
current and complete state.

| ID | Architecture | UI | DI | Network | Storage | Navigation | Platforms | Status |
|----|--------------|----|----|---------|---------|------------|-----------|--------|
| [`android-mvc-native`](samples/android/mvc-native) | MVC | XML Views | None | HttpURLConnection + Handler/Thread | SQLiteDatabase | Activities (Intents) | Android | Historical |
| [`android-mvc-async-task`](samples/android/mvc-async-task) | MVC | XML Views | None (ButterKnife view binding) | OkHttp + Moshi (AsyncTask) | ORMLite | Activities (Intents) | Android | Historical |
| [`android-mvp`](samples/android/mvp) | MVP | XML Views + Fragments | Dagger 2 | Volley + RxJava | Realm | FragmentManager | Android | Reference |
| [`android-mvvm`](samples/android/mvvm) | MVVM | XML Views + Fragments | Hilt | Retrofit + OkHttp | Room | Navigation component | Android | Reference |
| [`android-mvvm-clean`](samples/android/mvvm-clean) | MVVM + Clean Architecture | XML Views + Fragments | Hilt | Retrofit + OkHttp | Room | Navigation component | Android | Reference |
| [`android-mvvm-compose`](samples/android/mvvm-compose) | MVVM | Jetpack Compose in Fragments | Hilt | Retrofit + OkHttp | Room | Navigation component | Android | Reference |
| [`android-mvvm-compose-navigation`](samples/android/mvvm-compose-navigation) | MVVM | Jetpack Compose | Koin | Ktor Client | SQLDelight | Navigation Compose | Android | Reference |
| [`android-mvi-compose-navigation`](samples/android/mvi-compose-navigation) | MVI | Jetpack Compose | Koin | Ktor Client | SQLDelight | Voyager | Android | Reference |
| [`kmp-kmm`](samples/kmp/kmm) | MVI | Compose Multiplatform | Koin | Ktor Client | SQLDelight | Navigation Compose | Android / iOS | Historical |
| [`kmp-basic`](samples/kmp/basic) | MVI | Compose Multiplatform + Compose HTML | Koin | Ktor Client | SQLDelight + DataStore | Navigation Compose | Android / iOS / Desktop / Web | Reference |
| [`kmp-with-server`](samples/kmp/with-server) | MVI + Ktor server | Compose Multiplatform + Compose HTML | Koin | Ktor Client + Ktor Server | SQLDelight + DataStore + PostgreSQL (server) | Navigation Compose | Android / iOS / Desktop / Web / Server | Reference |
| [`kmp-multimodule`](samples/kmp/multimodule) | MVI, multi-module | Compose Multiplatform + Compose HTML (web) | Koin | Ktor Client | SQLDelight + DataStore | Navigation Compose | Android / iOS / Desktop / Web / Server | Reference |
| [`kmp-git-submodules`](samples/kmp/git-submodules) | MVI, multi-repo | Compose Multiplatform + Compose HTML (web) | Koin | Ktor Client | SQLDelight + DataStore | Navigation Compose | Android / iOS / Desktop / Web / Server | Reference |
| [`kmp-tests`](samples/kmp/tests) | MVI, multi-module | Compose Multiplatform + Compose HTML (web) | Koin | Ktor Client | SQLDelight + DataStore | Navigation Compose | Android / iOS / Desktop / Web / Server | Reference |
| [`kmp-custom-plugins`](samples/kmp/custom-plugins) | MVI, multi-module | Compose Multiplatform + Compose HTML (web) | Koin | Ktor Client | SQLDelight + DataStore | Navigation Compose | Android / iOS / Desktop / Web / Server | Reference |
| [`kmp-analysis-tools`](samples/kmp/analysis-tools) | MVI, multi-module | Compose Multiplatform + Compose HTML (web) | Koin | Ktor Client | SQLDelight + DataStore | Navigation Compose | Android / iOS / Desktop / Web / Server | Reference |
| [`kmp-ci-cd`](samples/kmp/ci-cd) | MVI, multi-module | Compose Multiplatform + Compose HTML (web) | Koin | Ktor Client | SQLDelight + DataStore | Navigation Compose | Android / iOS / Desktop / Web / Server | Latest |

The original branch of every sample is listed in its `sample.json` and README
(`sourceBranch`). The branches still exist and their history was not changed.

## Where to start

- **New to the scenario:** [`android-mvvm`](samples/android/mvvm) - the classic Android stack
  (ViewModel, Hilt, Retrofit, Room).
- **Modern Android:** [`android-mvi-compose-navigation`](samples/android/mvi-compose-navigation) -
  Compose, MVI, Koin, Ktor, SQLDelight.
- **Kotlin Multiplatform:** [`kmp-multimodule`](samples/kmp/multimodule) for module structure,
  then [`kmp-ci-cd`](samples/kmp/ci-cd) for the latest, most complete state.
- **Historical baseline:** [`android-mvc-native`](samples/android/mvc-native) and
  [`android-mvc-async-task`](samples/android/mvc-async-task) (plain platform APIs, AsyncTask).
  `android-mvp` still uses Volley and Realm. `kmp-kmm` keeps the old "KMM" naming.

## Open a sample in Android Studio

1. **File > Open** and choose the **sample folder**, for example `samples/android/mvvm`.
2. Let Android Studio sync that folder's Gradle build (it uses the sample's own wrapper).
3. Run the `app` (or `mobile` / `composeApp`) configuration.

Do **not** import the repository root as a Gradle project - it is not one. Open the root folder
in a plain editor (or any text editor / project view) only to browse and compare sources side
by side.

## Build, test and run

```sh
./scripts/build-sample <id> [gradle-task...]   # default: the sample's assemble task
./scripts/test-sample  <id> [gradle-task...]   # default: the sample's test task
./scripts/run-sample   <id> <android|desktop|web|server>
```

Examples:

```sh
./scripts/build-sample android-mvvm assembleDebug
./scripts/build-sample kmp-basic :composeApp:assembleDebug
./scripts/run-sample kmp-multimodule desktop
```

You can also work directly in a sample: `cd samples/kmp/multimodule && ./gradlew :mobile:assembleDebug`.

- `run-sample` supports `android`, `desktop`, `web` and `server` where the sample has such a
  target (see `./scripts/sample-info <id>`). iOS is not started from the command line: open the
  sample's `iosApp` in Xcode.
- The scripts pick the JDK listed in `sample.json` when they can find it (on macOS through
  `/usr/libexec/java_home`); override with `SAMPLE_JAVA_HOME=/path/to/jdk`.
- All Android apps have a unique `applicationId`
  (`com.tarasovvp.architecturetemplates.<sample>`), so several samples can be installed on one
  device at the same time. Kotlin packages and namespaces are unchanged.

## Environment requirements

General: an Android SDK (`ANDROID_HOME` or `sdk.dir` in the sample's `local.properties`),
internet access to download dependencies.  Exact versions are in each sample's `sample.json` and README.

| ID | JDK | Extra requirements |
|----|-----|--------------------|
| `android-mvc-native` | 17 | - |
| `android-mvc-async-task` | 17 | - |
| `android-mvp` | 17 | - |
| `android-mvvm` | 17 | - |
| `android-mvvm-clean` | 17 | - |
| `android-mvvm-compose` | 17 | - |
| `android-mvvm-compose-navigation` | 17 | - |
| `android-mvi-compose-navigation` | 17 | - |
| `kmp-kmm` | 17 | Xcode (iOS target only) |
| `kmp-basic` | 17 | Xcode (iOS target only) |
| `kmp-with-server` | 17 | Xcode (iOS target only), PostgreSQL (`server` target only), Docker (server image only) |
| `kmp-multimodule` | 17 | Xcode (iOS target only), PostgreSQL (`server` target only), Docker (server image only) |
| `kmp-git-submodules` | 17 | Xcode (iOS target only), PostgreSQL (`server` target only), Docker (server image only) |
| `kmp-tests` | 17 | Xcode (iOS target only), PostgreSQL (`server` target only), Docker (server image only), `local.properties` keys / secrets |
| `kmp-custom-plugins` | 17 | Xcode (iOS target only), PostgreSQL (`server` target only), Docker (server image only), `local.properties` keys / secrets |
| `kmp-analysis-tools` | 17 | Xcode (iOS target only), PostgreSQL (`server` target only), Docker (server image only), `local.properties` keys / secrets |
| `kmp-ci-cd` | 21 | Xcode (iOS target only), PostgreSQL (`server` target only), Docker (server image only), `local.properties` keys / secrets |

Notes:

- Samples with a `server` target need PostgreSQL only to **run** the server; building the
  Android/Desktop/Web clients does not need it. Docker is only used for the server image.
- Node.js and Yarn for the Web targets are downloaded by the Kotlin Gradle plugin.
- `kmp-tests`, `kmp-custom-plugins`, `kmp-analysis-tools` and `kmp-ci-cd` read values such as
  `CLOUD_URL` from `local.properties`; release signing, Sonar and Fastlane values come from
  environment variables. See each sample's README. Never commit `local.properties` or keystores.

## Comparing samples

- Side by side in your editor, or `git diff --no-index samples/android/mvvm samples/android/mvvm-clean`.

## How the project evolved

Android: `mvc-native` -> `mvc-async-task` -> `mvp` -> `mvvm` -> `mvvm-compose` ->
`mvvm-compose-navigation` -> `mvi-compose-navigation`, with `mvvm-clean` branching off `mvvm`.
KMP: `mvi-compose-navigation` -> `kmm` -> `basic` -> `with-server` -> `multimodule` -> `tests` ->
`custom-plugins` -> `analysis-tools` -> `ci-cd`, with `git-submodules` as a side branch of
`multimodule`.

## Articles

Articles by the author on [Medium](https://medium.com/@vptarasov) related to the samples:

| Article | Most relevant samples |
|---------|-----------------------|
| [Kotlin Multiplatform - Now Supported by Google](https://medium.com/@vptarasov/kotlin-multiplatform-now-supported-by-google-2874dd2d69b7) | Background for the KMP line |
| [SQLDelight in Kotlin Multiplatform: Steps to Implementation](https://medium.com/@vptarasov/sqldelight-in-kotlin-multiplatform-steps-to-implementation-bc930029de69) | `android-mvvm-compose-navigation`, `kmp-kmm`, `kmp-basic` (topic) |
| [KMP: Network Requests with Ktor](https://medium.com/@vptarasov/kmp-network-requests-with-ktor-498d0a7b544e) | `kmp-kmm`, `kmp-basic` (topic) |
| [KMP: Dependency Injection with Koin](https://medium.com/@vptarasov/kmp-dependency-injection-with-koin-d1f442f87cee) | `kmp-basic` (topic) |
| [KMP: Server Implementation](https://medium.com/@vptarasov/kmp-server-implementation-49a0a4389b34) | `kmp-with-server` |
| [Navigation in a Web Application with Kotlin Multiplatform](https://medium.com/@vptarasov/navigation-in-a-web-application-with-kotlin-multiplatform-2ac625b0ea04) | `kmp-with-server` (web module) |
| [Deploying a KMP Server on Google Cloud: Step-by-Step Guide](https://medium.com/@vptarasov/deploying-a-kmp-server-on-google-cloud-step-by-step-guide-f32ade8c4be3) | `kmp-with-server` |
| [KMP: Modular Architecture](https://medium.com/@vptarasov/kmp-modular-architecture-faabbdf58197) | `kmp-multimodule` |
| [Migration to Git Submodules Architecture in a KMP Project](https://medium.com/@vptarasov/migration-to-git-submodules-architecture-in-a-kmp-project-4a73ec4a2c9e) | `kmp-git-submodules` |
| [Unit Testing in Kotlin Multiplatform](https://medium.com/@vptarasov/unit-testing-in-kotlin-multiplatform-fb3ca7d5c869) | `kmp-tests` |
| [Automating Work with Secrets in Kotlin Multiplatform](https://medium.com/@vptarasov/automating-work-with-secrets-in-kotlin-multiplatform-a2b4c587180b) | `kmp-custom-plugins` |
| [Ktlint in KMP: Setup, Configuration, and Custom Rules](https://medium.com/@vptarasov/ktlint-in-kmp-setup-configuration-and-custom-rules-4b9f7ab86347) | `kmp-custom-plugins`, `kmp-analysis-tools` |
| [Code Quality Control in KMP with SonarQube](https://medium.com/@vptarasov/code-quality-control-in-kmp-with-sonarqube-1237ec4f3a4b) | `kmp-analysis-tools`, `kmp-ci-cd` |

"(topic)" means the article covers a technology used in the sample but is not a walkthrough of
it. Samples without an entry have no dedicated article.

## Status and limitations

This is a personal **architecture playground and showcase**, not a library or framework.

- Samples are snapshots; dependency versions differ and older ones are not kept up to date.
- Deprecated technologies (AsyncTask, Volley, Realm, KMM naming) are kept deliberately.
- Default URLs, database settings and secrets in the code are placeholders or demo values.

Contributing rules: [CONTRIBUTING.md](CONTRIBUTING.md). License: [Apache-2.0](LICENSE).

## Author

**Volodymyr Tarasov** - [GitHub](https://github.com/TarasovVP) - [Medium](https://medium.com/@vptarasov)
