package com.vnteam.architecturetemplates.shared

import com.sun.speech.freetts.Voice
import com.sun.speech.freetts.VoiceManager

private const val FREETTS_VOICES_PROPERTY = "freetts.voices"
private const val FREETTS_VOICES_VALUE = "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory"
private const val VOICE_NAME = "kevin16"
private const val VOICE_NOT_FOUND_MESSAGE = "Voice not found"

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TextToSpeechHelper {
    private var voice: Voice? = null

    init {
        System.setProperty(FREETTS_VOICES_PROPERTY, FREETTS_VOICES_VALUE)
        val voiceManager = VoiceManager.getInstance()
        voiceManager.voices.forEach {
            println(it.name)
        }
        voice =
            voiceManager.getVoice(VOICE_NAME).apply {
                if (this != null) {
                    allocate()
                } else {
                    println(VOICE_NOT_FOUND_MESSAGE)
                }
            }
    }

    actual fun speak(text: String) {
        voice?.speak(text)
    }

    actual fun stop() = Unit

    fun shutdown() {
        voice?.deallocate()
    }
}
