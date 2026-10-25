package com.vnteam.architecturetemplates.shared

import com.vnteam.architecturetemplates.presentation.TextToSpeechInstance

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TextToSpeechHelper {
    private var textToSpeechInstance = TextToSpeechInstance()

    actual fun speak(text: String) {
        textToSpeechInstance.speak(text)
    }

    actual fun stop() {
        textToSpeechInstance.stop()
    }
}