# KMP with custom Gradle plugins

> **Independent Gradle build** from the [Android Architecture Templates](../../../README.md)
> catalog. Open *this folder* in Android Studio / IntelliJ IDEA or run `./gradlew` here.
> It has its own Gradle wrapper and version catalog and does not depend on other samples.

| | |
|---|---|
| Catalog ID | `kmp-custom-plugins` |
| Status | Reference - a complete, self-contained implementation of an approach. |
| Architecture | MVI, multi-module |
| UI | Compose Multiplatform + Compose HTML (web) |
| Platforms | Android, iOS, Desktop, Web, Server |
| Key technologies | DI: Koin; network: Ktor Client; storage: SQLDelight + DataStore; navigation: Navigation Compose |
| Gradle modules | `:core`, `:shared`, `:mobile`, `:desktop`, `:web`, `:server`, `:kmp-secrets-plugin`, `:lint-checks`, `:custom-ktlint-rules` |
| Tool versions | Gradle 8.7, AGP 8.5.2, Kotlin 2.1.10 |
| JDK | 17 |
| Android SDK | compileSdk 34, targetSdk 34 (set `ANDROID_HOME` or `sdk.dir` in `local.properties`) |
| applicationId | `com.tarasovvp.architecturetemplates.kmp.customplugins` (changed from `com.vnteam.architecturetemplates` so samples can be installed side by side) |
| Article | [Automating Work with Secrets in Kotlin Multiplatform](https://medium.com/@vptarasov/automating-work-with-secrets-in-kotlin-multiplatform-a2b4c587180b) |

## Build, test, run

From this directory:

```sh
./gradlew :mobile:assembleDebug    # build
./gradlew :shared:jvmTest    # test
./gradlew :mobile:installDebug    # android
./gradlew :desktop:run    # desktop
./gradlew :web:jsBrowserDevelopmentRun    # web
./gradlew :server:run    # server
```

Or from the repository root: `./scripts/build-sample kmp-custom-plugins`, `./scripts/test-sample kmp-custom-plugins`,
`./scripts/run-sample kmp-custom-plugins <target>`.
- iOS: open `iosApp/` in Xcode (macOS only); it is not started from the command line.

## Environment variables

- `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (the server has built-in defaults; set them to point at your PostgreSQL)

## Secrets / `local.properties`

`local.properties` (sample root or module) with `CLOUD_URL=<server base url>`, read by the `kmp-secrets-plugin`. No real value is committed.

Never commit `local.properties`, keystores or real secrets.

## Known limitations

- `generateSecrets*` tasks fail without a `local.properties` file (create one with `CLOUD_URL=...`).
- Running `:server:run` needs PostgreSQL.

# KMP with Custom Plugins and Rules

Builds on the [tests branch](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_tests) by turning repeated build and code-quality chores into **custom, reusable tooling** that lives in the repository: a Gradle plugin for secrets, custom ktlint rules, a custom Android Lint check and a Git pre-commit hook. Ktlint and Detekt are wired into every module.

## Overview

- **Purpose:** show how to extend the toolchain of a KMP project instead of copy-pasting build logic and style conventions.
- **Approach:** small, separate modules (`kmp-secrets-plugin`, `custom-ktlint-rules`, `lint-checks`) plus root-level configuration applied to all subprojects.
- **Place in the evolution:** tests → **custom plugins** → [analysis tools](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_analyze_tools) → [CI/CD](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_ci_cd).

The application itself (Ktor server + multiplatform clients) is the same as in the tests branch.

## What the custom tooling does

### `kmp-secrets-plugin` — Gradle plugin

Generalises the `generateSecrets` logic of the previous branch (a module-local Gradle task plus a first in-repo plugin) into a configurable plugin.

- Reads key–value pairs from `local.properties` — the module's own file first, otherwise the root file — and generates a `Secrets` Kotlin object with `const val` fields (only keys that are valid Kotlin identifiers).
- Output goes to `<module>/src/commonMain/kotlin/secrets/Secrets.kt` by default; configurable through the `secrets { outputDir = … }` extension.
- For every Kotlin Multiplatform target compilation it registers a `generateSecrets<Target><Compilation>` task that the compile task depends on, and appends the generated file to `.gitignore` (`GitIgnoreUpdater`).
- Repetitive configuration removed: no per-module Gradle task that parses properties, writes the file and wires it into `preBuild`.
- The local module is declared as plugin `com.vnteam.kmp-secrets` (publishing to Maven Local). `shared` applies the **published** release (`io.github.tarasovvp.kmp-secrets-plugin` 1.1.0 from the version catalog) and uses the result in `ApiServiceImpl` as `Secrets.CLOUD_URL`. Source of the published plugin: [TarasovVP/KMP-Secrets-Plugin](https://github.com/TarasovVP/KMP-Secrets-Plugin).

### `custom-ktlint-rules` — ktlint rule set

A `RuleSetProviderV3` (`custom-ktlint-rules`, registered via `META-INF/services`) with two rules, covered by unit tests:

| Rule id | What it reports |
|---------|-----------------|
| `custom:magic-numbers-rule` | numeric literals other than `0`, `1`, `-1`, `1f`; `const val` and `Color(0xFF…)` literals are allowed |
| `custom:raw-string-rule` | raw string literals; empty strings, `const val`, annotation arguments and strings with templates are allowed |

The root build adds the module to the `ktlint` configuration of every subproject, so the rules run with `ktlintCheck`.

### `lint-checks` — Android Lint check

`UnresolvedComponentDetector` (issue `UnresolvedManifestClass`) reports `activity`, `service`, `receiver` and `provider` entries in `AndroidManifest.xml` whose class cannot be found. In the build files of this branch the module is **not wired** into an Android module (no `lintChecks(...)` dependency), so it is a standalone, not yet applied check.

### Code style and Git hook

- `.editorconfig` disables ktlint for generated/build files, tests, `secrets` and resource packages and allows `@Composable` function naming.
- `detekt.yml` (on top of Detekt's default config) tunes `LongMethod`, `LongParameterList`, `FunctionNaming`, `MagicNumber`, `MaxLineLength` for Compose and generated code. Detekt is applied to all subprojects with `ignoreFailures = false`.
- `pre-commit` script: for staged `.kt/.kts` files runs `ktlintFormat`, re-stages them, then `ktlintCheck` and `detekt`. The root build registers `installGitHook`, which copies it into `.git/hooks` before Kotlin compilation.

## Platforms

Android, iOS, Desktop (JVM), Web (Kotlin/JS) and a JVM server — the module layout is `core`, `shared`, `mobile`, `desktop`, `web`, `server` plus `kmp-secrets-plugin`, `lint-checks`, `custom-ktlint-rules`.

## Technology stack

| Area | Technology |
|------|------------|
| UI | Compose Multiplatform, Compose HTML |
| Networking | Ktor 2.3.12 |
| Serialization | kotlinx.serialization |
| Database | SQLDelight 2.0.2 |
| Concurrency | Kotlin coroutines and `Flow` |
| State management | MVI with `StateFlow` |
| Dependency injection | Koin 4.0 |
| Testing | `kotlin.test`, `kotlinx-coroutines-test`, `koin-test`; ktlint-test for rule tests |
| Code quality | ktlint (Gradle plugin 12.2.0, engine 1.0.0 for custom rules), Detekt 1.23.8, Kover 0.9.1 |
| Custom tooling | Gradle plugin (`kotlin-dsl`), ktlint rule set, Android Lint detector |
| Build | Gradle 8.7, AGP 8.5.2, Kotlin 2.1.10 |

## How to run

> Derived from the project configuration; **not executed** while preparing this documentation.

```bash
git clone --branch kmp_with_custom_plugins https://github.com/TarasovVP/Android-Architecture-Templates.git
cd Android-Architecture-Templates
# create local.properties (sdk.dir plus the keys used by the code, e.g. CLOUD_URL)

./gradlew ktlintCheck                 # style incl. custom rules
./gradlew ktlintFormat                # auto-format
./gradlew detekt                      # static analysis
./gradlew :custom-ktlint-rules:test   # tests of the custom rules
./gradlew :shared:jvmTest             # shared unit tests
./gradlew installGitHook              # install the pre-commit hook manually
```

## What this branch demonstrates

- Writing and consuming a custom Gradle plugin in a multi-module KMP build.
- Extending ktlint with project-specific rules and testing them.
- Writing an Android Lint detector (and why wiring is a separate step).
- Enforcing quality locally with a Git hook before CI exists.

## Related articles

- [Automating Work with Secrets in Kotlin Multiplatform](https://medium.com/@vptarasov/automating-work-with-secrets-in-kotlin-multiplatform-a2b4c587180b) — the secrets plugin.
- [Ktlint in KMP: Setup, Configuration, and Custom Rules](https://medium.com/@vptarasov/ktlint-in-kmp-setup-configuration-and-custom-rules-4b9f7ab86347) — ktlint setup, `.editorconfig` and custom rules.

## Other implementations

- [Repository overview](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md) · [All branches](https://github.com/TarasovVP/Android-Architecture-Templates/blob/master/README.md#all-branches)
- Previous: [KMP with tests](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_tests)
- Next: [Analysis tools](https://github.com/TarasovVP/Android-Architecture-Templates/tree/kmp_with_analyze_tools)
