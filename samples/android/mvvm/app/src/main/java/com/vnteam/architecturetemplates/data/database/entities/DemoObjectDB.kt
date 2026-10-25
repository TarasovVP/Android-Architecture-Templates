package com.vnteam.architecturetemplates.data.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("demoObjects")
data class DemoObjectDB(
    @PrimaryKey
    var id: Long? = null,
    var name: String? = null,
    var fullName: String? = null,
    @Embedded("owner")
    var owner: OwnerDB? = null,
    var htmlUrl: String? = null,
    var description: String? = null,
)