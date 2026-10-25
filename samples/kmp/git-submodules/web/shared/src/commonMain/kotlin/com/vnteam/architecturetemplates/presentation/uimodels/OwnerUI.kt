package com.vnteam.architecturetemplates.presentation.uimodels

import kotlinx.serialization.Serializable

@Serializable
data class OwnerUI(
    var login: String? = null,
    var ownerId: String? = null,
    var avatarUrl: String? = null,
    var url: String? = null
)