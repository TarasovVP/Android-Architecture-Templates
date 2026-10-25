package com.vnteam.architecturetemplates.fake.data.repositoryimpl

import com.vnteam.architecturetemplates.domain.repositories.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePreferencesRepository : PreferencesRepository {
    private var darkTheme: Boolean = false
    private var language: String = ""

    override suspend fun setIsDarkTheme(isDarkTheme: Boolean) {
        darkTheme = isDarkTheme
    }

    override fun getIsDarkTheme(): Flow<Boolean> {
        return flow {
            emit(darkTheme)
        }
    }

    override suspend fun setLanguage(language: String) {
        this.language = language
    }

    override fun getLanguage(): Flow<String?> {
        return flow {
            emit(language)
        }
    }
}