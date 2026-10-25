package com.vnteam.architecturetemplates.utils

import android.app.Activity
import android.content.Intent

private const val PLAIN_TEXT_TYPE = "text/plain"
private const val ACTIVITY_PROVIDER_ERROR =
    "You need to implement the 'activityProvider' to provide the required Activity. " +
        "Just make sure to set a valid activity using " +
        "the 'setActivityProvider()' method."

actual fun shareLink(url: String) {
    val intent =
        Intent(Intent.ACTION_SEND).apply {
            type = PLAIN_TEXT_TYPE
            putExtra(Intent.EXTRA_TEXT, url)
        }
    val intentChooser = Intent.createChooser(intent, null)
    activityProvider.invoke().startActivity(intentChooser)
}

private var activityProvider: () -> Activity = {
    throw IllegalArgumentException(ACTIVITY_PROVIDER_ERROR)
}

fun setActivityProvider(provider: () -> Activity) {
    activityProvider = provider
}
