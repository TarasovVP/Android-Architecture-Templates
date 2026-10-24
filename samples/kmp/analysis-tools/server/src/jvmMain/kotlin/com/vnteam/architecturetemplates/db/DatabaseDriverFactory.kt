package com.vnteam.architecturetemplates.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.vnteam.architecturetemplates.CONNECTION_TIMEOUT
import com.vnteam.architecturetemplates.DB_USER_NAME
import com.vnteam.architecturetemplates.DB_USER_NAME_KEY
import com.vnteam.architecturetemplates.DB_USER_PASSWORD
import com.vnteam.architecturetemplates.DB_USER_PASSWORD_KEY
import com.vnteam.architecturetemplates.DRIVER_CLASS_NAME
import com.vnteam.architecturetemplates.DRIVER_CLASS_NAME_KEY
import com.vnteam.architecturetemplates.IDLE_TIMEOUT
import com.vnteam.architecturetemplates.JDBC_URL
import com.vnteam.architecturetemplates.JDBC_URL_KEY
import com.vnteam.architecturetemplates.MAXIMUM_POOL_SIZE
import com.vnteam.architecturetemplates.MAX_LIFE_TIME
import com.vnteam.architecturetemplates.MINIMUM_IDLE
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver {
        val config =
            HikariConfig().apply {
                jdbcUrl = System.getenv(JDBC_URL_KEY) ?: JDBC_URL
                driverClassName = System.getenv(DRIVER_CLASS_NAME_KEY) ?: DRIVER_CLASS_NAME
                username = System.getenv(DB_USER_NAME_KEY) ?: DB_USER_NAME
                password = System.getenv(DB_USER_PASSWORD_KEY) ?: DB_USER_PASSWORD

                maximumPoolSize = MAXIMUM_POOL_SIZE
                minimumIdle = MINIMUM_IDLE
                idleTimeout = IDLE_TIMEOUT
                connectionTimeout = CONNECTION_TIMEOUT
                maxLifetime = MAX_LIFE_TIME
            }
        val dataSource = HikariDataSource(config)
        return dataSource.asJdbcDriver()
    }
}
