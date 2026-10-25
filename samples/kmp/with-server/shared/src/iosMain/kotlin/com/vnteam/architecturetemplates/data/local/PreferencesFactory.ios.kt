package com.vnteam.architecturetemplates.data.local

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.vnteam.architecturetemplates.data.PREFERENCES_PB
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PreferencesFactory : Preferences {

    @OptIn(ExperimentalForeignApi::class)
    private val dataStore = PreferenceDataStoreFactory.createWithPath(
        produceFile = { val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
            (documentDirectory?.path + "/$PREFERENCES_PB").toPath()
        }
    )
    actual override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    actual override suspend fun getString(key: String): Flow<String?> {
        val preferencesKey = stringPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[preferencesKey]
        }
    }

    actual override suspend fun putBoolean(key: String, value: Boolean) {
        val preferencesKey = booleanPreferencesKey(key)
        dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    actual override suspend fun getBoolean(key: String): Flow<Boolean> {
        val preferencesKey = booleanPreferencesKey(key)
        return dataStore.data.map { preferences ->
            preferences[preferencesKey] ?: false
        }
    }
}