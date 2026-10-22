package com.vnteam.architecturetemplates.domain.repositories

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    suspend fun setIsDarkTheme(isDarkTheme: Boolean)

    fun getIsDarkTheme(): Flow<Boolean>

    suspend fun setLanguage(language: String)

    fun getLanguage(): Flow<String?>
}
