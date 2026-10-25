package com.vnteam.architecturetemplates.presentation.states

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI


data class CreateViewState(var demoObject: MutableState<DemoObjectUI?> = mutableStateOf(null),
                           var isChangeAvatarDialogVisible: MutableState<Boolean> = mutableStateOf(false),
                           var successResult: Boolean = false
)
