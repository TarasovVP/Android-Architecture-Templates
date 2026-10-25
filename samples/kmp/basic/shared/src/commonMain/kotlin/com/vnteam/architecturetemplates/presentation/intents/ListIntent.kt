package com.vnteam.architecturetemplates.presentation.intents

sealed class ListIntent {
     class ClearDemoObjects : ListIntent()
     class LoadDemoObjects : ListIntent()
}