package com.vnteam.architecturetemplates

import org.json.JSONArray
import org.json.JSONObject

class JsonConverter {

    fun getDemoObjectList(responseData: String): ArrayList<DemoObject> {
        val demoObjectList = ArrayList<DemoObject>()
        val jsonArray = JSONArray(responseData)
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.get(i) as JSONObject
            demoObjectList.add(createForkObject(jsonObject))
        }
        return demoObjectList
    }

    private fun createForkObject(jsonObject: JSONObject): DemoObject {
        val demoObject = DemoObject()
        demoObject.id = jsonObject.getLong("id")
        demoObject.name = jsonObject.getString("name")
        demoObject.full_name = jsonObject.getString("full_name")
        demoObject.html_url = jsonObject.getString("html_url")
        demoObject.description = jsonObject.getString("description")
        val ownerObject = jsonObject.getJSONObject("owner")
        val owner = Owner()
        owner.login = ownerObject.getString("login")
        owner.id = ownerObject.getLong("id")
        owner.avatar_url = ownerObject.getString("avatar_url")
        demoObject.owner = owner
        return demoObject
    }
}