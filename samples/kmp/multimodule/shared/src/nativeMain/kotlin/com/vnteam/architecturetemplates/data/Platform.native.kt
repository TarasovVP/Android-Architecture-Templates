package com.vnteam.architecturetemplates.data

import platform.Foundation.NSUUID

actual fun generateUUID(): String = NSUUID().UUIDString()

actual fun baseUrl(): String = CLOUD_URL