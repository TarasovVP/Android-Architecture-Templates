package com.vnteam.architecturetemplates.data.database

import com.vnteam.architecturetemplates.AppDatabase

class SharedDatabase(private val databaseDriverFactory: DatabaseDriverFactory) {
    private var database: AppDatabase? = null

    private suspend fun initDatabase() {
        database.takeIf { it != null } ?: run {
            database = AppDatabase(databaseDriverFactory.createDriver())
        }
    }

    suspend operator fun <R> invoke(block: suspend (AppDatabase) -> R): R {
        initDatabase()
        return database.takeIf { it != null }?.let {
            block(it)
        } ?: throw IllegalStateException("Database is not initialized")
    }
}