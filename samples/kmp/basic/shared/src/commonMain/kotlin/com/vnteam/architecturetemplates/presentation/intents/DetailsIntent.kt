package com.vnteam.architecturetemplates.presentation.intents

sealed class DetailsIntent {
    data class LoadDemoObject(val demoObjectId: Long) : DetailsIntent()
}