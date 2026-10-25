# KMP with Ktor server

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `kmp-with-server` |
| Source branch | `kmp_with_server_implementation` (commit `5ecf0b82`) |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVI + Ktor server |
| UI | Compose Multiplatform + Compose HTML |
| Platforms | Android, iOS, Desktop, Web, Server |
| Key technologies | DI: Koin; network: Ktor Client + Ktor Server; storage: SQLDelight + DataStore + PostgreSQL (server); navigation: Navigation Compose |
| Gradle modules | `:shared`, `:core`, `:composeUi`, `:composeApp`, `:composeWebApp`, `:server`, `:webApp` |
| Tool versions | Gradle 8.7, AGP 8.5.2, Kotlin 2.0.20 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.kmp.withserver` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | [KMP: Server Implementation](https://medium.com/@vptarasov/kmp-server-implementation-49a0a4389b34) |

## Build, test, run

From this directory:

```sh
./gradlew :composeApp:assembleDebug    # build
./gradlew :shared:testDebugUnitTest    # test
./gradlew :composeApp:installDebug    # android
./gradlew :composeApp:run    # desktop
./gradlew :composeWebApp:jsBrowserDevelopmentRun    # web
./gradlew :server:run    # server
```

Or from the repository root: `./scripts/build-sample kmp-with-server`, `./scripts/test-sample kmp-with-server`,
`./scripts/run-sample kmp-with-server <target>`.
- iOS: open `iosApp/` in Xcode (macOS only); it is not started from the command line.

## Environment variables

- `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (the server has built-in defaults; set them to point at your PostgreSQL)

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- Running `:server:run` needs PostgreSQL (see environment variables). iOS needs macOS with Xcode.

The original README of the source branch follows unchanged.

<!-- catalog:original-readme -->

# KMP with a Ktor Server

Extends the [KMP branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_implementation) with the project's **own backend**: a Ktor server with a PostgreSQL database, written in Kotlin and sharing its models with the clients. The clients stop calling the GitHub API and talk to this server instead, with a local SQLDelight database as a cache.

## Overview

- **Purpose:** show a full-stack Kotlin setup — multiplatform clients and a JVM server in one Gradle project, sharing a `core` module.
- **Approach:** MVI clients (as in the KMP branch) plus a small layered server (routes → service → SQLDelight/PostgreSQL).
- **Place in the evolution:** KMP → **KMP with server** → [multi-module KMP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_multimodules_implementation).

## Functionality

Compared with the base scenario this branch has full CRUD over the server data:

1. **List** — loads items from the server (`GET /demoObjects`), stores them in the local database and renders the stored list; items can be deleted (`DELETE /demoObjects/{id}`).
2. **Details** — shows an item with its owner and avatar.
3. **Create / edit** — a form screen that sends a new or changed item to the server (`POST /demoObjects`).
4. **Settings** — dark theme and language (en / uk) stored in DataStore preferences; a text-to-speech helper is provided per platform.

## Platforms

Android, iOS, Desktop (JVM), Web (Kotlin/JS) and a **JVM server**. As in the KMP branch there is no `wasmJs` target; the canvas-based web UI is a Kotlin/JS build using the Skiko WebAssembly runtime.

## Architecture

| Module | Responsibility |
|--------|----------------|
| `core` | Multiplatform models (`DemoObject`, `Owner`), response DTOs and mapper contracts shared by clients **and** server |
| `shared` | Client domain/data/presentation: use cases, Ktor `ApiService`, SQLDelight cache, preferences, ViewModels (intents, states), Koin modules, platform source sets |
| `composeUi` | Compose Multiplatform screens and components reused by the app targets (list, details, create, theme) |
| `composeApp` | Application entry points: Android, desktop, iOS framework, canvas web build |
| `composeWebApp` | Web application using `composeUi` (with URL navigation) |
| `webApp` | Compose HTML (DOM) web application |
| `server` | Ktor + Netty server |
| `iosApp` | SwiftUI host |

### Server

- **Framework:** Ktor server (Netty engine, ContentNegotiation with kotlinx.serialization JSON, CORS), DI with Koin.
- **API:** `POST /demoObjects` (insert/upsert list), `GET /demoObjects`, `GET /demoObjects/{id}`, `DELETE /demoObjects/{id}`.
- **Layers:** `http/Routes.kt` → `DemoObjectService` / `DemoObjectServiceImpl` → SQLDelight queries (`ServerDatabase.sq`, PostgreSQL dialect) → HikariCP pool.
- **Database:** PostgreSQL, table `DemoObjectWithOwner` with `ON CONFLICT … DO UPDATE` upsert. The JDBC driver can be configured with environment variables (`JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD`); the defaults in `Constants.kt` are placeholders for a Google Cloud SQL instance.
- **Packaging:** `fatJar` Gradle task and a `Dockerfile` that runs `ktor-server-1.0.0.jar` on port 8080 (`PORT` env variable is honoured). The server was deployed to Google Cloud Run (see the article below).

Clients read the server address from `shared/.../data/Constants.kt` (`LOCAL_HOST_URL`, `CLOUD_URL`); change it to your own deployment.

## Technology stack

| Area | Technology |
|------|------------|
| UI | Compose Multiplatform (Material 3), Compose HTML |
| Networking | Ktor client (clients), Ktor server + Netty |
| Image loading | Coil 3 (alpha) |
| Serialization | kotlinx.serialization |
| Database | SQLDelight 2.0.2 — platform-specific drivers on clients (incl. sql.js web worker), PostgreSQL dialect + HikariCP on server |
| Preferences | AndroidX DataStore |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | MVI with `StateFlow` |
| Navigation | JetBrains `navigation-compose`; route objects in `NavigationScreens` |
| Dependency injection | Koin 4.0 (clients and server via `koin-ktor`) |
| Logging (server) | Logback |
| Testing | Template tests only (`ktor-server-tests` dependency is declared) |
| Build | Gradle 8.7 (Kotlin DSL, version catalog), AGP 8.5.2, Kotlin 2.0.20 |

## Project structure

```
core/         shared models + DTOs
shared/       client logic (commonMain + android/ios/jvm/js/native)
composeUi/    shared Compose screens
composeApp/   android · desktop · ios · js entry points
composeWebApp/ webApp/   web applications
server/       Ktor server, SQLDelight schema, Dockerfile
iosApp/       Xcode project
```

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation. JDK 17+ required.

```bash
git clone --branch kmp_with_server_implementation https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates

# Server (needs a reachable PostgreSQL database; set the DB environment variables first)
./gradlew :server:run
./gradlew :server:fatJar            # build the deployable jar

# Clients (point Constants.kt at your server)
./gradlew :composeApp:installDebug  # Android
./gradlew :composeApp:run           # Desktop
./gradlew :composeWebApp:jsBrowserDevelopmentRun   # Web
```

## What this branch demonstrates

- A Kotlin backend sharing models with multiplatform clients.
- Server-side SQLDelight with a PostgreSQL dialect.
- Containerisation and cloud deployment of a Ktor service.
- Client-side offline cache on top of a remote API.

## Related articles

- [KMP: Server Implementation](https://medium.com/@vptarasov/kmp-server-implementation-49a0a4389b34) — documents this branch.
- [Navigation in a Web Application with Kotlin Multiplatform](https://medium.com/@vptarasov/navigation-in-a-web-application-with-kotlin-multiplatform-2ac625b0ea04) — web navigation in the `composeWebApp` module.
- [Deploying a KMP Server on Google Cloud: Step-by-Step Guide](https://medium.com/@vptarasov/deploying-a-kmp-server-on-google-cloud-step-by-step-guide-f32ade8c4be3) — Cloud SQL, Docker and Cloud Run deployment of the `server` module.

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [KMP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_implementation)
- Next: [Multi-module KMP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_multimodules_implementation)
