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
