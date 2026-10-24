package com.vnteam.architecturetemplates

const val HTTP_HOST = "0.0.0.0"
const val HTTP_PORT = 8080
const val DB_NAME = "DemoObjectWithOwner"
const val DB_INSTANCE_CONNECTION_NAME = "android-architecture-templates:us-central1:android-architecture-templates-db"
const val JDBC_URL = "jdbc:postgresql:///$DB_NAME?cloudSqlInstance=$DB_INSTANCE_CONNECTION_NAME&socketFactory=com.google.cloud.sql.postgres.SocketFactory"
const val DRIVER_CLASS_NAME = "org.postgresql.Driver"
const val DB_USER_NAME = "dbusername"
const val DB_USER_PASSWORD = "yourpassword"