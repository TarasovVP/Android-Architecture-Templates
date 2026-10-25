package com.vnteam.architecturetemplates.presentation.states.screen

data class ScreenState(var appBarState: AppBarState = AppBarState(),
                       var floatingActionState: FloatingActionState = FloatingActionState(),
                       var appMessageState: AppMessageState = AppMessageState(),
                       var isProgressVisible: Boolean = false,
                       var isScreenUpdatingNeeded: Boolean = false
)
