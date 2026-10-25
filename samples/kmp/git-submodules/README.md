# KMP with Git submodules

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `kmp-git-submodules` |
| Source branch | `kmp_with_git_submodules_implementation` (commit `e8ee3e40`) |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVI, multi-repo |
| UI | Compose Multiplatform + Compose HTML (web) |
| Platforms | Android, iOS, Desktop, Web, Server |
| Key technologies | DI: Koin; network: Ktor Client; storage: SQLDelight + DataStore; navigation: Navigation Compose |
| Gradle modules | `:core`, `:shared`, `:mobile`, `:desktop`, `:web:web`, `:server` |
| Tool versions | Gradle 8.7, AGP 8.5.2, Kotlin 2.0.20 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.kmp.gitsubmodules` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | [Migration to Git Submodules Architecture in a KMP Project](https://medium.com/@vptarasov/migration-to-git-submodules-architecture-in-a-kmp-project-4a73ec4a2c9e) |

## Build, test, run

From this directory:

```sh
./gradlew :mobile:assembleDebug    # build
./gradlew :shared:testDebugUnitTest    # test
./gradlew :mobile:installDebug    # android
./gradlew :desktop:run    # desktop
./gradlew :web:web:jsBrowserDevelopmentRun    # web
./gradlew :server:run    # server
```

Or from the repository root: `./scripts/build-sample kmp-git-submodules`, `./scripts/test-sample kmp-git-submodules`,
`./scripts/run-sample kmp-git-submodules <target>`.
- iOS: open `iosApp/` in Xcode (macOS only); it is not started from the command line.

## Environment variables

- `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (the server has built-in defaults; set them to point at your PostgreSQL)

## Secrets / `local.properties`

none

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- `shared` and `web` are vendored copies of the original submodules (see ORIGINAL_SUBMODULES.md); the real submodule setup exists only on the source branch.
- Running `:server:run` needs PostgreSQL. iOS needs macOS with Xcode.

The original README of the source branch follows unchanged.

<!-- catalog:original-readme -->

# KMP with Git Submodules

A variant of the [multi-module KMP project](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_multimodules_implementation) in which two parts of the code base — the **`shared`** module and the **`web`** application — live in their own Git repositories and are attached to this repository as **Git submodules**.

## Overview

- **Purpose:** show how to split a KMP project across repositories (independent history, release cadence, access control or CI per part) while keeping a single Gradle build.
- **Approach:** the multi-module layout is preserved; `shared` and `web` directories are submodule checkouts pinned to specific commits.
- **Place in the history:** a side branch. It starts from the same code as the multi-module branch (initial commit "Init commit", 2025-01-19) but the later branches (tests, plugins, analysis, CI/CD) continue from the multi-module branch, **not** from this one.

> **Important:** a plain `git clone` leaves the `shared/` and `web/` directories empty. Clone with `--recurse-submodules` (see below).

## Functionality

The same scenario as the multi-module branch: list items from a Ktor server with a local cache, delete, details, create/edit, theme and language settings. Application logic is in the `shared` submodule.

## Platforms

Android and iOS (`mobile`), Desktop (`desktop`), Web (`web`, Kotlin/JS with Compose HTML) and a JVM `server`.

## Submodules

Declared in `.gitmodules`:

| Path | Repository | Pinned commit | Contents |
|------|------------|---------------|----------|
| `shared` | [TarasovVP/architecture_template_shared](https://github.com/TarasovVP/architecture_template_shared) | `45fbbdb` | The KMP `shared` module (`build.gradle.kts` + `src`): domain, data, presentation, Koin, platform source sets. The former `core` models/DTOs are included here. |
| `web` | [TarasovVP/architecture_template_web](https://github.com/TarasovVP/architecture_template_web) | `60cede9` | A standalone Gradle project that contains the `web` module (and itself references `shared` as a submodule). |

In the main repository stay: `mobile`, `desktop`, `server`, `iosApp`, the Gradle build files and the version catalog.

`settings.gradle.kts` includes `:core`, `:shared`, `:mobile`, `:desktop`, `:web:web` and `:server`. The `:web:web` path reflects that the `web` submodule is a project with a nested `web` module. **Verify:** the `:core` entry is still present although no `core/` directory exists in this branch (its content moved into the `shared` submodule); this has not been checked with a build.

## Module dependencies

```
mobile ─┐
desktop ├──► shared (submodule) ──► (models, DTOs, logic)
web:web ┘     ▲
              └── also pulled in by the web submodule project
server (in-repo, Ktor + PostgreSQL)
```

## Technology stack

Identical to the [multi-module branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_multimodules_implementation): Compose Multiplatform / Compose HTML, Ktor, SQLDelight, DataStore, Koin, kotlinx.serialization, MVI with `StateFlow`, Gradle 8.7 and Kotlin 2.0.20.

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation (submodule contents are not part of this repository's tree).

```bash
git clone --recurse-submodules --branch kmp_with_git_submodules_implementation \
  https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates

# if you cloned without submodules
git submodule update --init --recursive
```

Then build as in the multi-module branch, e.g. `./gradlew :mobile:installDebug`, `./gradlew :desktop:run`, `./gradlew :server:run`. To move a submodule to a newer commit, `cd shared && git pull origin main`, then commit the updated pointer in the main repository.

## What this branch demonstrates

- Extracting modules into separate repositories without rewriting the project structure.
- Pinning dependencies by commit through submodule pointers.
- Trade-offs: extra clone/update steps, two-step commits (submodule, then pointer), and path adjustments such as `:web:web`.

## Related article

- [Migration to Git Submodules Architecture in a KMP Project](https://medium.com/@vptarasov/migration-to-git-submodules-architecture-in-a-kmp-project-4a73ec4a2c9e) — describes moving the web module and then `shared` into their own repositories.
- Background: [KMP: Modular Architecture](https://medium.com/@vptarasov/kmp-modular-architecture-faabbdf58197).

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Base: [Multi-module KMP](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_multimodules_implementation)
- Mainline continues with: [KMP with tests](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_tests)
