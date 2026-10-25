package com.vnteam.architecturetemplates.domain.models

data class Owner(
    var login: String? = null,
    var ownerId: String? = null,
    var avatarUrl: String? = null,
    var url: String? = null
)