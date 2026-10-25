package com.vnteam

object Constants {
    const val SECRETS_EXTENSION_NAME = "secretsConfig"
    const val GENERATE_SECRETS_TASK = "generateSecrets"
    const val KOTLIN_MULTIPLATFORM = "org.jetbrains.kotlin.multiplatform"

    const val SECRETS_PACKAGE = "package"
    const val SECRETS_OBJECT = "object"
    const val SECRETS_PACKAGE_NAME = "secrets"
    const val SECRETS_OBJECT_NAME = "Secrets"
    const val SECRETS_FILE_NAME = "Secrets.kt"
    const val LOCAL_PROPERTIES_FILE = "local.properties"
    const val DEFAULT_OUTPUT_DIR = "src/commonMain/kotlin"

    // Messages
    const val ERROR_NO_PROPERTIES_FOUND = "❌ No local.properties found for module: "
    const val ERROR_LOCAL_PROPERTIES_NOT_FOUND = "local.properties file not found at "

    // .gitignore
    const val GITIGNORE_FILE = ".gitignore"
    const val GITIGNORE_AUTO_GENERATED = "# Auto-generated .gitignore"

    const val FORWARD_SLASH = "/"
    const val BACKSLASH = "\\"
    const val DOT = "."
    const val CURLY_BRACE_CLOSE = "}"
    const val VALID_KEY_PATTERN = "^[a-zA-Z_][a-zA-Z0-9_]*$"
}
