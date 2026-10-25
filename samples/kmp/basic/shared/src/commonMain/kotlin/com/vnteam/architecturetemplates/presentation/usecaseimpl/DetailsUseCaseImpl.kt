package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.DetailsUseCase
import kotlinx.coroutines.flow.Flow

class DetailsUseCaseImpl(private val dbRepository: DBRepository) :
    DetailsUseCase {

    override suspend fun getDemoObjectById(id: Long): Flow<DemoObject?> {
        return dbRepository.getDemoObjectById(id)
    }
}