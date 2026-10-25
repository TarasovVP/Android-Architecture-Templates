package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.ListUseCase
import kotlinx.coroutines.flow.Flow

class ListUseCaseImpl(private val apiRepository: ApiRepository, private val dbRepository: DBRepository) :
    ListUseCase {

    override suspend fun getDemoObjectsFromApi(): Flow<List<DemoObject>?> {
        return apiRepository.getDemoObjectsFromApi()
    }

    override suspend fun clearDemoObjects() {
        return dbRepository.clearDemoObjects()
    }

    override suspend fun insertDemoObjectsToDB(demoObjects: List<DemoObject>): Flow<Unit> {
        return dbRepository.insertDemoObjectsToDB(demoObjects)
    }

    override suspend fun getDemoObjectsFromDB(): Flow<List<DemoObject>> {
        return dbRepository.getDemoObjectsFromDB()
    }

    override suspend fun deleteDemoObjectById(id: String): Flow<Unit> {
        return apiRepository.deleteDemoObjectById(id)
    }
}