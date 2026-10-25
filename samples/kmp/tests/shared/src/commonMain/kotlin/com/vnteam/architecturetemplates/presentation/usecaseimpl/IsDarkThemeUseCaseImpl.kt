package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.repositories.PreferencesRepository
import com.vnteam.architecturetemplates.domain.usecase.IsDarkThemeUseCase
import kotlinx.coroutines.flow.Flow

class IsDarkThemeUseCaseImpl(private val preferencesRepository: PreferencesRepository) :
    IsDarkThemeUseCase {

    override fun get(): Flow<Boolean?> {
        return preferencesRepository.getIsDarkTheme()
    }

    override suspend fun set(params: Boolean) {
        preferencesRepository.setIsDarkTheme(params)
    }
}