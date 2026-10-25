package com.vnteam.architecturetemplates

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.io.Serializable

@DatabaseTable
class DemoObjectDB: Serializable {
    @DatabaseField(columnName = "id")
    var id: Long? = null
    @DatabaseField(columnName = "name")
    var name: String? = null
    @DatabaseField(columnName = "full_name")
    var fullName: String? = null
    @DatabaseField(columnName = "owner")
    var owner: String? = null
    @DatabaseField(columnName = "html_url")
    var htmlUrl: String? = null
    @DatabaseField(columnName = "description")
    var description: String? = null
}
