package com.vnteam.architecturetemplates.presentation.uimodels

import kotlinx.serialization.Serializable

@Serializable
data class DemoObjectUI(
    var demoObjectId: String? = null,
    var name: String? = null,
    var owner: OwnerUI? = null,
    var htmlUrl: String? = null,
    var description: String? = null,
) {
    fun isDemoObjectValid(): Boolean {
        return !name.isNullOrEmpty() && !owner?.login.isNullOrEmpty()
    }
}