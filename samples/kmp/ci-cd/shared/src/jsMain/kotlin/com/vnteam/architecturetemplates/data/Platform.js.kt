package com.vnteam.architecturetemplates.data

import com.vnteam.architecturetemplates.Constants

actual fun generateUUID(): String = js(Constants.RANDOM_UIID).unsafeCast<String>()
