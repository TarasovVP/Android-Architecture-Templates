package com.vnteam.architecturetemplates.presentation.states.screen

data class FloatingActionState(var floatingActionButtonVisible: Boolean = false,
                               var floatingActionButtonTitle: String = "",
                               var floatingActionButtonAction: () -> Unit = {})
