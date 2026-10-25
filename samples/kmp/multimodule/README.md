# KMP multi-module

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `kmp-multimodule` |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVI, multi-module |
| UI | Compose Multiplatform + Compose HTML (web) |
| Platforms | Android, iOS, Desktop, Web, Server |
| Key technologies | DI: Koin; network: Ktor Client; storage: SQLDelight + DataStore; navigation: Navigation Compose |
| Gradle modules | `:core`, `:shared`, `:mobile`, `:desktop`, `:web`, `:server` |
| Tool versions | Gradle 8.7, AGP 8.5.2, Kotlin 2.0.20 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.kmp.multimodule` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | [KMP: Modular Architecture](https://medium.com/@vptarasov/kmp-modular-architecture-faabbdf58197) |

## Build, test, run

From this directory:

```sh
./gradlew :mobile:assembleDebug    # build
./gradlew :shared:testDebugUnitTest    # test
./gradlew :mobile:installDebug    # android
./gradlew :desktop:run    # desktop
./gradlew :web:jsBrowserDevelopmentRun    # web
./gradlew :server:run    # server
```

Or from the repository root: `./scripts/build-sample kmp-multimodule`, `./scripts/test-sample kmp-multimodule`,
`./scripts/run-sample kmp-multimodule <target>`.
- iOS: open `iosApp/` in Xcode (macOS only); it is not started from the command line.

## Environment variables

- `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (the server has built-in defaults; set them to point at your PostgreSQL)

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- Running `:server:run` needs PostgreSQL. iOS needs macOS with Xcode.

# KMP with a Multi-Module Structure

Restructures the [KMP-with-server project](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_server_implementation) into **one Gradle module per platform application** plus shared libraries. The goal is clear module boundaries: each platform app depends on `shared`, `shared` depends on `core`, and the server depends only on `core`.

## Overview

- **Purpose:** demonstrate modular architecture for Kotlin Multiplatform and show how to split the earlier `composeApp`/`composeUi`/`webApp` layout into focused modules.
- **Approach:** the same MVI client + Ktor server as before, reorganised into `core`, `shared`, `mobile`, `desktop`, `web` and `server` modules.
- **Place in the evolution:** KMP with server → **multi-module** → [tests](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_tests) (the mainline continues from here). The [Git submodules variant](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_git_submodules_implementation) is a side branch that starts from the same code.

## Functionality

The scenario of the server branch: list items from the server with a local cache, delete items, open details, create or edit an item, switch dark theme and language (en / uk), text-to-speech helper. The server exposes `GET/POST /demoObjects`, `GET/DELETE /demoObjects/{id}` (see the [server branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_server_implementation) for details).

## Platforms

| Platform | Module |
|----------|--------|
| Android, iOS | `mobile` (+ `iosApp` Xcode host) |
| Desktop (JVM; Dmg / Msi / Deb packaging configured) | `desktop` |
| Web (Kotlin/JS, Compose HTML) | `web` |
| Server (JVM, Ktor) | `server` |

## Module structure and dependencies

```
              ┌────────┐
              │  core  │  models, DTOs, mapper contracts (Android, iOS, JVM, JS)
              └───┬────┘
        ┌─────────┴─────────┐
   ┌────▼─────┐        ┌────▼─────┐
   │  shared  │        │  server  │  Ktor + PostgreSQL
   └────┬─────┘        └──────────┘
        │  (domain, data, ViewModels, intents/states, Compose screens, Koin)
  ┌─────┼──────────┐
┌─▼───┐ ┌▼───────┐ ┌▼────┐
│mobile│ │desktop │ │ web │
└──────┘ └────────┘ └─────┘
```

| Module | Plugins / targets | Responsibility |
|--------|-------------------|----------------|
| `core` | KMP library: `androidTarget`, iOS, `jvm`, `js` | Domain models, response DTOs, base mappers — shared by clients and server |
| `shared` | KMP library + Compose + SQLDelight: Android, iOS, JVM, JS | Use cases, repositories, Ktor `ApiService`, SQLDelight cache, DataStore preferences, ViewModels, Compose screens and theme, Koin `AppModule`; platform source sets for drivers |
| `mobile` | KMP application: `androidTarget` + iOS frameworks | `MainActivity`, `AppApplication`, iOS `MainViewController`, mobile `AppNavigation` and screen content |
| `desktop` | Compose Desktop (`jvm("desktop")`), `mainClass = …MainKt` | Window, desktop navigation and screens |
| `web` | `js(IR)` + Compose HTML | DOM-based UI, URL navigation, `index.html` |
| `server` | Kotlin JVM + Ktor plugin + SQLDelight (PostgreSQL) | REST API, service, mappers, Dockerfile, `fatJar` |

Boundary rules that are visible in the build files: platform modules never depend on each other, only on `shared`; the `server` module does not depend on `shared`, `mobile`, `desktop` or `web`.

## Technology stack

| Area | Technology |
|------|------------|
| UI | Compose Multiplatform (mobile, desktop), Compose HTML (web) |
| Networking | Ktor 2.3.12 (client and server) |
| Image loading | Coil 3 (alpha) |
| Serialization | kotlinx.serialization |
| Database | SQLDelight 2.0.2 (client drivers; PostgreSQL dialect + HikariCP on server) |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | MVI with `StateFlow` |
| Navigation | JetBrains `navigation-compose` |
| Dependency injection | Koin 4.0 |
| Testing | Template tests per module |
| Build | Gradle 8.7, AGP 8.5.2, Kotlin 2.0.20, version catalog, type-safe project accessors (`projects.shared`) |

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation. JDK 17+; iOS needs macOS + Xcode.

```bash
git clone --branch kmp_with_multimodules_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
./gradlew :mobile:installDebug                 # Android
./gradlew :desktop:run                         # Desktop
./gradlew :web:jsBrowserDevelopmentRun         # Web
./gradlew :server:run                          # Server (requires PostgreSQL settings, see server branch)
```

## What this branch demonstrates

- Splitting a KMP project by platform application and by shared library.
- A one-way dependency graph enforced by Gradle modules instead of package conventions.
- Reusing `core` between clients and the server.
- Faster, more targeted builds and clearer ownership per module.

## Related articles

- [KMP: Modular Architecture](https://medium.com/@vptarasov/kmp-modular-architecture-faabbdf58197) — documents the modular structure of this project.
- [KMP: Server Implementation](https://medium.com/@vptarasov/kmp-server-implementation-49a0a4389b34) — the server module (introduced in the previous branch).

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [KMP with server](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_server_implementation)
- Next: [KMP with tests](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_tests) · Variant: [Git submodules](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_git_submodules_implementation)
