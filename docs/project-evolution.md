# Project evolution

Every sample implements the same small scenario (fetch a list from an API,
store it locally, show it, open a details screen) and changes one technical
decision at a time. The samples were created in this order; the arrows show the
conceptual sequence, not Git ancestry (some branches started from earlier
points than the diagrams suggest).

IDs below are the catalog IDs from `scripts/list-samples`.

## Android line

```
android-mvc-native              Activity-as-controller, HttpURLConnection, SQLiteDatabase
        │
        ▼
android-mvc-async-task          + OkHttp, Moshi, ORMLite, ButterKnife, AsyncTask
        │
        ▼
android-mvp                     Presenter + view contracts, Volley, RxJava, Realm, Dagger 2
        │
        ▼
android-mvvm                    ViewModel + LiveData, Retrofit, Room, Hilt, Navigation component
        │
        ├──► android-mvvm-clean                 same layering, domain naming changed from DemoObject to Fork
        │
        └──► android-mvvm-compose               Compose UI hosted in Fragments
                    │
                    ▼
             android-mvvm-compose-navigation    Compose Navigation, StateFlow, Ktor, SQLDelight, Koin
                    │
                    ▼
             android-mvi-compose-navigation     MVI (state + intents), Voyager navigation
```

## Kotlin Multiplatform line

```
android-mvi-compose-navigation
        │
        ▼
kmp-kmm                  Android + iOS, shared Compose UI (Kotlin Multiplatform Mobile)
        │
        ▼
kmp-basic                + Desktop (JVM) + Web (Kotlin/JS)
        │
        ▼
kmp-with-server          + Ktor server, PostgreSQL, own API
        │
        ▼
kmp-multimodule          one Gradle module per platform application + core / shared
        │            │
        │            └──► kmp-git-submodules    side branch: shared and web as Git submodules
        ▼
kmp-tests                unit tests in commonTest, Kover
        │
        ▼
kmp-custom-plugins       secrets Gradle plugin, custom ktlint rules, Lint check, pre-commit hook
        │
        ▼
kmp-analysis-tools       Detekt, Kover, SonarQube, GitHub Actions, build cache
        │
        ▼
kmp-ci-cd                Jenkins + Fastlane, benchmarks, baseline profiles
```

`kmp-git-submodules` is not an ancestor of the samples that follow
`kmp-multimodule`; it is a parallel experiment with a different repository
structure.

## What each step changed

| Step | Main change | Tool versions (Gradle / AGP / Kotlin) |
|------|-------------|---------------------------------------|
| `android-mvc-native` | Baseline with platform APIs only | 7.5 / 7.4.1 / 1.8.0 |
| `android-mvc-async-task` | Libraries replace hand-written plumbing | 7.5 / 7.4.1 / 1.8.0 |
| `android-mvp` | Presenters and contracts separate UI from logic | 7.5 / 7.4.1 / 1.8.0 |
| `android-mvvm` | Lifecycle-aware state holders, DI by Hilt, Kotlin DSL build scripts | 8.4 / 8.3.0 / 1.9.23 |
| `android-mvvm-clean` | Domain naming made concrete | 8.4 / 8.3.0 / 1.9.23 |
| `android-mvvm-compose` | Views become Compose, still inside Fragments | 8.4 / 8.3.0 / 1.9.23 |
| `android-mvvm-compose-navigation` | Single activity, Compose Navigation, Ktor, SQLDelight, Koin | 8.4 / 8.3.0 / 1.9.23 |
| `android-mvi-compose-navigation` | Immutable state and intents | 8.4 / 8.3.0 / 1.9.23 |
| `kmp-kmm` | Logic and UI shared with iOS | 8.4 / 8.2.2 / 1.9.23 |
| `kmp-basic` | Desktop and Web targets | 8.4 / 8.3.2 / 2.0.20 |
| `kmp-with-server` | Backend in the same repository | 8.7 / 8.5.2 / 2.0.20 |
| `kmp-multimodule` | Module per platform application | 8.7 / 8.5.2 / 2.0.20 |
| `kmp-git-submodules` | Parts of the code in other repositories | 8.7 / 8.5.2 / 2.0.20 |
| `kmp-tests` | Automated tests and coverage | 8.7 / 8.5.2 / 2.1.10 |
| `kmp-custom-plugins` | Own Gradle plugin and lint rules | 8.7 / 8.5.2 / 2.1.10 |
| `kmp-analysis-tools` | Static analysis, Sonar, CI, build cache | 8.11.1 / 8.9.2 / 2.1.20 |
| `kmp-ci-cd` | Delivery pipeline, benchmarks | 8.11.1 / 8.9.2 / 2.2.0 |

Versions are read from each sample's wrapper, `gradle/libs.versions.toml` and
root build script; they are not normalised on purpose.
