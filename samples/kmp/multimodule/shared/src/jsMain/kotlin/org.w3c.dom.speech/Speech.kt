package org.w3c.dom.speech

import org.w3c.dom.events.EventTarget

external class SpeechSynthesisUtterance(text: String) : EventTarget {
    var text: String
    var lang: String
    var volume: Float
    var rate: Float
    var pitch: Float
}

external class SpeechSynthesis : EventTarget {
    fun speak(utterance: SpeechSynthesisUtterance)
    fun cancel()
    val pending: Boolean
    val speaking: Boolean
    val paused: Boolean
}

external val speechSynthesis: SpeechSynthesis