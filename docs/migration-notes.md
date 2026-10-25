# Migration notes

Migration of the per-branch layout into this single-branch catalog (branch `catalog-monorepo`).
Date: 2026-10-25 (the first three commits are dated 22-24 October at the author's request).

## Source branches and directories

Every sample was exported with `git archive` from the **remote** branch (`origin/<branch>`,
fetched on the migration day). Local branches were one commit behind their remotes (README link
updates), so the remotes are the source. No existing branch was modified, deleted or rewritten;
nothing was force-pushed.

| Branch | Commit | Directory |
|--------|--------|-----------|
| `mvc_native_implementation` | `86aef182` | `samples/android/mvc-native` |
| `mvc_async_task_implementation` | `6c02c716` | `samples/android/mvc-async-task` |
| `mvp_implementation` | `87af8031` | `samples/android/mvp` |
| `mvvm_implementation` | `be3505bc` | `samples/android/mvvm` |
| `mvvm_clean_architecture_implementation` | `e9c9326f` | `samples/android/mvvm-clean` |
| `mvvm_compose_implementation` | `609620a1` | `samples/android/mvvm-compose` |
| `mvvm_compose_navigation_implementation` | `bbe64720` | `samples/android/mvvm-compose-navigation` |
| `mvi_compose_navigation_implementation` | `0dff2a85` | `samples/android/mvi-compose-navigation` |
| `kmm_implementation` | `001f6c6f` | `samples/kmp/kmm` |
| `kmp_implementation` | `703c240c` | `samples/kmp/basic` |
| `kmp_with_server_implementation` | `5ecf0b82` | `samples/kmp/with-server` |
| `kmp_with_multimodules_implementation` | `71291d4e` | `samples/kmp/multimodule` |
| `kmp_with_git_submodules_implementation` | `e8ee3e40` | `samples/kmp/git-submodules` |
| `kmp_with_tests` | `07ea49cc` | `samples/kmp/tests` |
| `kmp_with_custom_plugins` | `e6baff20` | `samples/kmp/custom-plugins` |
| `kmp_with_analyze_tools` | `dcde9f37` | `samples/kmp/analysis-tools` |
| `kmp_with_ci_cd` (= `master`) | `f37069c1` (`master`: `e770f71d`) | `samples/kmp/ci-cd` |

Full SHAs are in each `sample.json` (`sourceCommit`). `master` (`e770f71d`) is the base of
`catalog-monorepo`.

## master vs kmp_with_ci_cd

`git diff origin/master origin/kmp_with_ci_cd` differs only in `LICENSE` and `README.md`
(`master` has the license and the catalog README, the branch has the implementation README), so
the code is identical. `master` was **moved** (`git mv`) into `samples/kmp/ci-cd` instead of
creating a second copy, and the sample README is the one from `kmp_with_ci_cd`. The former catalog
README of `master` stays in Git history (`git show e770f71d:README.md`). `master`'s
`.github/workflows/sonar.yml` moved with the project into the sample and is no longer an active
workflow; the active root workflow is `.github/workflows/samples.yml`.

## Files not imported

- Tracked IDE directories: `.idea/` (15 samples).
- Tracked generated directories: top-level `.kotlin/` (7 samples, plus the one `master` tracked
  with `.kotlin/errors/*.log`, plus the one inside the `web` submodule).
- Compiled outputs that some branches tracked: `build/` directories in `kmp/basic` (`shared`, and a
  `server` that has no sources, only outputs), `kmm`, `with-server` and `custom-plugins`. They are
  excluded by the root `.gitignore`, so they were not committed. As a result `kmp-basic` has no
  `server` module (its `settings.gradle.kts` never included it).
- `xcuserdata/` (Xcode user state) in every sample with an `iosApp`; for `ci-cd` the files `master` tracked were dropped from the index (still present in earlier commits of this branch).
- No `local.properties`, keystore, APK/AAB or `.DS_Store` was tracked in the branches.
- Executable bits: only the `gradlew` scripts were executable in the sources; they are unchanged.

## Git submodules

`kmp_with_git_submodules_implementation` references two submodules. Their pinned commits were
cloned from GitHub and exported into the sample (no nested `.git`):

| Path | URL | Commit |
|------|-----|--------|
| `shared` | https://github.com/TarasovVP/architecture_template_shared.git | `45fbbdb89723ced76b42785b68167fd2ec7f6d7b` |
| `web` | https://github.com/TarasovVP/architecture_template_web.git | `60cede935aee96c69b020fbd082e7f948273ad06` |

`web` itself has a nested `shared` submodule at the same commit, also vendored (`web/shared`). Removed:
`.kotlin/` and `.gitmodules` of `web`, `.DS_Store`. Details: `samples/kmp/git-submodules/ORIGINAL_SUBMODULES.md`.

## applicationId changes

Only `applicationId` was changed (namespaces and Kotlin packages are untouched). It was
`com.vnteam.architecturetemplates` in every app.

| Sample | New applicationId |
|--------|-------------------|
| `android-mvc-native` | `com.tarasovvp.architecturetemplates.mvc.native` |
| `android-mvc-async-task` | `com.tarasovvp.architecturetemplates.mvc.asynctask` |
| `android-mvp` | `com.tarasovvp.architecturetemplates.mvp` |
| `android-mvvm` | `com.tarasovvp.architecturetemplates.mvvm` |
| `android-mvvm-clean` | `com.tarasovvp.architecturetemplates.mvvm.clean` |
| `android-mvvm-compose` | `com.tarasovvp.architecturetemplates.mvvm.compose` |
| `android-mvvm-compose-navigation` | `com.tarasovvp.architecturetemplates.mvvm.compose.navigation` |
| `android-mvi-compose-navigation` | `com.tarasovvp.architecturetemplates.mvi.compose.navigation` |
| `kmp-kmm` | `com.tarasovvp.architecturetemplates.kmm` |
| `kmp-basic` | `com.tarasovvp.architecturetemplates.kmp.basic` |
| `kmp-with-server` | `com.tarasovvp.architecturetemplates.kmp.withserver` |
| `kmp-multimodule` | `com.tarasovvp.architecturetemplates.kmp.multimodule` |
| `kmp-git-submodules` | `com.tarasovvp.architecturetemplates.kmp.gitsubmodules` |
| `kmp-tests` | `com.tarasovvp.architecturetemplates.kmp.tests` |
| `kmp-custom-plugins` | `com.tarasovvp.architecturetemplates.kmp.customplugins` |
| `kmp-analysis-tools` | `com.tarasovvp.architecturetemplates.kmp.analysistools` |
| `kmp-ci-cd` | `com.tarasovvp.architecturetemplates.kmp.cicd` |

`mvc.native` contains the Java keyword `native`; AGP accepted it and the app builds. The package
name used in code is the unchanged `namespace`, which is why this is not a problem.

## Deviations from the source branches

All other files are byte-identical to the branches (apart from the README blocks and `sample.json`
added by the catalog).

| Sample | Change | Why |
|--------|--------|-----|
| all | `applicationId` (above); README gets a catalog block above the original text; `sample.json` added | catalog |
| `kmp-ci-cd` | `settings.gradle.kts`: `isEnabled = providers.gradleProperty("remoteCache").isPresent` for the remote build cache | without a cache node on `localhost:5071` every build logs `Connection refused`. Same change as the author's local commit `b13833bb` |
| `kmp-ci-cd` | `composeUI/build.gradle.kts`: removed the `configurations.all { resolutionStrategy ... useVersion("1.6.7") }` block for `androidx.compose.foundation` | `Modifier.animateItem` (needs foundation 1.7+) was unresolved on the Android target, so `:mobile:assembleDebug` failed |
| `kmp-custom-plugins` | `mobile`, `desktop`, `web` `App.kt`: import `...presentation.screens.splash.SplashScreen` instead of `...presentation.components.SplashScreen` | the old package does not exist; `:mobile:compileDebugKotlinAndroid` failed with "Unresolved reference" |
| `kmp-kmm` | `OwnerUIMapperImpl.kt`: removed a duplicated import | "Conflicting import" compile error |
| `kmp-kmm` | `app/src/androidMain/res/mipmap-anydpi-v26/ic_launcher*.xml`: `@mipmap/ic_launcher_foreground` to `@drawable/ic_launcher_foreground` | the foreground exists only as `drawable-v24/ic_launcher_foreground.xml`; resource linking failed |

The three source fixes and the `ci-cd` cache change are separate commits.

## Verification results

Machine: macOS (arm64), Android SDK with platforms 25-37, Xcode 26 installed but not used,
JDK 17 (Microsoft 17.0.16) for all samples except `kmp-ci-cd` (Temurin 21). No emulator or device was
connected. Each command was run in the sample directory with its own wrapper (`--no-daemon`).

| Sample | Build command | Result | Test command | Result |
|--------|---------------|--------|--------------|--------|
| `android-mvc-native` | `assembleDebug` | OK | `testDebugUnitTest` | OK |
| `android-mvc-async-task` | `assembleDebug` | OK | `testDebugUnitTest` | OK |
| `android-mvp` | `assembleDebug` | OK | `testDebugUnitTest` | OK |
| `android-mvvm` | `assembleDebug` | OK | `testDebugUnitTest` | OK |
| `android-mvvm-clean` | `assembleDebug` | OK | `testDebugUnitTest` | OK |
| `android-mvvm-compose` | `assembleDebug` | OK | `testDebugUnitTest` | OK |
| `android-mvvm-compose-navigation` | `assembleDebug` | OK | `testDebugUnitTest` | OK |
| `android-mvi-compose-navigation` | `assembleDebug` | OK | `testDebugUnitTest` | OK |
| `kmp-kmm` | `:app:assembleDebug` | OK after the two fixes (failed before) | `:shared:testDebugUnitTest` | OK |
| `kmp-basic` | `:composeApp:assembleDebug` | OK | `:shared:testDebugUnitTest` | OK |
| `kmp-with-server` | `:composeApp:assembleDebug` | OK | `:shared:testDebugUnitTest` | OK |
| `kmp-multimodule` | `:mobile:assembleDebug` | OK | `:shared:testDebugUnitTest` | OK |
| `kmp-git-submodules` | `:mobile:assembleDebug` | OK | `:shared:testDebugUnitTest` | OK |
| `kmp-tests` | `:mobile:assembleDebug` | OK with a placeholder `local.properties` (fails without it) | `:shared:jvmTest` | OK |
| `kmp-custom-plugins` | `:mobile:assembleDebug` | OK after the import fix and a placeholder `local.properties` | `:shared:jvmTest` | OK |
| `kmp-analysis-tools` | `:mobile:assembleDebug` | OK with a placeholder `local.properties` | `:shared:jvmTest` | OK |
| `kmp-ci-cd` | `:mobile:assembleDebug` | OK after the foundation fix, a placeholder `local.properties` and placeholder `STORE_PASSWORD`/`KEY_ALIAS`/`KEY_PASSWORD` | `:shared:jvmTest` | 57 of 58 pass; `DeleteDemoObjectUseCaseTest.testDeleteDemoObject` needs the real server (`ConnectException`) |

"Placeholder" means a throw-away, git-ignored file or dummy values (`CLOUD_URL=https://example.invalid`,
`placeholder`), never real secrets. Samples with few or no unit tests complete the test task
without running many tests; a green task is not a statement about coverage.

### Grouping

- **Built and tested without extra setup:** 8 Android samples, `kmp-basic`, `kmp-with-server`,
  `kmp-multimodule`, `kmp-git-submodules`, `kmp-kmm` (after the source fixes).
- **Built and tested only with placeholder configuration:** `kmp-tests`, `kmp-custom-plugins`
  (after the import fix), `kmp-analysis-tools`, `kmp-ci-cd` (after the foundation fix).
- **Partially verified:** `kmp-ci-cd` (one network-dependent test fails offline).
- **Blocked by environment, not executed:** PostgreSQL and Docker (server runs and images),
  Sonar token (`sonar`), Jenkins and Fastlane, release signing, baseline-profile generation, iOS
  builds (the iOS task was not run even though Xcode is present).
- **Not verified:** Desktop, Web and Server targets; `scripts/run-sample` against a real device
  or emulator; installing several apps side by side; the GitHub Actions workflow (its YAML parses,
  but it has not run on GitHub); the `scripts/` against Linux (they use only POSIX tools).

## Known problems

- `kmp-ci-cd` test `DeleteDemoObjectUseCaseTest` depends on a reachable server. The CI matrix
  skips the test step for this sample.
- `kmp-tests`, `kmp-custom-plugins`, `kmp-analysis-tools` and `kmp-ci-cd` do not build without a
  `local.properties` containing `CLOUD_URL`; `kmp-ci-cd` also needs signing values at configuration
  time. The `kmp-secrets-plugin` has `check(!propertiesFile.exists())` in `SecretsGenerator`, an inverted
  condition that looks like a bug; it was left as is and does not block the placeholder-based builds.
- `kmp-analysis-tools` and `kmp-ci-cd` use a remote build cache at `http://localhost:5071/cache/`
  (only `ci-cd` was made opt-in).
- `kmp-basic` has no `server` sources in its source branch.
- Workflows stored inside samples (`kmp/analysis-tools/.github`, `kmp/ci-cd/.github`) are examples
  and are not run by GitHub.

## CI matrix

All 17 samples are in `.github/workflows/samples.yml`, grouped as historical Android, modern Android,
early KMP and modern KMP, each with the JDK it needs. Only the Android target is built; no secrets,
PostgreSQL, Docker, Xcode or keystores are used. Exceptions: the modern KMP samples get a placeholder
`local.properties` and dummy signing values, and `kmp-ci-cd` skips the test step (see above).
