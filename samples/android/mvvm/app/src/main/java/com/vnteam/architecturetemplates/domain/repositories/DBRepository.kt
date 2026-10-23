package com.vnteam.architecturetemplates.domain.repositories

import com.vnteam.architecturetemplates.domain.models.DemoObject

interface DBRepository {

    fun insertDemoObjectsToDB(demoObjects: List<DemoObject>)

    fun getDemoObjectsFromDB(): List<DemoObject>

    fun getDemoObjectById(demoObjectId: Long): DemoObject
}