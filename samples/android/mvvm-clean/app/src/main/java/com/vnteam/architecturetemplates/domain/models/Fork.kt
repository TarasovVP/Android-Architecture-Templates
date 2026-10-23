package com.vnteam.architecturetemplates.domain.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Fork(
    var id: Long? = null,
    var name: String? = null,
    @SerializedName("full_name")
    var fullName: String? = null,
    var owner: Owner? = null,
    @SerializedName("html_url")
    var htmlUrl: String? = null,
    var description: String? = null,
) : Parcelable