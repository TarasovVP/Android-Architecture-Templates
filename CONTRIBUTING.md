# Contributing

This repository is a catalog of independent Gradle builds, so the rules differ
from a normal multi-module project.

## Ground rules

- **One sample = one Gradle build.** Never add a root `settings.gradle(.kts)`,
  `includeBuild`, or a Gradle dependency from one sample to another.
- **Keep each sample's own tooling.** Do not replace a sample's Gradle wrapper,
  merge version catalogs, or bump Kotlin/AGP/Compose "to be consistent".
- **Historical samples stay historical.** AsyncTask, Volley, Realm, XML Views
  and other old technologies are the point of those samples.
- **No shared code between samples.** Duplication is intentional.
- **No secrets or local files.** `local.properties`, keystores, `.env` files,
  build outputs and IDE folders must not be committed (the root `.gitignore`
  covers them).

## Changing a sample

1. Make the smallest change that fixes the problem.
2. Run the sample's own checks from its directory (`./gradlew ...`) or through
   `scripts/build-sample <id>` and `scripts/test-sample <id>`.
3. If tool versions, commands, or run targets change, update the sample's
   `sample.json` **and** the matching entry in `catalog/samples.json` (they use
   the same schema; keep one key per line, the scripts rely on it).

## Adding a sample

1. Create `samples/<category>/<name>/` with its own Gradle wrapper.
2. Add `README.md` and `sample.json`, then add the same object to
   `catalog/samples.json`.
3. Add it to the matrix in `.github/workflows/samples.yml`.
4. Give any Android app its own unique `applicationId`
   (`com.tarasovvp.architecturetemplates.<sample>`) so samples can be installed
   side by side.

## Pull requests

Describe which sample(s) are touched and how they were verified. Changes that
touch many samples at once are usually a sign that something is being
unified that should not be.
