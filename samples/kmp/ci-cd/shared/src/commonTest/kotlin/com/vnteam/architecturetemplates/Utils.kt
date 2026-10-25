package com.vnteam.architecturetemplates

import org.koin.test.KoinTest
import kotlin.random.Random

inline fun <reified T : Any, reified R : T> KoinTest.injectAs(): Lazy<R> =
    lazy {
        val obj = getKoin().get<T>()
        obj as R
    }

private const val BYTE_ARRAY_SIZE = 16
private const val RADIX = 16
private const val PAD_START_LENGTH = 2

fun randomUuidLikeString(): String {
    val bytes = ByteArray(BYTE_ARRAY_SIZE)
    Random.nextBytes(bytes)
    return bytes.joinToString("") {
        it.toString(RADIX).padStart(PAD_START_LENGTH, '0')
    }
}
