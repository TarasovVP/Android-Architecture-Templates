package com.vnteam.architecturetemplates

const val HTTP_HOST = "0.0.0.0"
const val PORT = "PORT"
const val JDBC_URL_KEY = "JDBC_URL"
const val DRIVER_CLASS_NAME_KEY = "DRIVER_CLASS_NAME"
const val DB_USER_NAME_KEY = "DB_USER_NAME"
const val DB_USER_PASSWORD_KEY = "DB_USER_PASSWORD"
const val HTTP_PORT = 8080
const val DRIVER_CLASS_NAME = "org.postgresql.Driver"
const val JDBC_URL =
    "jdbc:postgresql:///DemoObjectWithOwner?cloudSqlInstance=android-architecture-templates:us-central1:" +
        "android-architecture-templates-db&socketFactory=com.google.cloud.sql.postgres.SocketFactory"
const val DB_USER_NAME = "dbusername"
const val DB_USER_PASSWORD = "yourpassword"

const val DEMO_OBJECTS_ROUTE = "/demoObjects"
const val DEMO_OBJECTS_ID_ROUTE = "/demoObjects/{id}"
const val DEMO_OBJECTS_ID = "id"

const val MAXIMUM_POOL_SIZE = 5
const val MINIMUM_IDLE = 2
const val IDLE_TIMEOUT = 10000L
const val CONNECTION_TIMEOUT = 30000L
const val MAX_LIFE_TIME = 1800000L
