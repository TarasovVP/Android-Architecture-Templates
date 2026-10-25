package com.vnteam.architecturetemplates.fake.data.local

import com.vnteam.architecturetemplates.data.local.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePreferencesFactory : Preferences {
    private val booleanPrefs = mutableMapOf<String, Boolean>()
    private val stringPrefs = mutableMapOf<String, String?>()

    override suspend fun putBoolean(key: String, value: Boolean) {
        booleanPrefs[key] = value
    }

    override fun getBoolean(key: String): Flow<Boolean> = flow {
        emit(booleanPrefs[key] ?: false)
    }

    override suspend fun putString(key: String, value: String) {
        stringPrefs[key] = value
    }

    override fun getString(key: String): Flow<String?> = flow {
        emit(stringPrefs[key])
    }
}