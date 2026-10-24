package com.vnteam.architecturetemplates.presentation.intents

import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI

sealed class CreateIntent {
    data class LoadDemoObject(val demoObjectId: String) : CreateIntent()
    data class CreateDemoObject(val demoObjectUI: DemoObjectUI) : CreateIntent()
}