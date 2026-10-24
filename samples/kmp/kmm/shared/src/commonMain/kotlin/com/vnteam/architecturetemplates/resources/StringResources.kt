package com.vnteam.architecturetemplates.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.intl.Locale

sealed class StringResources(
    var APP_NAME: String,
    var START: String,
    var OWNER_AVATAR: String,
    val BACK: String
)

class StringResourcesEN : StringResources(
    APP_NAME = "Architecture Templates",
    START = "Start",
    OWNER_AVATAR = "Owner avatar",
    BACK = "Back"
)

class StringResourcesUK : StringResources(
    APP_NAME = "Architecture Templates",
    START = "Початок",
    OWNER_AVATAR = "Owner avatar",
    BACK = "Назад"
)

fun getStringResourcesByLocale(locale: String): StringResources {
    return when (locale) {
        "uk" -> StringResourcesUK()
        else -> StringResourcesEN()
    }
}

@Composable
fun getStringResources(): StringResources {
    val locale = Locale.current.language
    return getStringResourcesByLocale(locale)
}