package com.vnteam.architecturetemplates

import org.koin.test.KoinTest
import kotlin.random.Random

inline fun <reified T: Any, reified R : T> KoinTest.injectAs(): Lazy<R> = lazy {
    val obj = getKoin().get<T>()
    obj as R
}

fun randomUuidLikeString(): String {
    val bytes = ByteArray(16)
    Random.nextBytes(bytes)
    return bytes.joinToString("") {
        it.toString(16).padStart(2, '0')
    }
}