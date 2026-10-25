package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.InsertDemoObjectsUseCase

class InsertDemoObjectsUseCaseImpl(
    private val dbRepository: DBRepository,
) : InsertDemoObjectsUseCase {
    override suspend fun execute(params: List<DemoObject>) {
        return dbRepository.insertDemoObjectsToDB(params)
    }
}
