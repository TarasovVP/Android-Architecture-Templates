package com.vnteam.architecturetemplates.utils

import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.free
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.Foundation.NSError
import platform.Foundation.NSHTTPURLResponse
import platform.Foundation.NSURL
import platform.Foundation.NSURLConnection
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURLResponse
import platform.Foundation.sendSynchronousRequest

private const val URL = "https://www.google.com"

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun isNetworkAvailable(): Boolean {
    val url = NSURL.URLWithString(URL)
    val request = url?.let { NSURLRequest.requestWithURL(it) }

    val responsePtr = nativeHeap.alloc<ObjCObjectVar<NSURLResponse?>>().ptr
    val errorPtr = nativeHeap.alloc<ObjCObjectVar<NSError?>>().ptr

    val data = request?.let { NSURLConnection.sendSynchronousRequest(it, responsePtr, errorPtr) }
    val response = responsePtr.pointed.value
    val error = errorPtr.pointed.value

    nativeHeap.free(responsePtr)
    nativeHeap.free(errorPtr)

    return when {
        data != null && response is NSHTTPURLResponse -> true
        error != null -> false
        else -> false
    }
}
