package com.vnteam.architecturetemplates.data.local

import kotlinx.browser.window
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.w3c.dom.Storage
import org.w3c.dom.StorageEvent

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class PreferencesFactory : Preferences {

    private val localStorage: Storage = window.localStorage

    private val stringFlows = mutableMapOf<String, MutableStateFlow<String?>>()
    private val booleanFlows = mutableMapOf<String, MutableStateFlow<Boolean>>()

    init {
        window.addEventListener("storage", { event ->
            event as StorageEvent
            event.key?.let { key ->
                event.newValue?.let { newValue ->
                    stringFlows[key]?.value = newValue
                    booleanFlows[key]?.value = newValue.toBoolean()
                }
            }
        })
    }

    actual override suspend fun putString(key: String, value: String) {
        localStorage.setItem(key, value)
        stringFlows[key]?.value = value
    }

    actual override suspend fun getString(key: String): Flow<String?> {
        val flow = stringFlows.getOrPut(key) { MutableStateFlow(localStorage.getItem(key)) }
        return flow
    }

    actual override suspend fun putBoolean(key: String, value: Boolean) {
        localStorage.setItem(key, value.toString())
        booleanFlows[key]?.value = value
    }

    actual override suspend fun getBoolean(key: String): Flow<Boolean> {
        val flow = booleanFlows.getOrPut(key) { MutableStateFlow(localStorage.getItem(key)?.toBoolean() ?: false) }
        return flow
    }
}