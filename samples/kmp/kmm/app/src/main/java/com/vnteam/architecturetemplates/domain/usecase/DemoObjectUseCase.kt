package com.vnteam.architecturetemplates.domain.usecase

import com.vnteam.architecturetemplates.domain.models.DemoObject
import kotlinx.coroutines.flow.Flow

interface DemoObjectUseCase {

    suspend fun getDemoObjectsFromApi(): Flow<List<DemoObject>?>

    fun insertDemoObjectsToDB(demoObjects: List<DemoObject>)

    fun getDemoObjectsFromDB(): Flow<List<DemoObject>>

    fun getDemoObjectById(demoObjectId: Long): Flow<DemoObject?>
}