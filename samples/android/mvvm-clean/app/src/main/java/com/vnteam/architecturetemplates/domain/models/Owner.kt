package com.vnteam.architecturetemplates.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner(
    var login: String? = null,
    @SerializedName("id")
    var ownerId: Long? = null,
    @SerializedName("avatar_url")
    var avatarUrl: String? = null,
) : Parcelable