package com.vnteam.architecturetemplates.domain.repositories

import com.vnteam.architecturetemplates.domain.models.DemoObject

interface ApiRepository {

    suspend fun getDemoObjectsFromApi(): List<DemoObject>?

    suspend fun insertDemoObjectsToApi(demoObjects: List<DemoObject>?)

    suspend fun getDemoObjectById(demoObjectId: String?): DemoObject?

    suspend fun deleteDemoObjectById(demoObjectId: String)
}