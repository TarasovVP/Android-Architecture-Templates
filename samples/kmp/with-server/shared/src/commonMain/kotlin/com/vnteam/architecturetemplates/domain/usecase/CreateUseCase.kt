package com.vnteam.architecturetemplates.domain.usecase

import com.vnteam.architecturetemplates.domain.models.DemoObject
import kotlinx.coroutines.flow.Flow

interface CreateUseCase {

    suspend fun getDemoObjectById(id: String): Flow<DemoObject?>

    suspend fun insertDemoObjectToDB(demoObject: DemoObject): Flow<Unit>

    suspend fun createDemoObject(demoObject: DemoObject): Flow<Unit>
}