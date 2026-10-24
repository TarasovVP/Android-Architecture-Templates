package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromDBUseCase
import kotlinx.coroutines.flow.Flow

class GetDemoObjectsFromDBUseCaseImpl(
    private val dbRepository: DBRepository,
) : GetDemoObjectsFromDBUseCase {
    override suspend fun execute(params: Nothing?): Flow<List<DemoObject>?> {
        return dbRepository.getDemoObjectsFromDB()
    }
}
