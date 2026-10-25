package com.vnteam.architecturetemplates.presentation.intents

sealed class ListIntent {
     class ClearDemoObjects : ListIntent()
     class LoadDemoObjects(val isInit: Boolean) : ListIntent()
     class DeleteDemoObject(val id: String) : ListIntent()
}