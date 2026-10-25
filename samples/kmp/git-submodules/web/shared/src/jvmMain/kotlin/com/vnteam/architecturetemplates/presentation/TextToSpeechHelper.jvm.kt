package com.vnteam.architecturetemplates.presentation


import com.sun.speech.freetts.Voice
import com.sun.speech.freetts.VoiceManager

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class TextToSpeechHelper {
    private var voice: Voice? = null

    init {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory")
        val voiceManager = VoiceManager.getInstance()
        voiceManager.voices.forEach {
            println(it.name)
        }
        voice = voiceManager.getVoice("kevin16").apply {
            if (this != null) {
                allocate()
            } else {
                println("Voice not found")
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