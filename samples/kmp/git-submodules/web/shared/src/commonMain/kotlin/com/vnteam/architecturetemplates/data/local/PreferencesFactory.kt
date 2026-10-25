package com.vnteam.architecturetemplates.data.local

import kotlinx.coroutines.flow.Flow

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PreferencesFactory: Preferences {
    override suspend fun getString(key: String): Flow<String?>
    override suspend fun putString(key: String, value: String)
    override suspend fun getBoolean(key: String): Flow<Boolean>
    override suspend fun putBoolean(key: String, value: Boolean)
}