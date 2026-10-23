package com.vnteam.architecturetemplates.models

import com.squareup.moshi.Json
import io.realm.RealmObject
import java.io.Serializable

open class Owner: Serializable, RealmObject() {
    @Json(name = "login")
    var login: String? = null
    @Json(name = "id")
    var ownerId: Long? = null
    @Json(name = "avatar_url")
    var avatarUrl: String? = null
}