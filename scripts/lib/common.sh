# Shared helpers for the scripts in this directory. Sourced, never executed.
# POSIX sh only. The catalog (catalog/samples.json) is written by
# tools that keep one key per line, so it can be read with awk alone.

ROOT=$(cd "$(dirname "$0")/.." && pwd)
CATALOG="$ROOT/catalog/samples.json"

die() {
    printf 'error: %s\n' "$*" >&2
    exit 1
}

[ -f "$CATALOG" ] || die "catalog not found: $CATALOG"

# catalog_ids: one sample id per line
catalog_ids() {
    awk -F'"' '/^      "id": /{print $4}' "$CATALOG"
}

# catalog_has ID: succeeds when the sample exists
catalog_has() {
    catalog_ids | grep -qx -- "$1"
}

# catalog_raw ID KEY: raw JSON value of a key, without trailing comma
catalog_raw() {
    awk -v id="$1" -v key="$2" '
        $0 == "      \"id\": \"" id "\"," { cur = 1; next }
        /^    }/ { cur = 0 }
        cur && index($0, "      \"" key "\": ") == 1 {
            v = substr($0, length(key) + 11)
            sub(/,$/, "", v)
            print v
            exit
        }' "$CATALOG"
}

# catalog_str ID KEY: string (or number/boolean) value, quotes removed
catalog_str() {
    catalog_raw "$1" "$2" | sed -e 's/^"//' -e 's/"$//'
}

# catalog_list ID KEY: elements of a JSON array of strings, one per line
catalog_list() {
    catalog_raw "$1" "$2" | sed -e 's/^\[//' -e 's/\]$//' |
        awk '{ n = split($0, a, /", "/); for (i = 1; i <= n; i++) { s = a[i]; gsub(/^"|"$/, "", s); if (s != "") print s } }'
}

# catalog_targets ID: run targets as "name=gradle-task" lines
catalog_targets() {
    catalog_raw "$1" runTargets | sed -e 's/^{//' -e 's/}$//' |
        awk '{ n = split($0, a, /", "/); for (i = 1; i <= n; i++) { s = a[i]; gsub(/"/, "", s); sub(/: /, "=", s); if (s != "") print s } }'
}

require_sample() {
    [ -n "${1:-}" ] || die "missing <sample-id>; run scripts/list-samples to see the available ids"
    catalog_has "$1" || die "unknown sample id '$1'; run scripts/list-samples to see the available ids"
}

sample_dir() {
    printf '%s/%s\n' "$ROOT" "$(catalog_str "$1" path)"
}

# select_jdk ID: point JAVA_HOME at the JDK the sample needs when it can be
# found. Never installs anything; only warns when the version does not match.
select_jdk() {
    want=$(catalog_str "$1" jdkVersion)
    if [ -n "${SAMPLE_JAVA_HOME:-}" ]; then
        JAVA_HOME=$SAMPLE_JAVA_HOME
        export JAVA_HOME
        return 0
    fi
    if [ -z "${JAVA_HOME:-}" ] && [ -x /usr/libexec/java_home ]; then
        jh=$(/usr/libexec/java_home -v "$want" 2>/dev/null || true)
        if [ -n "$jh" ]; then
            JAVA_HOME=$jh
            export JAVA_HOME
        fi
    fi
    if [ -n "${JAVA_HOME:-}" ] && [ -x "$JAVA_HOME/bin/java" ]; then
        jbin="$JAVA_HOME/bin/java"
    else
        jbin=$(command -v java || true)
    fi
    [ -n "$jbin" ] || die "no Java found. Install JDK $want and set JAVA_HOME (or SAMPLE_JAVA_HOME)."
    have=$("$jbin" -version 2>&1 | awk -F'"' '/version/{split($2, v, "."); print (v[1] == 1 ? v[2] : v[1]); exit}')
    if [ "$have" != "$want" ]; then
        printf 'warning: sample %s expects JDK %s but %s is used; set SAMPLE_JAVA_HOME to override\n' "$1" "$want" "$have" >&2
    fi
}

# check_android_sdk ID: fail early with instructions when no SDK is configured
check_android_sdk() {
    [ "$(catalog_str "$1" requiresAndroidSdk)" = "true" ] || return 0
    [ -n "${ANDROID_HOME:-}" ] && return 0
    [ -n "${ANDROID_SDK_ROOT:-}" ] && return 0
    if [ -f "$(sample_dir "$1")/local.properties" ] && grep -q '^sdk.dir=' "$(sample_dir "$1")/local.properties"; then
        return 0
    fi
    die "Android SDK not found. Install it (Android Studio or cmdline-tools) and set ANDROID_HOME, or add sdk.dir=<path> to $(catalog_str "$1" path)/local.properties."
}

# in_sample ID: cd into the sample and make sure its own wrapper exists
in_sample() {
    dir=$(sample_dir "$1")
    [ -d "$dir" ] || die "directory missing: $dir"
    cd "$dir"
    [ -f ./gradlew ] || die "no Gradle wrapper in $dir"
    [ -x ./gradlew ] || chmod +x ./gradlew 2>/dev/null || die "gradlew is not executable in $dir"
}

# default_tasks ID KEY: gradle arguments of the first command in KEY
default_tasks() {
    catalog_list "$1" "$2" | head -n 1 | sed 's|^\./gradlew *||'
}
