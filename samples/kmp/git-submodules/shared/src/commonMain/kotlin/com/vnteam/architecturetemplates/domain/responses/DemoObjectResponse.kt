package com.vnteam.architecturetemplates.domain.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DemoObjectResponse(
    var demoObjectId: String? = null,
    var name: String? = null,
    var owner: OwnerResponse? = null,
    @SerialName("html_url")
    var htmlUrl: String? = null,
    var description: String? = null,
)