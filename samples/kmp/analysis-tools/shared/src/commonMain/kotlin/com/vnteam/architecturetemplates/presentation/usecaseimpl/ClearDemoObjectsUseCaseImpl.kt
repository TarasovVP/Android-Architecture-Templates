package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.ClearDemoObjectUseCase

class ClearDemoObjectsUseCaseImpl(
    private val dbRepository: DBRepository,
) : ClearDemoObjectUseCase {
    override suspend fun execute(params: Nothing?) {
        dbRepository.clearDemoObjects()
    }
}
