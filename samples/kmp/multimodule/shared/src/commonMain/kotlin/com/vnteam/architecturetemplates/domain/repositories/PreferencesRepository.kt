package com.vnteam.architecturetemplates.domain.repositories

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    suspend fun setIsDarkTheme(isDarkTheme: Boolean)

    suspend fun getIsDarkTheme(): Flow<Boolean>

    suspend fun setLanguage(language: String)

    suspend fun getLanguage(): Flow<String?>
}