package com.vnteam.architecturetemplates.presentation.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI

data class ListViewState(val demoObject: List<DemoObjectUI>? = null,
                         val isLoading: Boolean = false,
                         var infoMessage: MutableState<InfoMessageState?> = mutableStateOf(null)
)
