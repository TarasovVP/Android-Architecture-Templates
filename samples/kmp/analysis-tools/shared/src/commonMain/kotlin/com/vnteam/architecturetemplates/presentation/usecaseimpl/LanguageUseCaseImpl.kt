package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.repositories.PreferencesRepository
import com.vnteam.architecturetemplates.domain.usecase.LanguageUseCase
import kotlinx.coroutines.flow.Flow

class LanguageUseCaseImpl(private val preferencesRepository: PreferencesRepository) :
    LanguageUseCase {
    override fun get(): Flow<String?> = preferencesRepository.getLanguage()

    override suspend fun set(params: String) {
        preferencesRepository.setLanguage(params)
    }
}
