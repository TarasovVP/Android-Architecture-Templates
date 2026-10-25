package com.vnteam.architecturetemplates

import com.squareup.moshi.Json
import java.io.Serializable

class Owner: Serializable {
    @Json(name = "login")
    var login: String? = null
    @Json(name = "id")
    var ownerId: Long? = null
    @Json(name = "avatar_url")
    var avatarUrl: String? = null
}