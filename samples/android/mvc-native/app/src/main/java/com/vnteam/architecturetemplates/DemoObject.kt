package com.vnteam.architecturetemplates

import java.io.Serializable

class DemoObject: Serializable {
    var id: Long? = null
    var name: String? = null
    var full_name: String? = null
    var owner: Owner? = Owner()
    var html_url: String? = null
    var description: String? = null
}
