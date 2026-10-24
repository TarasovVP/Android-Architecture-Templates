package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.CreateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf

class CreateUseCaseImpl(private val apiRepository: ApiRepository, private val dbRepository: DBRepository) :
    CreateUseCase {

    override suspend fun getDemoObjectById(id: String): Flow<DemoObject?> {
        val dbDemoObject = dbRepository.getDemoObjectById(id).firstOrNull()
        return if (dbDemoObject != null) {
            flowOf(dbDemoObject)
        } else {
            apiRepository.getDemoObjectById(id)
        }
    }

    override suspend fun insertDemoObjectToDB(demoObject: DemoObject): Flow<Unit> {
        return dbRepository.insertDemoObjectsToDB(listOf(demoObject))
    }


    override suspend fun createDemoObject(demoObject: DemoObject): Flow<Unit> {
        return apiRepository.insertDemoObjectsToApi(listOf(demoObject))
    }
}