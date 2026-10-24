package com.vnteam.architecturetemplates.data.database

import com.vnteam.architecturetemplates.appdatabase.AppDatabase
import com.vnteam.architecturetemplates.data.DATABASE_NOT_INITIALIZED

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
        } ?: throw IllegalStateException(DATABASE_NOT_INITIALIZED)
    }
}
