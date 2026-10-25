package com.vnteam.architecturetemplates.presentation.resources

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.vnteam.architecturetemplates.data.APP_LANG_UK

sealed class StringResources(
    var APP_NAME: String,
    var START: String,
    var OWNER_AVATAR: String,
    var DESCRIPTION: String,
    var NAME: String,
    var DEMO_OBJECT: String,
    var OWNER: String,
    var URL: String,
    val BACK: String,
    var ADD: String,
    var DELETE: String,
    var BUTTON_CANCEL: String,
    var BUTTON_OK: String,
    var CREATE: String,
    var EDIT: String,
    var SUBMIT: String,
    val EMPTY_STATE: String,
    val NO_DATA: String,
    val CHANGE_AVATAR: String,
    val SPEAK: String,
    val PAGE_NOT_FOUND: String
)

class StringResourcesEN : StringResources(
    APP_NAME = "Architecture Templates",
    START = "Start",
    OWNER_AVATAR = "Owner avatar",
    DESCRIPTION = "Description:",
    NAME = "Name",
    DEMO_OBJECT = "Demo Object",
    OWNER = "Owner",
    URL = "URL",
    BACK = "Back",
    ADD = "Add",
    DELETE = "Delete",
    BUTTON_CANCEL = "Cancel",
    BUTTON_OK = "Ok",
    CREATE = "Create",
    EDIT = "Edit",
    SUBMIT = "Submit",
    EMPTY_STATE = "No data",
    NO_DATA = "N/A",
    CHANGE_AVATAR = "Change avatar",
    SPEAK = "Speak",
    PAGE_NOT_FOUND = "Page not found"
)

class StringResourcesUK : StringResources(
    APP_NAME = "Architecture Templates",
    START = "Початок",
    OWNER_AVATAR = "Owner avatar",
    DESCRIPTION = "Опис:",
    NAME = "Ім'я",
    DEMO_OBJECT = "Демо об'єкт",
    OWNER = "Власник",
    URL = "URL",
    BACK = "Назад",
    ADD = "Добавити",
    DELETE = "Видалити",
    BUTTON_CANCEL = "Відмінити",
    BUTTON_OK = "Так",
    CREATE = "Створити",
    EDIT = "Редагувати",
    SUBMIT = "Підтвердити",
    EMPTY_STATE = "Немає даних",
    NO_DATA = "Немає даних",
    CHANGE_AVATAR = "Змінити аватар",
    SPEAK = "Проговорити",
    PAGE_NOT_FOUND = "Сторінку не знайдено"
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