package com.vnteam.architecturetemplates

import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class JsonConverter {

    private var moshi: Moshi? = null

    init {
        moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    fun getDemoObjectList(responseData: String): List<DemoObject> {
        val listType = Types.newParameterizedType(List::class.java, DemoObject::class.java)
        val adapter: JsonAdapter<List<DemoObject>>? = moshi?.adapter(listType)
        return adapter?.fromJson(responseData) ?: listOf()
    }

    fun demoObjectListToDemoObjectDBList(demoObjects: List<DemoObject>): List<DemoObjectDB> {
        val demoObjectDBList = mutableListOf<DemoObjectDB>()
        demoObjects.forEach { demoObject ->
            demoObjectDBList.add(DemoObjectDB().apply {
                id = demoObject.id
                name = demoObject.name
                fullName = demoObject.fullName
                htmlUrl = demoObject.htmlUrl
                description = demoObject.description
                owner = moshi?.adapter(Owner::class.java)?.toJson(demoObject.owner)
            })
        }
        return demoObjectDBList
    }
}