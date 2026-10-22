# Android Architecture Templates

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

**One functional scenario, many architectural, platform and infrastructure implementations — each in its own branch.**

This repository is an architecture playground and showcase for Android and Kotlin Multiplatform (KMP). The same small feature is implemented again and again, changing one decision at a time: architecture pattern, UI toolkit, networking, persistence, concurrency, dependency injection, navigation, supported platforms, module structure, testing, code analysis, Gradle tooling and CI/CD. Because the functionality stays constant, the differences between branches show the effect of each technical choice.

> The `master` branch contains the catalog (this file) together with the latest and most complete KMP implementation. To see a specific approach, switch to the corresponding branch — see [How to open a branch](#how-to-open-a-branch).

## Contents

- [The shared scenario](#the-shared-scenario)
- [Evolution of the project](#evolution-of-the-project)
- [All branches](#all-branches)
- [Android implementations](#android-implementations)
- [Kotlin Multiplatform implementations](#kotlin-multiplatform-implementations)
- [Engineering and infrastructure](#engineering-and-infrastructure)
- [Articles](#articles)
- [How to open a branch](#how-to-open-a-branch)
- [Build requirements](#build-requirements)
- [Project status](#project-status)
- [Author](#author)

## The shared scenario

Every implementation is built around the same two-screen flow:

1. **Fetch** a list of items from an API. In the Android and early KMP branches this is the GitHub *forks of `octocat/Spoon-Knife`* endpoint; from [`kmp_with_server_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_server_implementation) on, the clients use the project's own Ktor server.
2. **Store / cache** the data locally (SQLite, ORMLite, Realm, Room or SQLDelight, depending on the branch).
3. **Show** the stored list on the first screen.
4. **Open** a details screen for the selected item (including the owner's avatar).

The server-based KMP branches extend the scenario with create / edit / delete, dark theme and language settings.

## Evolution of the project

The history is not a single linear chain; the diagrams below show how the branches relate.

### Android line

```
mvc_native_implementation            Activity-as-controller, HttpURLConnection, SQLiteDatabase, Handler+Thread   (historical)
        │
        ▼
mvc_async_task_implementation        + OkHttp, Moshi, ORMLite, ButterKnife, AsyncTask                             (historical)
        │
        ▼
mvp_implementation                   Presenter + view contracts, Volley, Realm, RxJava, Dagger 2
        │
        ▼
mvvm_implementation                  ViewModel + LiveData, Retrofit, Room, Hilt, Navigation component
        │
        ├──► mvvm_clean_architecture_implementation   same layering with a concrete domain naming (Fork)
        │
        └──► mvvm_compose_implementation              Compose UI hosted in Fragments
                    │
                    ▼
             mvvm_compose_navigation_implementation   Compose Navigation, StateFlow, Ktor, SQLDelight, Koin
                    │
                    ▼
             mvi_compose_navigation_implementation    MVI (state + intents), Voyager navigation
```

### Kotlin Multiplatform line

```
mvi_compose_navigation_implementation
        │
        ▼
kmm_implementation                         Android + iOS, shared Compose UI (KMM)
        │
        ▼
kmp_implementation                         + Desktop (JVM) + Web (Kotlin/JS)
        │
        ▼
kmp_with_server_implementation             + Ktor server, PostgreSQL, own API
        │
        ▼
kmp_with_multimodules_implementation       one module per platform app + core/shared
        │                       │
        │                       └──► kmp_with_git_submodules_implementation   (side branch: shared + web as Git submodules)
        ▼
kmp_with_tests                             unit tests in commonTest, Kover
        │
        ▼
kmp_with_custom_plugins                    secrets Gradle plugin, custom ktlint rules, Lint check, pre-commit hook
        │
        ▼
kmp_with_analyze_tools                     Detekt, Kover, SonarQube, GitHub Actions, build cache
        │
        ▼
kmp_with_ci_cd                             Jenkins + Fastlane, benchmarks, baseline profiles   (= master)
```

> Branches were created over time and some started from earlier points than the diagram suggests; the arrows show the *conceptual* sequence. In particular, `kmp_with_git_submodules_implementation` is not an ancestor of the branches that follow the multi-module branch.

## All branches

Status legend: **Historical** — kept to show how Android evolved, not recommended for new projects; **Reference** — a complete, self-contained implementation of an approach; **Latest** — the most current and feature-complete state.

| Branch | Category | Architecture | UI | Platforms | Main focus | Related article | Status |
|--------|----------|--------------|----|-----------|------------|-----------------|--------|
| [`master`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/master) | Overview + KMP | MVI, layered, multi-module | Compose Multiplatform, Compose HTML | Android, iOS, Desktop, Web, Server | This catalog; same code as `kmp_with_ci_cd` | [KMP: Modular Architecture](https://medium.com/@vptarasov/kmp-modular-architecture-faabbdf58197) | Latest |
| [`mvc_native_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvc_native_implementation) | Android | MVC | XML Views | Android | Platform APIs only: `HttpURLConnection`, SQLite, `Handler` + `Thread` | — | Historical |
| [`mvc_async_task_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvc_async_task_implementation) | Android | MVC | XML Views | Android | OkHttp, Moshi, ORMLite, ButterKnife, `AsyncTask` | — | Historical |
| [`mvp_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvp_implementation) | Android | MVP | XML Views + Fragments | Android | Presenters, view contracts, RxJava, Realm, Dagger 2 | — | Reference |
| [`mvvm_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_implementation) | Android | MVVM (layered) | XML Views + Fragments | Android | ViewModel, LiveData, Retrofit, Room, Hilt | — | Reference |
| [`mvvm_clean_architecture_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_clean_architecture_implementation) | Android | MVVM + Clean Architecture | XML Views + Fragments | Android | Domain / data / presentation layers with `Fork*` naming | — | Reference |
| [`mvvm_compose_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_implementation) | Android | MVVM | Jetpack Compose in Fragments | Android | Migration from Views to Compose | — | Reference |
| [`mvvm_compose_navigation_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_navigation_implementation) | Android | MVVM | Jetpack Compose | Android | Compose Navigation, `StateFlow`, Ktor, SQLDelight, Koin | [SQLDelight in KMP](https://medium.com/@vptarasov/sqldelight-in-kotlin-multiplatform-steps-to-implementation-bc930029de69) (topic) | Reference |
| [`mvi_compose_navigation_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvi_compose_navigation_implementation) | Android | MVI | Jetpack Compose | Android | Unidirectional data flow, Voyager navigation | — | Reference |
| [`kmm_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmm_implementation) | KMP | MVI | Compose Multiplatform + SwiftUI host | Android, iOS | First shared-code version (Kotlin Multiplatform Mobile) | [Network Requests with Ktor](https://medium.com/@vptarasov/kmp-network-requests-with-ktor-498d0a7b544e) (topic) | Historical naming |
| [`kmp_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_implementation) | KMP | MVI | Compose Multiplatform, Compose HTML | Android, iOS, Desktop, Web | Four platform families from one code base | [Dependency Injection with Koin](https://medium.com/@vptarasov/kmp-dependency-injection-with-koin-d1f442f87cee) (topic) | Reference |
| [`kmp_with_server_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_server_implementation) | KMP + backend | MVI + Ktor server | Compose Multiplatform, Compose HTML | Android, iOS, Desktop, Web, Server | Ktor, PostgreSQL, Docker, Cloud Run | [KMP: Server Implementation](https://medium.com/@vptarasov/kmp-server-implementation-49a0a4389b34), [Navigation in a Web Application](https://medium.com/@vptarasov/navigation-in-a-web-application-with-kotlin-multiplatform-2ac625b0ea04), [Deploying a KMP Server on Google Cloud](https://medium.com/@vptarasov/deploying-a-kmp-server-on-google-cloud-step-by-step-guide-f32ade8c4be3) | Reference |
| [`kmp_with_multimodules_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_multimodules_implementation) | KMP + structure | MVI, multi-module | Compose Multiplatform, Compose HTML | Android, iOS, Desktop, Web, Server | `core` / `shared` / `mobile` / `desktop` / `web` / `server` modules | [KMP: Modular Architecture](https://medium.com/@vptarasov/kmp-modular-architecture-faabbdf58197) | Reference |
| [`kmp_with_git_submodules_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_git_submodules_implementation) | KMP + structure | MVI, multi-repo | Compose Multiplatform, Compose HTML | Android, iOS, Desktop, Web, Server | `shared` and `web` as Git submodules | [Migration to Git Submodules Architecture](https://medium.com/@vptarasov/migration-to-git-submodules-architecture-in-a-kmp-project-4a73ec4a2c9e) | Reference (side branch) |
| [`kmp_with_tests`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_tests) | KMP + testing | MVI, multi-module | Compose Multiplatform, Compose HTML | Android, iOS, Desktop, Web, Server | `commonTest`, fakes, Koin test, Kover | [Unit Testing in Kotlin Multiplatform](https://medium.com/@vptarasov/unit-testing-in-kotlin-multiplatform-fb3ca7d5c869) | Reference |
| [`kmp_with_custom_plugins`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_custom_plugins) | KMP + tooling | MVI, multi-module | Compose Multiplatform, Compose HTML | Android, iOS, Desktop, Web, Server | Secrets Gradle plugin, custom ktlint rules, Lint check, Git hook | [Automating Work with Secrets](https://medium.com/@vptarasov/automating-work-with-secrets-in-kotlin-multiplatform-a2b4c587180b), [Ktlint in KMP](https://medium.com/@vptarasov/ktlint-in-kmp-setup-configuration-and-custom-rules-4b9f7ab86347) | Reference |
| [`kmp_with_analyze_tools`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_analyze_tools) | KMP + quality | MVI, multi-module | Compose Multiplatform, Compose HTML | Android, iOS, Desktop, Web, Server | Detekt, Kover, SonarQube, GitHub Actions, build cache | [Code Quality Control in KMP with SonarQube](https://medium.com/@vptarasov/code-quality-control-in-kmp-with-sonarqube-1237ec4f3a4b) | Reference |
| [`kmp_with_ci_cd`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_ci_cd) | KMP + delivery | MVI, multi-module | Compose Multiplatform, Compose HTML | Android, iOS, Desktop, Web, Server | Jenkins + Fastlane, benchmarks, baseline profiles | [Code Quality Control in KMP with SonarQube](https://medium.com/@vptarasov/code-quality-control-in-kmp-with-sonarqube-1237ec4f3a4b) (analysis stage) | Latest |

"(topic)" means the article covers a technology used in that branch, but is not a walkthrough of the branch. Branches marked "—" have no dedicated article.

## Android implementations

Eight branches follow the architecture and tooling trends of Android development. All of them are Android-only.

- **MVC** — [native](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvc_native_implementation) and [with AsyncTask](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvc_async_task_implementation). Historical: activities act as controllers, background work is done by hand or with the deprecated `AsyncTask`. They are useful as a baseline.
- **MVP** — [`mvp_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvp_implementation). Presenter and view contracts, RxJava, Realm and Dagger 2.
- **MVVM** — [`mvvm_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_implementation) (Jetpack ViewModel/LiveData, Hilt, Room, Retrofit) and [`mvvm_clean_architecture_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_clean_architecture_implementation) (the same layering with concrete domain naming).
- **Compose** — [`mvvm_compose_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_implementation) (Compose inside Fragments) and [`mvvm_compose_navigation_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvvm_compose_navigation_implementation) (Compose Navigation, Ktor, SQLDelight, Koin).
- **MVI** — [`mvi_compose_navigation_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/mvi_compose_navigation_implementation). Immutable state, intents and Voyager navigation.

## Kotlin Multiplatform implementations

- [`kmm_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmm_implementation) — Android and iOS with shared logic and UI. "KMM" (Kotlin Multiplatform Mobile) is the historical name of this mobile-only use case; the branch keeps it.
- [`kmp_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_implementation) — adds Desktop (JVM) and Web (Kotlin/JS; a canvas build using the Skiko WebAssembly runtime and a Compose HTML build). There is no `wasmJs` Gradle target in the repository.
- [`kmp_with_server_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_server_implementation) — adds a Ktor + PostgreSQL server with REST API and a Docker/Cloud Run setup.
- [`kmp_with_multimodules_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_multimodules_implementation) — one Gradle module per platform application and shared libraries.
- [`kmp_with_git_submodules_implementation`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_git_submodules_implementation) — the same structure with `shared` and `web` extracted into separate repositories.

## Engineering and infrastructure

These branches keep the application constant and add engineering practices to the multi-module KMP project:

| Branch | What it adds |
|--------|--------------|
| [`kmp_with_tests`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_tests) | Unit tests of repositories, use cases and ViewModels in `commonTest`; fakes; Kover coverage |
| [`kmp_with_custom_plugins`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_custom_plugins) | `kmp-secrets-plugin`, custom ktlint rules, Android Lint detector, Detekt and ktlint wiring, pre-commit hook |
| [`kmp_with_analyze_tools`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_analyze_tools) | SonarQube aggregation of Detekt and Kover reports, GitHub Actions workflow, build cache and build scans, shared `composeUI` module |
| [`kmp_with_ci_cd`](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_ci_cd) | Extended GitHub workflow (tests, benchmarks, baseline profile), Jenkins + Fastlane release bundle, Macrobenchmark, Baseline Profile, dependency analysis |

The secrets plugin is also published separately: [TarasovVP/KMP-Secrets-Plugin](https://github.com/TarasovVP/KMP-Secrets-Plugin). The two repositories used as Git submodules are [architecture_template_shared](https://github.com/TarasovVP/architecture_template_shared) and [architecture_template_web](https://github.com/TarasovVP/architecture_template_web).

## Articles

Articles by the author on [Medium](https://medium.com/@vptarasov) related to this repository:

| Article | Most relevant branch(es) |
|---------|--------------------------|
| [Kotlin Multiplatform — Now Supported by Google](https://medium.com/@vptarasov/kotlin-multiplatform-now-supported-by-google-2874dd2d69b7) | Background for the whole KMP line |
| [SQLDelight in Kotlin Multiplatform: Steps to Implementation](https://medium.com/@vptarasov/sqldelight-in-kotlin-multiplatform-steps-to-implementation-bc930029de69) | `mvvm_compose_navigation_implementation`, `kmm_implementation`, `kmp_implementation` (topic) |
| [KMP: Network Requests with Ktor](https://medium.com/@vptarasov/kmp-network-requests-with-ktor-498d0a7b544e) | `kmm_implementation`, `kmp_implementation` (topic) |
| [KMP: Dependency Injection with Koin](https://medium.com/@vptarasov/kmp-dependency-injection-with-koin-d1f442f87cee) | `kmp_implementation` (topic; links to this repository) |
| [KMP: Server Implementation](https://medium.com/@vptarasov/kmp-server-implementation-49a0a4389b34) | `kmp_with_server_implementation` |
| [Navigation in a Web Application with Kotlin Multiplatform](https://medium.com/@vptarasov/navigation-in-a-web-application-with-kotlin-multiplatform-2ac625b0ea04) | `kmp_with_server_implementation` (web module) |
| [Deploying a KMP Server on Google Cloud: Step-by-Step Guide](https://medium.com/@vptarasov/deploying-a-kmp-server-on-google-cloud-step-by-step-guide-f32ade8c4be3) | `kmp_with_server_implementation` |
| [KMP: Modular Architecture](https://medium.com/@vptarasov/kmp-modular-architecture-faabbdf58197) | `kmp_with_multimodules_implementation` |
| [Migration to Git Submodules Architecture in a KMP Project](https://medium.com/@vptarasov/migration-to-git-submodules-architecture-in-a-kmp-project-4a73ec4a2c9e) | `kmp_with_git_submodules_implementation` |
| [Unit Testing in Kotlin Multiplatform](https://medium.com/@vptarasov/unit-testing-in-kotlin-multiplatform-fb3ca7d5c869) | `kmp_with_tests` |
| [Automating Work with Secrets in Kotlin Multiplatform](https://medium.com/@vptarasov/automating-work-with-secrets-in-kotlin-multiplatform-a2b4c587180b) | `kmp_with_custom_plugins` |
| [Ktlint in KMP: Setup, Configuration, and Custom Rules](https://medium.com/@vptarasov/ktlint-in-kmp-setup-configuration-and-custom-rules-4b9f7ab86347) | `kmp_with_custom_plugins`, `kmp_with_analyze_tools` |
| [Code Quality Control in KMP with SonarQube](https://medium.com/@vptarasov/code-quality-control-in-kmp-with-sonarqube-1237ec4f3a4b) | `kmp_with_analyze_tools`, `kmp_with_ci_cd` |

## How to open a branch

Browse a branch on GitHub with the branch selector, or use a direct link such as
`https://github.com/TarasovVP/Android-Architecture-Templates/tree/<branch>`.

Clone a single branch:

```bash
git clone --branch <branch> --single-branch https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
```

Switch in an existing clone:

```bash
git fetch origin
git switch <branch>
```

`kmp_with_git_submodules_implementation` needs submodules: `git clone --recurse-submodules --branch kmp_with_git_submodules_implementation …` (or `git submodule update --init --recursive` afterwards).

Every branch has its own `README.md` with the architecture, technology stack, project structure and run instructions for that implementation.

## Build requirements

Requirements differ per branch; check the branch README. In general:

| Group | Requirements |
|-------|--------------|
| Android branches | Android Studio, Android SDK (33 for MVC/MVP, 34 for MVVM/MVI), JDK 17 for the MVVM / Compose / MVI branches, emulator or device, Internet access |
| `kmm_` / `kmp_` branches | JDK 17+, Android SDK; macOS with Xcode for iOS targets; Node.js/Yarn are downloaded by Gradle for the web targets |
| Server branches | JDK 17+; a PostgreSQL database for `:server` (configured through environment variables) |
| Tests / plugins / analysis / CI/CD branches | A `local.properties` file with the keys read by the secrets plugin (for example `CLOUD_URL`); Sonar token for `sonar`; signing values and keystore for release builds. `master` / `kmp_with_ci_cd` use `compileSdk 36`, Gradle 8.11.1 and compile some modules for JVM 21, so use JDK 21 as CI does |

Build commands in the branch READMEs are derived from the project configuration and have **not** all been executed; each README says so explicitly.

## Project status

This is a personal **architecture playground and showcase**. The implementations are maintained as reference examples, not as a production library or a framework:

- Branches are snapshots; dependency versions differ between them and older branches are not kept up to date.
- Deprecated technologies (`AsyncTask`, Volley, Realm, KMM naming) are kept deliberately and marked as historical.
- Default URLs, database settings and secrets in the code are placeholders or demo values; provide your own configuration.

Issues and suggestions are welcome through GitHub issues.

## Author

**Volodymyr Tarasov** — [GitHub](https://github.com/TarasovVP) · [Medium](https://medium.com/@vptarasov)
