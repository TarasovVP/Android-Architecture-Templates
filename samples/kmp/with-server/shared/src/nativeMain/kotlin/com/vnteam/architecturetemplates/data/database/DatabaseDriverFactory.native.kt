package com.vnteam.architecturetemplates.data.database

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.vnteam.architecturetemplates.AppDatabase
import com.vnteam.architecturetemplates.data.DEMO_OBJECTS_DB

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        return NativeSqliteDriver(AppDatabase.Schema.synchronous(), DEMO_OBJECTS_DB)
    }
}