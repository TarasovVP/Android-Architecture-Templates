package com.vnteam.architecturetemplates.presentation

import org.w3c.dom.speech.SpeechSynthesisUtterance
import org.w3c.dom.speech.speechSynthesis

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TextToSpeechHelper {
    private var utterance: SpeechSynthesisUtterance? = null

    actual fun speak(text: String) {
        utterance = SpeechSynthesisUtterance(text).also {
            speechSynthesis.speak(it)
        }
    }

    actual fun stop() {
        speechSynthesis.cancel()
    }
}