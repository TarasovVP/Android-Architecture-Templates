package com.vnteam.architecturetemplates.domain.usecase

interface AppUseCase {

    suspend fun setIsDarkTheme(isDarkTheme: Boolean)

    suspend fun getIsDarkTheme(): Boolean

    suspend fun setLanguage(language: String)

    suspend fun getLanguage(): String?
}