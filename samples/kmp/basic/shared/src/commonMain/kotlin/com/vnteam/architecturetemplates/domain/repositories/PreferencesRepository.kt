package com.vnteam.architecturetemplates.domain.repositories

interface PreferencesRepository {

    suspend fun setIsDarkTheme(isDarkTheme: Boolean)

    suspend fun getIsDarkTheme(): Boolean

    suspend fun setLanguage(language: String)

    suspend fun getLanguage(): String?
}