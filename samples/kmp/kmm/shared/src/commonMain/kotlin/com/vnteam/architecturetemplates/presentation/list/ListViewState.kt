package com.vnteam.architecturetemplates.presentation.list

import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI

data class ListViewState(val demoObjects: List<DemoObjectUI>? = null,
                         val isLoading: Boolean = false,
                         val error: String? = null)
