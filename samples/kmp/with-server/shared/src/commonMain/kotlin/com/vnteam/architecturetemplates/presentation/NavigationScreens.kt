package com.vnteam.architecturetemplates.presentation

import com.vnteam.architecturetemplates.presentation.Constants.CREATE_SCREEN
import com.vnteam.architecturetemplates.presentation.Constants.DETAILS_SCREEN
import com.vnteam.architecturetemplates.presentation.Constants.EDIT_SCREEN
import com.vnteam.architecturetemplates.presentation.Constants.MAIN_SCREEN
import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationScreens(var route: String) {
    @Serializable data object MainScreen : NavigationScreens("$MAIN_SCREEN/")
    @Serializable data object DetailsScreen : NavigationScreens("$DETAILS_SCREEN/")
    @Serializable data object EditScreen : NavigationScreens("$EDIT_SCREEN/")
    @Serializable data object CreateScreen : NavigationScreens("$CREATE_SCREEN/")
}