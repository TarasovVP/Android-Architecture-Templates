package com.vnteam.architecturetemplates.presentation.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI

data class ListViewState(val demoObjectUIs: List<DemoObjectUI>? = null,
                         var isConfirmationDialogVisible: MutableState<Boolean> = mutableStateOf(false),
                         var demoObjectToDelete: String = "",
                         var successResult: Boolean = false
)