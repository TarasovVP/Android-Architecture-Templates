# Architecture comparison

The comparison is based on the code in `samples/`, not on textbook
definitions. Paths are relative to the sample directory. Every sample solves
the same scenario: load a list of "demo objects" (GitHub forks) from an API,
cache it in a local database, show it, open a details screen.

## Android: from MVC to MVI

| | `android-mvc-native` | `android-mvc-async-task` | `android-mvp` | `android-mvvm` | `android-mvvm-clean` | `android-mvi-compose-navigation` |
|---|---|---|---|---|---|---|
| Entry points | `MainActivity`, `DetailsActivity` | same | `MainActivity` + `ListFragment`, `DetailsFragment` | `MainActivity` + Fragments (Navigation component) | same as MVVM | `MainActivity` + Compose screens (Voyager) |
| Where the logic lives | In the activities (324 lines in 7 files in total) | In the activities, with `AsyncTask` callbacks | `ListPresenter`, `DetailsPresenter` | `ListViewModel`, `DetailsViewModel` + use cases | same | `ListViewModel`, `DetailsViewModel` + use cases |
| UI contract | none | none | `ListViewContract`, `DetailsViewContract` | observable state (LiveData) | same | `ListViewState` + `ListIntent` (`processIntent`) |
| Layers | one package | one package | `database`, `network`, `models`, `list`, `details`, `di` | `data` / `domain` / `presentation` / `di` | `data` / `domain` / `presentation` / `di` | `data` / `domain` / `presentation` / `di` |
| DI | none | none (ButterKnife only binds views) | Dagger 2 | Hilt | Hilt | Koin |
| Testability | UI code and I/O are mixed | I/O wrapped in `OkHttpClientConnector`, still inside activities | presenter can be tested against a view contract | ViewModel and use cases can be unit-tested | same | state is a single immutable object, easy to assert |

### MVC (`android-mvc-native`, `android-mvc-async-task`)

There is no separation beyond "model classes" (`DemoObject`, `Owner`) and
helpers (`HttpUrlConnector`, `SQLiteDBConnector`, `JsonConverter`). The
activity creates the connectors, runs the request, parses JSON and fills the
views. The async-task variant swaps hand-written threads for libraries
(`OkHttpClientConnector`, `OrmLiteSqliteDBConnector`) but keeps the same
structure. Strength: very little code. Weakness: the activity is controller,
view and lifecycle owner at once, so nothing is testable without a device.

### MVP (`android-mvp`)

Each screen has a Fragment (the view), a `*ViewContract` interface and a
`*Presenter`. The presenter talks to Volley and Realm and updates the view only
through the contract. Strength: logic is testable with a fake view.
Weakness: presenters hold a view reference and must be detached manually;
`list/` still contains adapter and click-listener classes next to the
presenter.

### MVVM (`android-mvvm`)

`presentation/` holds `ListViewModel`/`DetailsViewModel` and UI models;
`domain/` holds models, repository interfaces, mappers and a use case;
`data/` holds Retrofit responses, Room entities/DAO and the repository
implementations. ViewModels survive configuration changes and expose state, so
the Fragments only render.

### MVVM + Clean Architecture (`android-mvvm-clean`)

The package structure is the same as `android-mvvm`. The visible difference in
the code is naming: `DemoObject*` classes become `Fork*` (`ForkDao`,
`ForkDB`, `ForkResponse`, ...), i.e. the domain vocabulary is concrete. Treat
it as MVVM with explicit layers and concrete domain names rather than as a
structurally different architecture.

### MVVM + Compose (`android-mvvm-compose`, `android-mvvm-compose-navigation`)

`android-mvvm-compose` keeps Fragments and Hilt and replaces XML with Compose.
`android-mvvm-compose-navigation` removes Fragments (Compose Navigation),
exposes `StateFlow`, and replaces Retrofit/Room/Hilt with Ktor/SQLDelight/Koin,
the stack that later moves to Kotlin Multiplatform without changes.

### MVI (`android-mvi-compose-navigation`)

Same layers as the MVVM Compose sample, but each screen has a
`*ViewState` (immutable data class), a `*Intent` (sealed type) and a single
`processIntent(intent)` entry point on the ViewModel. UI is a function of
state; user actions are intents.

## Kotlin Multiplatform

| | `kmp-kmm` | `kmp-basic` | `kmp-with-server` | `kmp-multimodule` | `kmp-git-submodules` |
|---|---|---|---|---|---|
| Gradle modules | `app`, `shared` | `composeApp`, `shared`, `webApp` | `core`, `shared`, `composeUi`, `composeApp`, `composeWebApp`, `server`, `webApp` | `core`, `shared`, `mobile`, `desktop`, `web`, `server` | same list, but `shared` and `web` come from other repositories |
| Targets | Android, iOS | Android, iOS, Desktop, Web | + Ktor server | + Ktor server | + Ktor server |
| Where the UI is | `shared` | `composeApp` (+ `webApp` for Compose HTML) | `composeUi` + per-platform apps | per-platform app modules | per-platform app modules |
| Platform app per target | `app` | one module for Android and Desktop (`composeApp`) | separate Android/Desktop (`composeApp`) and web modules | one module per platform | one module per platform |

### KMM (`kmp-kmm`)

Only Android and iOS. Business logic and UI are in `shared` with
`androidMain`, `commonMain`, `iosMain`; `app` is the Android launcher and
`iosApp` the Xcode project. The name "KMM" is the historical one for this
mobile-only use case.

### KMP, single module (`kmp-basic`)

`shared` plus `composeApp` cover four platform families. `composeApp` has
`androidMain`, `desktopMain`, `iosMain`, `jsMain`, so a single module is the
launcher for several targets. Simple to navigate, but a change to one target's
build script affects all of them.

### KMP with a server (`kmp-with-server`)

Adds `server` (Ktor, PostgreSQL via HikariCP) in the same Gradle build and
splits shared UI (`composeUi`) from platform apps. The clients stop calling the
public GitHub API and use the project's own server.

### KMP multi-module (`kmp-multimodule`)

One module per platform application (`mobile`, `desktop`, `web`, `server`)
and two libraries: `core` (common building blocks) and `shared` (data, domain,
presentation). Dependencies point inward: apps depend on `shared`, `shared`
on `core`. The `shared` module is organised like the Android MVVM/MVI
samples (`data`/`domain`/`presentation`/`di`) with `presentation/intents`,
`presentation/states` and `presentation/viewmodels` for MVI.

### Git submodules (`kmp-git-submodules`)

Same module structure, but `shared` and `web` were separate repositories
mounted as Git submodules. In this catalog the files are vendored
(see `samples/kmp/git-submodules/ORIGINAL_SUBMODULES.md`). Trade-off: the
pieces can be versioned and reused independently, at the cost of pinned SHAs,
extra clone steps and cross-repository changes.

### What the later KMP samples add (same architecture)

`kmp-tests` (commonTest, fakes, Kover), `kmp-custom-plugins` (secrets plugin,
ktlint rules, Lint check), `kmp-analysis-tools` (Detekt, Sonar, CI, build
cache, shared `composeUI` module) and `kmp-ci-cd` (Jenkins, Fastlane,
Macrobenchmark, Baseline Profile) do not change the application architecture;
they add engineering practices around it.

## Single module vs multi-module, in practice

| Question | Single module (`kmp-basic`) | Multi-module (`kmp-multimodule` and later) |
|---|---|---|
| Finding code | Fewer places to look | Module names tell the platform and layer |
| Build scripts | One per platform family inside `composeApp` | One small script per module |
| Isolation | Any file can use any dependency of the module | Dependencies are limited by the module graph |
| Build time | Whole module recompiles | Gradle can skip unchanged modules |
| Overhead | Low | More `build.gradle.kts` files and `settings` entries |
