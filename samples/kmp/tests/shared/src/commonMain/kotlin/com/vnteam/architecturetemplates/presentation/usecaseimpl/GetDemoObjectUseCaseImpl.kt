package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf

class GetDemoObjectUseCaseImpl(
    private val apiRepository: ApiRepository,
    private val dbRepository: DBRepository
) : GetDemoObjectUseCase {

    override suspend fun execute(params: String): DemoObject? {
        val dbDemoObject = dbRepository.getDemoObjectById(params).firstOrNull()
        return if (dbDemoObject != null) {
            dbDemoObject
        } else {
            apiRepository.getDemoObjectById(params)
        }
    }
}