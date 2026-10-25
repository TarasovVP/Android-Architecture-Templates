package com.vnteam.architecturetemplates.presentation.details

import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI


data class DetailsViewState(val demoObject: DemoObjectUI? = null,
                            val isLoading: Boolean = false,
                            val error: String? = null)
