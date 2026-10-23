package com.vnteam.architecturetemplates.data.network.responses

import com.google.gson.annotations.SerializedName

data class ForkResponse(
    var id: Long? = null,
    var name: String? = null,
    @SerializedName("full_name")
    var fullName: String? = null,
    var owner: OwnerResponse? = null,
    @SerializedName("html_url")
    var htmlUrl: String? = null,
    var description: String? = null,
)