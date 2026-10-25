package com.vnteam.architecturetemplates.presentation

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class TextToSpeechHelper {
    fun speak(text: String)
    fun stop()
}