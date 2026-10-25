package com.vnteam.architecturetemplates.data.network

import com.vnteam.architecturetemplates.domain.models.BaseException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import secrets.Secrets

const val CONNECTION_EXCEPTION = "Connection Exception. Check if the server is running."
const val UNKNOWN_ERROR = "Unknown error"
const val ERROR_STATUS_400 = 400
const val ERROR_STATUS_405 = 405
const val SUCCESS_STATUS_200 = 200
const val SUCCESS_STATUS_299 = 299
const val LOCAL_PORT = ":8080/"

suspend inline fun <reified T> HttpResponse?.handleResponse(): NetworkResult<T> {
    return when {
        this == null -> NetworkResult.Failure(UNKNOWN_ERROR)
        status.value in ERROR_STATUS_400..ERROR_STATUS_405 -> {
            println("Error ${bodyAsText()}")
            NetworkResult.Failure(CONNECTION_EXCEPTION)
        }
        status.value !in SUCCESS_STATUS_200..SUCCESS_STATUS_299 -> {
            val error = bodyAsText()
            NetworkResult.Failure(error)
        }

        else -> {
            try {
                val result = body<T>()
                NetworkResult.Success(result)
            } catch (e: BaseException) {
                NetworkResult.Failure(e.message ?: UNKNOWN_ERROR)
            }
        }
    }
}

suspend inline fun <reified T> HttpClient.safeRequest(block: HttpClient.() -> HttpResponse): NetworkResult<T> =
    try {
        val response = block()
        response.handleResponse<T>()
    } catch (e: BaseException) {
        val errorMessage = if (Secrets.CLOUD_URL.contains(LOCAL_PORT)) CONNECTION_EXCEPTION else e.message
        NetworkResult.Failure(errorMessage)
    }
