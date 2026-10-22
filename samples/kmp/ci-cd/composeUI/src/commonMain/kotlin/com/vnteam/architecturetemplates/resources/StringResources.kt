package com.vnteam.architecturetemplates.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import com.vnteam.architecturetemplates.UIConstants

sealed class StringResources(
    var appName: String,
    var start: String,
    var ownerAvatar: String,
    var description: String,
    var name: String,
    var demoObject: String,
    var owner: String,
    var url: String,
    val back: String,
    var add: String,
    var delete: String,
    var buttonCancel: String,
    var buttonOk: String,
    var create: String,
    var edit: String,
    var submit: String,
    val emptyState: String,
    val noData: String,
    val changeAvatar: String,
    val speak: String,
    val pageNotFound: String,
    val switchToLightTheme: String,
    val switchToDarkTheme: String,
    val home: String
)

class StringResourcesEN : StringResources(
    appName = "Architecture Templates",
    start = "Start",
    ownerAvatar = "Owner avatar",
    description = "Description:",
    name = "Name",
    demoObject = "Demo Object",
    owner = "Owner",
    url = "URL",
    back = "Back",
    add = "Add",
    delete = "Delete",
    buttonCancel = "Cancel",
    buttonOk = "Ok",
    create = "Create",
    edit = "Edit",
    submit = "Submit",
    emptyState = "No data",
    noData = "N/A",
    changeAvatar = "Change avatar",
    speak = "Speak",
    pageNotFound = "Page not found",
    switchToLightTheme = "Switch to light theme",
    switchToDarkTheme = "Switch to dark theme",
    home = "Home",
)

class StringResourcesUK : StringResources(
    appName = "Architecture Templates",
    start = "Початок",
    ownerAvatar = "Owner avatar",
    description = "Опис:",
    name = "Ім'я",
    demoObject = "Демо об'єкт",
    owner = "Власник",
    url = "URL",
    back = "Назад",
    add = "Добавити",
    delete = "Видалити",
    buttonCancel = "Відмінити",
    buttonOk = "Так",
    create = "Створити",
    edit = "Редагувати",
    submit = "Підтвердити",
    emptyState = "Немає даних",
    noData = "Немає даних",
    changeAvatar = "Змінити аватар",
    speak = "Проговорити",
    pageNotFound = "Сторінку не знайдено",
    switchToLightTheme = "Переключити на світлу тему",
    switchToDarkTheme = "Переключити на темну тему",
    home = "Головна",
)

val LocalStringResources: ProvidableCompositionLocal<StringResources> =
    staticCompositionLocalOf {
        error("No StringResources provided")
    }

fun getStringResourcesByLocale(locale: String): StringResources {
    return when (locale) {
        UIConstants.LANG_UK -> StringResourcesUK()
        else -> StringResourcesEN()
    }
}

@Composable
fun getThemeSwitchDescription(isDarkTheme: Boolean): String {
    return if (isDarkTheme) {
        LocalStringResources.current.switchToLightTheme
    } else {
        LocalStringResources.current.switchToDarkTheme
    }
}
