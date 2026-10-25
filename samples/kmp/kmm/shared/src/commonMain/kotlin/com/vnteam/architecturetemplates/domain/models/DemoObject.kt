package com.vnteam.architecturetemplates.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DemoObject(
    var id: Long? = null,
    var name: String? = null,
    @SerialName("full_name")
    var fullName: String? = null,
    var owner: Owner? = null,
    @SerialName("html_url")
    var htmlUrl: String? = null,
    var description: String? = null,
)