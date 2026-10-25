package com.vnteam.architecturetemplates.presentation

import platform.AVFAudio.AVSpeechBoundary
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechSynthesizerDelegateProtocol
import platform.AVFAudio.AVSpeechUtterance
import platform.NaturalLanguage.NLLanguageRecognizer
import platform.darwin.NSObject

private const val DEFAULT_LANGUAGE = "en-US"

class TextToSpeechHelper : NSObject(), AVSpeechSynthesizerDelegateProtocol {
    private var synthesizer: AVSpeechSynthesizer = AVSpeechSynthesizer()
    private var isSpeaking = false
    private var isPaused = false

    init {
        synthesizer.delegate = this
    }

    override fun speechSynthesizer(
        synthesizer: AVSpeechSynthesizer,
        @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
        didFinishSpeechUtterance: AVSpeechUtterance,
    ) {
        isSpeaking = false
    }

    fun speak(text: String) {
        if (!isSpeaking && isPaused) {
            continueSpeaking()
        }

        val utterance = AVSpeechUtterance.speechUtteranceWithString(text)
        utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage(DEFAULT_LANGUAGE)
        if (utterance.voice == null) {
            return
        }

        val recognizer = NLLanguageRecognizer()
        recognizer.processString(text)
        val language = recognizer.dominantLanguage
        language ?: run {
            utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage(language)
        }

        isPaused = false
        isSpeaking = true
        synthesizer.speakUtterance(utterance)
    }

    fun stop() {
        synthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
        isSpeaking = false
        isPaused = false
    }

    private fun continueSpeaking() {
        synthesizer.continueSpeaking()
        isPaused = false
        isSpeaking = true
    }
}
