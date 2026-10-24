package com.vnteam.architecturetemplates.data.local

import kotlinx.coroutines.flow.Flow

interface Preferences {

    suspend fun putString(key: String, value: String)
    fun getString(key: String): Flow<String?>

    suspend fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Flow<Boolean>
}