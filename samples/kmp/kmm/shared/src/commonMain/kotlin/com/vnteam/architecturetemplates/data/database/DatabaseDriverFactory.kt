package com.vnteam.architecturetemplates.data.database

import com.squareup.sqldelight.db.SqlDriver

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}