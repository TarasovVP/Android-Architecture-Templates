package com.vnteam.architecturetemplates.data.network

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T? = null) : NetworkResult<T>()
    data class Failure(val errorMessage: String? = null) : NetworkResult<Nothing>()
}
