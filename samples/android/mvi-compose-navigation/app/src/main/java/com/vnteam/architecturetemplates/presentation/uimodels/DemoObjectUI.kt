package com.vnteam.architecturetemplates.presentation.uimodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DemoObjectUI(
    var id: Long? = null,
    var name: String? = null,
    var fullName: String? = null,
    var owner: OwnerUI? = null,
    var htmlUrl: String? = null,
    var description: String? = null,
) : Parcelable