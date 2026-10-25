package com.vnteam.architecturetemplates.presentation.details

sealed class DetailsIntent {
    data class LoadDemoObject(val demoObjectId: Long) : DetailsIntent()
}