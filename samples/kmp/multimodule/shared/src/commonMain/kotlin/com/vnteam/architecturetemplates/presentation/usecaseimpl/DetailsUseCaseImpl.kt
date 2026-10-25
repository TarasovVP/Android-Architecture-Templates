package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.DetailsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf

class DetailsUseCaseImpl(private val dbRepository: DBRepository, private val apiRepository: ApiRepository) :
    DetailsUseCase {

    override suspend fun getDemoObjectById(id: String): Flow<DemoObject?> {
        val dbDemoObject = dbRepository.getDemoObjectById(id).firstOrNull()
        return if (dbDemoObject != null) {
            flowOf(dbDemoObject)
        } else {
            apiRepository.getDemoObjectById(id)
        }
    }
}