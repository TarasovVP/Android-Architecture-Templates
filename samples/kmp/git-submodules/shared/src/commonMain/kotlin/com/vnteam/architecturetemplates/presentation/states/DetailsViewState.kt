package com.vnteam.architecturetemplates.presentation.states

import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI


data class DetailsViewState(val demoObjectUI: DemoObjectUI? = null,
                            var successResult: Boolean = false
)
