package com.vnteam.architecturetemplates.data.database

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.vnteam.architecturetemplates.appdatabase.AppDatabase
import com.vnteam.architecturetemplates.data.DEMO_OBJECTS_DB

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory(
    private val context: Context,
) {
    actual suspend fun createDriver(): SqlDriver =
        AndroidSqliteDriver(
            schema = AppDatabase.Schema.synchronous(),
            context,
            DEMO_OBJECTS_DB,
        )
}
