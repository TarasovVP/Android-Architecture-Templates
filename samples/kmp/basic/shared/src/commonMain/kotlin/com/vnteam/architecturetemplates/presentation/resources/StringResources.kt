package com.vnteam.architecturetemplates.presentation.resources

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.vnteam.architecturetemplates.data.APP_LANG_UK

sealed class StringResources(
    var APP_NAME: String,
    var DEMO_OBJECTS: String,
    var START: String,
    var OWNER_AVATAR: String,
    var DESCRIPTION: String,
    var NAME: String,
    var DEMO_OBJECT: String,
    var OWNER: String,
    var URL: String,
    val BACK: String,
    var BUTTON_CANCEL: String,
    var BUTTON_OK: String
)

class StringResourcesEN : StringResources(
    APP_NAME = "Architecture Templates",
    DEMO_OBJECTS = "Demo Objects",
    START = "Start",
    OWNER_AVATAR = "Owner avatar",
    DESCRIPTION = "Description:",
    NAME = "Name",
    DEMO_OBJECT = "DemoObject",
    OWNER = "Owner",
    URL = "URL",
    BACK = "Back",
    BUTTON_CANCEL = "Cancel",
    BUTTON_OK = "Ok"
)

class StringResourcesUK : StringResources(
    APP_NAME = "Architecture Templates",
    START = "Початок",
    DEMO_OBJECTS = "Демо об'єкти",
    OWNER_AVATAR = "Owner avatar",
    DESCRIPTION = "Опис:",
    NAME = "Ім'я",
    DEMO_OBJECT = "Форк",
    OWNER = "Власник",
    URL = "URL",
    BACK = "Назад",
    BUTTON_CANCEL = "Відмінити",
    BUTTON_OK = "Так"
)

val LocalStringResources: ProvidableCompositionLocal<StringResources> = staticCompositionLocalOf {
    error("No StringResources provided")
}

fun getStringResourcesByLocale(locale: String): StringResources {
    return when (locale) {
        APP_LANG_UK -> StringResourcesUK()
        else -> StringResourcesEN()
    }
}