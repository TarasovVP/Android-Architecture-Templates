package com.vnteam.architecturetemplates.domain.usecase

import com.vnteam.architecturetemplates.domain.models.DemoObject
import kotlinx.coroutines.flow.Flow

interface ListUseCase {

    suspend fun clearDemoObjects()

    suspend fun getDemoObjectsFromApi(): Flow<List<DemoObject>?>

    suspend fun insertDemoObjectsToDB(demoObjects: List<DemoObject>): Flow<Unit>

    suspend fun getDemoObjectsFromDB(): Flow<List<DemoObject>>

    suspend fun deleteDemoObjectById(id: String): Flow<Unit>
}