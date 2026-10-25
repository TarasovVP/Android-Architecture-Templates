package com.vnteam.architecturetemplates.presentation.usecaseimpl

import com.vnteam.architecturetemplates.domain.repositories.PreferencesRepository
import com.vnteam.architecturetemplates.domain.usecase.AppUseCase

class AppUseCaseImpl(private val preferencesRepository: PreferencesRepository) :
    AppUseCase {
    override suspend fun setIsDarkTheme(isDarkTheme: Boolean) {
        preferencesRepository.setIsDarkTheme(isDarkTheme)
    }

    override suspend fun getIsDarkTheme(): Boolean {
        return preferencesRepository.getIsDarkTheme()
    }

    override suspend fun setLanguage(language: String) {
        preferencesRepository.setLanguage(language)
    }

    override suspend fun getLanguage(): String? {
        return preferencesRepository.getLanguage()
    }

}