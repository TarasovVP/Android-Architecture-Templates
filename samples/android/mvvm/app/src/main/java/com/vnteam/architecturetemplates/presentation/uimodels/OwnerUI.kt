package com.vnteam.architecturetemplates.presentation.uimodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OwnerUI(
    var login: String? = null,
    var ownerId: Long? = null,
    var avatarUrl: String? = null,
) : Parcelable