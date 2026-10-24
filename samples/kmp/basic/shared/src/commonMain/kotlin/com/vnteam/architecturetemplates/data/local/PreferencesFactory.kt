package com.vnteam.architecturetemplates.data.local

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class PreferencesFactory: Preferences {
    override suspend fun getString(key: String): String?
    override suspend fun putString(key: String, value: String)
    override suspend fun getBoolean(key: String): Boolean
    override suspend fun putBoolean(key: String, value: Boolean)
}