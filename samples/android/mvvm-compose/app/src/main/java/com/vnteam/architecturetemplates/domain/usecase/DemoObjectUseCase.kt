package com.vnteam.architecturetemplates.domain.usecase

import com.vnteam.architecturetemplates.domain.models.DemoObject

interface DemoObjectUseCase {

    suspend fun getDemoObjectsFromApi(): List<DemoObject>?

    fun insertDemoObjectsToDB(demoObjects: List<DemoObject>)

    fun getDemoObjectsFromDB(): List<DemoObject>

    fun getDemoObjectById(demoObjectId: Long): DemoObject
}