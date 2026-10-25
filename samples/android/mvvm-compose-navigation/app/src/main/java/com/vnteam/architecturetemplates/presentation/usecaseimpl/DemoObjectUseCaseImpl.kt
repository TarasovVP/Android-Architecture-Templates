package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.DemoObjectUseCase
import kotlinx.coroutines.flow.Flow

class DemoObjectUseCaseImpl(private val apiRepository: ApiRepository, private val dbRepository: DBRepository) :
    DemoObjectUseCase {

    override suspend fun getDemoObjectsFromApi(): Flow<List<DemoObject>?> {
        return apiRepository.getDemoObjectsFromApi()
    }

    override fun insertDemoObjectsToDB(demoObjects: List<DemoObject>) {
        dbRepository.insertDemoObjectsToDB(demoObjects)
    }

    override fun getDemoObjectsFromDB(): Flow<List<DemoObject>> {
        return dbRepository.getDemoObjectsFromDB()
    }

    override fun getDemoObjectById(demoObjectId: Long): Flow<DemoObject?> {
        return dbRepository.getDemoObjectById(demoObjectId)
    }
}