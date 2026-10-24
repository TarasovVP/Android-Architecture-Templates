package com.vnteam.architecturetemplates

object Constants {
    const val STORAGE_TYPE = "storage"
    const val SQL_JS_WORKER_URL = """new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)"""
    const val RANDOM_UIID = "crypto.randomUUID().toString()"
}
