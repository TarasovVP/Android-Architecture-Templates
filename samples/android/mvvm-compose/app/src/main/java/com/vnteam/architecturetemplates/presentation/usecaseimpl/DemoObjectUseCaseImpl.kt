package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.DemoObjectUseCase

class DemoObjectUseCaseImpl(private val apiRepository: ApiRepository, private val dbRepository: DBRepository) :
    DemoObjectUseCase {

    override suspend fun getDemoObjectsFromApi(): List<DemoObject>? {
        return apiRepository.getDemoObjectsFromApi()
    }

    override fun insertDemoObjectsToDB(demoObjects: List<DemoObject>) {
        dbRepository.insertDemoObjectsToDB(demoObjects)
    }

    override fun getDemoObjectsFromDB(): List<DemoObject> {
        return dbRepository.getDemoObjectsFromDB()
    }

    override fun getDemoObjectById(demoObjectId: Long): DemoObject {
        return dbRepository.getDemoObjectById(demoObjectId)
    }
}