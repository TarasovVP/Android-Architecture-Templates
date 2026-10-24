package com.vnteam.architecturetemplates.domain.repositories

import com.vnteam.architecturetemplates.domain.models.DemoObject
import kotlinx.coroutines.flow.Flow

interface DBRepository {

    fun insertDemoObjectsToDB(demoObjects: List<DemoObject>)

    fun getDemoObjectsFromDB(): Flow<List<DemoObject>>

    fun getDemoObjectById(demoObjectId: Long): Flow<DemoObject?>
}