package com.vnteam.architecturetemplates.shared

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class TextToSpeechHelper {
    fun speak(text: String)
    fun stop()
}