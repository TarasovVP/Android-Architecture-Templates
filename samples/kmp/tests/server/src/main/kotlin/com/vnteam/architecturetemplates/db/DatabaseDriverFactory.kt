package com.vnteam.architecturetemplates.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.vnteam.architecturetemplates.DB_USER_NAME
import com.vnteam.architecturetemplates.DB_USER_PASSWORD
import com.vnteam.architecturetemplates.DRIVER_CLASS_NAME
import com.vnteam.architecturetemplates.JDBC_URL
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

class DatabaseDriverFactory {
    fun createDriver(): SqlDriver {
        val config = HikariConfig().apply {
            jdbcUrl = System.getenv("JDBC_URL") ?: JDBC_URL
            driverClassName = System.getenv("DRIVER_CLASS_NAME") ?: DRIVER_CLASS_NAME
            username = System.getenv("DB_USER_NAME") ?: DB_USER_NAME
            password = System.getenv("DB_USER_PASSWORD") ?: DB_USER_PASSWORD

            maximumPoolSize = 5
            minimumIdle = 2
            idleTimeout = 10000
            connectionTimeout = 30000
            maxLifetime = 1800000
        }
        val dataSource = HikariDataSource(config)
        return dataSource.asJdbcDriver()
    }
}