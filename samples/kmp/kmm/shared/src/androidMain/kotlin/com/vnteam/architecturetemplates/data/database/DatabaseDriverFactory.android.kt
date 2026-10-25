package com.vnteam.architecturetemplates.data.database

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.vnteam.architecturetemplates.AppDatabase

actual class DatabaseDriverFactory(private val context: android.content.Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(AppDatabase.Schema, context, "demoObjects.db")
    }
}