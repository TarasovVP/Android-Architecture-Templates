# Build requirements

All values are read from the files of the corresponding sample (wrapper properties,
`gradle/libs.versions.toml`, root and module build scripts, settings) and mirrored in
`sample.json` / `catalog/samples.json`. They are **not** normalised between samples on purpose.
Flags such as "needs Docker" mean: needed for at least one documented target (for example to
run the server), not for the default build task.

## Toolchain

| Sample | Gradle | AGP | Kotlin | UI toolkit versions | JDK | minSdk / compileSdk / targetSdk | Android SDK components |
|---|---|---|---|---|---|---|---|
| `android-mvc-native` | 7.5 | 7.4.1 | 1.8.0 | - | 17 | 24 / 33 / 33 | platform `android-33`, build-tools picked by AGP |
| `android-mvc-async-task` | 7.5 | 7.4.1 | 1.8.0 | - | 17 | 24 / 33 / 33 | platform `android-33`, build-tools picked by AGP |
| `android-mvp` | 7.5 | 7.4.1 | 1.8.0 | - | 17 | 24 / 33 / 33 | platform `android-33`, build-tools picked by AGP |
| `android-mvvm` | 8.4 | 8.3.0 | 1.9.23 | - | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `android-mvvm-clean` | 8.4 | 8.3.0 | 1.9.23 | - | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `android-mvvm-compose` | 8.4 | 8.3.0 | 1.9.23 | Compose BOM 2024.04.00, compiler extension 1.5.11 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `android-mvvm-compose-navigation` | 8.4 | 8.3.0 | 1.9.23 | Compose BOM 2024.04.00, compiler extension 1.5.11 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `android-mvi-compose-navigation` | 8.4 | 8.3.0 | 1.9.23 | Compose BOM 2024.04.00, compiler extension 1.5.11 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `kmp-kmm` | 8.4 | 8.2.2 | 1.9.23 | Compose Multiplatform 1.6.10-beta01 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `kmp-basic` | 8.4 | 8.3.2 | 2.0.20 | Compose Multiplatform 1.6.10-rc03 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `kmp-with-server` | 8.7 | 8.5.2 | 2.0.20 | Compose Multiplatform 1.6.10-rc03 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `kmp-multimodule` | 8.7 | 8.5.2 | 2.0.20 | Compose Multiplatform 1.6.10-rc03 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `kmp-git-submodules` | 8.7 | 8.5.2 | 2.0.20 | Compose Multiplatform 1.6.10-rc03 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `kmp-tests` | 8.7 | 8.5.2 | 2.1.10 | Compose Multiplatform 1.6.10-rc03 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `kmp-custom-plugins` | 8.7 | 8.5.2 | 2.1.10 | Compose Multiplatform 1.6.10-rc03 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `kmp-analysis-tools` | 8.11.1 | 8.9.2 | 2.1.20 | Compose Multiplatform 1.6.10-rc03 | 17 | 24 / 34 / 34 | platform `android-34`, build-tools picked by AGP |
| `kmp-ci-cd` | 8.11.1 | 8.9.2 | 2.2.0 | Compose Multiplatform 1.9.0 | 21 | 24 / 36 / 35 | platform `android-36`, build-tools picked by AGP |

JDK is the version the sample is built and verified with. The Android SDK needs the platform
listed for `compileSdk`; Android Studio installs missing components on sync. Use
`./scripts/sample-info <id>` for the same data in the terminal.

## Environment

| Sample | Xcode | Node / Yarn | PostgreSQL | Docker | Environment variables | Secrets / local.properties |
|---|---|---|---|---|---|---|
| `android-mvc-native` | no | no | no | no | none | none |
| `android-mvc-async-task` | no | no | no | no | none | none |
| `android-mvp` | no | no | no | no | none | none |
| `android-mvvm` | no | no | no | no | none | none |
| `android-mvvm-clean` | no | no | no | no | none | none |
| `android-mvvm-compose` | no | no | no | no | none | none |
| `android-mvvm-compose-navigation` | no | no | no | no | none | none |
| `android-mvi-compose-navigation` | no | no | no | no | none | none |
| `kmp-kmm` | iOS target only | no | no | no | none | none |
| `kmp-basic` | iOS target only | downloaded by Gradle (web target) | no | no | none | none |
| `kmp-with-server` | iOS target only | downloaded by Gradle (web target) | `server` target only | server image only | server: `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (defaults exist) | none |
| `kmp-multimodule` | iOS target only | downloaded by Gradle (web target) | `server` target only | server image only | server: `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (defaults exist) | none |
| `kmp-git-submodules` | iOS target only | downloaded by Gradle (web target) | `server` target only | server image only | server: `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (defaults exist) | none |
| `kmp-tests` | iOS target only | downloaded by Gradle (web target) | `server` target only | server image only | server: `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (defaults exist) | `local.properties` with `CLOUD_URL` (required by `generateSecrets`) |
| `kmp-custom-plugins` | iOS target only | downloaded by Gradle (web target) | `server` target only | server image only | server: `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (defaults exist) | `local.properties` with `CLOUD_URL` |
| `kmp-analysis-tools` | iOS target only | downloaded by Gradle (web target) | `server` target only | server image only | server: `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (defaults exist) | `local.properties` with `CLOUD_URL`; Sonar token for `sonar` |
| `kmp-ci-cd` | iOS target only | downloaded by Gradle (web target) | `server` target only | server image only | server: `PORT`, `JDBC_URL`, `DRIVER_CLASS_NAME`, `DB_USER_NAME`, `DB_USER_PASSWORD` (defaults exist) | `local.properties`/env: `CLOUD_URL`; signing `STORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD` (+ optional `STORE_FILE`); Sonar token for `sonar` |

Node.js and Yarn are downloaded by the Kotlin Gradle plugin for the Web targets; no global
installation is needed. iOS targets compile only on macOS with Xcode.

## Commands

All commands are run from the sample directory with its own wrapper (or via
`scripts/build-sample`, `scripts/test-sample`, `scripts/run-sample`).

| Sample | Build | Test | Run targets |
|---|---|---|---|
| `android-mvc-native` | `./gradlew assembleDebug` | `./gradlew testDebugUnitTest` | android: `:app:installDebug` |
| `android-mvc-async-task` | `./gradlew assembleDebug` | `./gradlew testDebugUnitTest` | android: `:app:installDebug` |
| `android-mvp` | `./gradlew assembleDebug` | `./gradlew testDebugUnitTest` | android: `:app:installDebug` |
| `android-mvvm` | `./gradlew assembleDebug` | `./gradlew testDebugUnitTest` | android: `:app:installDebug` |
| `android-mvvm-clean` | `./gradlew assembleDebug` | `./gradlew testDebugUnitTest` | android: `:app:installDebug` |
| `android-mvvm-compose` | `./gradlew assembleDebug` | `./gradlew testDebugUnitTest` | android: `:app:installDebug` |
| `android-mvvm-compose-navigation` | `./gradlew assembleDebug` | `./gradlew testDebugUnitTest` | android: `:app:installDebug` |
| `android-mvi-compose-navigation` | `./gradlew assembleDebug` | `./gradlew testDebugUnitTest` | android: `:app:installDebug` |
| `kmp-kmm` | `./gradlew :app:assembleDebug` | `./gradlew :shared:testDebugUnitTest` | android: `:app:installDebug` |
| `kmp-basic` | `./gradlew :composeApp:assembleDebug` | `./gradlew :shared:testDebugUnitTest` | android: `:composeApp:installDebug`, desktop: `:composeApp:run`, web: `:composeApp:jsBrowserDevelopmentRun` |
| `kmp-with-server` | `./gradlew :composeApp:assembleDebug` | `./gradlew :shared:testDebugUnitTest` | android: `:composeApp:installDebug`, desktop: `:composeApp:run`, web: `:composeWebApp:jsBrowserDevelopmentRun`, server: `:server:run` |
| `kmp-multimodule` | `./gradlew :mobile:assembleDebug` | `./gradlew :shared:testDebugUnitTest` | android: `:mobile:installDebug`, desktop: `:desktop:run`, web: `:web:jsBrowserDevelopmentRun`, server: `:server:run` |
| `kmp-git-submodules` | `./gradlew :mobile:assembleDebug` | `./gradlew :shared:testDebugUnitTest` | android: `:mobile:installDebug`, desktop: `:desktop:run`, web: `:web:web:jsBrowserDevelopmentRun`, server: `:server:run` |
| `kmp-tests` | `./gradlew :mobile:assembleDebug` | `./gradlew :shared:jvmTest` | android: `:mobile:installDebug`, desktop: `:desktop:run`, web: `:web:jsBrowserDevelopmentRun`, server: `:server:run` |
| `kmp-custom-plugins` | `./gradlew :mobile:assembleDebug` | `./gradlew :shared:jvmTest` | android: `:mobile:installDebug`, desktop: `:desktop:run`, web: `:web:jsBrowserDevelopmentRun`, server: `:server:run` |
| `kmp-analysis-tools` | `./gradlew :mobile:assembleDebug` | `./gradlew :shared:jvmTest` | android: `:mobile:installDebug`, desktop: `:desktop:run`, web: `:web:jsBrowserDevelopmentRun`, server: `:server:run` |
| `kmp-ci-cd` | `./gradlew :mobile:assembleDebug` | `./gradlew :shared:jvmTest` | android: `:mobile:installDebug`, desktop: `:desktop:run`, web: `:web:jsBrowserDevelopmentRun`, server: `:server:run` |

To check the environment without building, run `./gradlew tasks` (or `./gradlew help`) in the
sample directory.

## Setting placeholders for local verification

Samples that require secrets only read them to generate constants or to configure signing.
For a compile-only check create a throw-away file that is already ignored by `.gitignore`:

```sh
cd samples/kmp/tests
printf 'CLOUD_URL=https://example.invalid\n' > local.properties
```

and, for `kmp-ci-cd`, export placeholder signing values:
`STORE_PASSWORD=x KEY_ALIAS=x KEY_PASSWORD=x ./scripts/build-sample kmp-ci-cd`.
Use real values only for real releases and never commit them.
