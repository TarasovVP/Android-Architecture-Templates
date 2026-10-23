package com.vnteam.architecturetemplates.presentation.list

sealed class ListIntent {
    data class LoadDemoObjects(val demoObjectId: Long) : ListIntent()
}