package com.vnteam.architecturetemplates.data.network.responses

import com.google.gson.annotations.SerializedName

data class OwnerResponse(
    var login: String? = null,
    @SerializedName("id")
    var ownerId: Long? = null,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
)