package com.vnteam.architecturetemplates.data.local

import kotlinx.coroutines.flow.Flow

interface Preferences {

    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): Flow<String?>

    suspend fun putBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String): Flow<Boolean>
}