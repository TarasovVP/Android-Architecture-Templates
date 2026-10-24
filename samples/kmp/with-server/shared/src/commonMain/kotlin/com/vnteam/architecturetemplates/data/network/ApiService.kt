package com.vnteam.architecturetemplates.data.network

import com.vnteam.architecturetemplates.domain.responses.DemoObjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiService(
    private val baseUrl: String,
    private val httpClient: HttpClient,
) {

    suspend fun insertDemoObjectsToApi(demoObjects: List<DemoObjectResponse>): NetworkResult<Unit> {
        return httpClient.safeRequest<Unit> {
            httpClient.post("${baseUrl}demoObjects") {
                contentType(ContentType.Application.Json)
                setBody(demoObjects)
            }
        }
    }

    suspend fun getDemoObjectsFromApi(): NetworkResult<List<DemoObjectResponse>> {
        return httpClient.safeRequest<List<DemoObjectResponse>> {
            get("${baseUrl}demoObjects") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun getDemoObjectById(id: String): NetworkResult<DemoObjectResponse> {
        return httpClient.safeRequest<DemoObjectResponse> {
            httpClient.get("${baseUrl}demoObjects/$id") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    suspend fun deleteDemoObjectById(id: String): NetworkResult<Unit> {
        return httpClient.safeRequest<Unit> {
            httpClient.delete("${baseUrl}demoObjects/$id") {
                contentType(ContentType.Application.Json)
            }
        }
    }
}