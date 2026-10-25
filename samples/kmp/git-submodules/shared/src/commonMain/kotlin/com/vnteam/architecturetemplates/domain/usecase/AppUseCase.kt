package com.vnteam.architecturetemplates.domain.usecase

import kotlinx.coroutines.flow.Flow

interface AppUseCase {

    suspend fun setIsDarkTheme(isDarkTheme: Boolean)

    suspend fun getIsDarkTheme(): Flow<Boolean>

    suspend fun setLanguage(language: String)

    suspend fun getLanguage(): Flow<String?>
}