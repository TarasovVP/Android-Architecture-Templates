package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.DeleteDemoObjectUseCase

class DeleteDemoObjectUseCaseImpl(
    private val dbRepository: DBRepository,
) : DeleteDemoObjectUseCase {
    override suspend fun execute(params: String) {
        dbRepository.deleteDemoObjectById(params)
    }
}
