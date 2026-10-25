package com.vnteam.architecturetemplates.domain.repositories

import com.vnteam.architecturetemplates.domain.models.DemoObject
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    suspend fun getDemoObjectsFromApi(): Flow<List<DemoObject>?>

    suspend fun insertDemoObjectsToApi(demoObjects: List<DemoObject>?): Flow<Unit>

    suspend fun getDemoObjectById(demoObjectId: String?): Flow<DemoObject?>

    suspend fun deleteDemoObjectById(demoObjectId: String): Flow<Unit>
}