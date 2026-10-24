package com.vnteam.architecturetemplates.domain.repositories

import com.vnteam.architecturetemplates.domain.models.DemoObject
import kotlinx.coroutines.flow.Flow

interface DBRepository {

    suspend fun clearDemoObjects()

    suspend fun insertDemoObjectsToDB(demoObjects: List<DemoObject>): Flow<Unit>

    suspend fun getDemoObjectsFromDB(): Flow<List<DemoObject>>

    suspend fun getDemoObjectById(demoObjectId: Long): Flow<DemoObject?>
}