package com.vnteam.architecturetemplates.data

import java.util.UUID

actual fun generateUUID(): String = UUID.randomUUID().toString()