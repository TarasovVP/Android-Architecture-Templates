package com.vnteam.architecturetemplates.shared

import com.vnteam.architecturetemplates.shared.Constants.CREATE_SCREEN
import com.vnteam.architecturetemplates.shared.Constants.DETAILS_SCREEN
import com.vnteam.architecturetemplates.shared.Constants.EDIT_SCREEN
import com.vnteam.architecturetemplates.shared.Constants.MAIN_SCREEN
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationScreens(var route: String) {
    @Serializable data object MainScreen : NavigationScreens("$MAIN_SCREEN/")

    @Serializable data object DetailsScreen : NavigationScreens("$DETAILS_SCREEN/")

    @Serializable data object EditScreen : NavigationScreens("$EDIT_SCREEN/")

    @Serializable data object CreateScreen : NavigationScreens("$CREATE_SCREEN/")
}
