package com.vnteam.architecturetemplates.demo_object_service

import com.vnteam.architecturetemplates.domain.models.DemoObject

interface DemoObjectService {

    suspend fun insertDemoObjects(demoObjects: List<DemoObject>)

    suspend fun getDemoObjects(): List<DemoObject>?

    suspend fun getDemoObjectById(demoObjectId: String): DemoObject?

    suspend fun deleteDemoObjectById(demoObjectId: String)
}