package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.DeleteDemoObjectUseCase

class DeleteDemoObjectUseCaseImpl(
    private val dbRepository: DBRepository,
    private val apiRepository: ApiRepository,
) : DeleteDemoObjectUseCase {
    override suspend fun execute(params: String) {
        apiRepository.deleteDemoObjectById(params)
        dbRepository.deleteDemoObjectById(params)
    }
}
