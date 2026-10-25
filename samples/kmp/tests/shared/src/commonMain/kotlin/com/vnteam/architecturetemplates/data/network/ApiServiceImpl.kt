package com.vnteam.architecturetemplates.data.network

import com.vnteam.architecturetemplates.data.DEMO_OBJECTS_API
import com.vnteam.architecturetemplates.data.network.responses.DemoObjectResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import secrets.Properties

class ApiServiceImpl(
    private val httpClient: HttpClient,
) : ApiService {

    override suspend fun insertDemoObjectsToApi(demoObjects: List<DemoObjectResponse>): NetworkResult<Unit> {
        return httpClient.safeRequest<Unit> {
            httpClient.post("${Properties.CLOUD_URL}$DEMO_OBJECTS_API") {
                contentType(ContentType.Application.Json)
                setBody(demoObjects)
            }
        }
    }

    override suspend fun getDemoObjectsFromApi(): NetworkResult<List<DemoObjectResponse>> {
        return httpClient.safeRequest<List<DemoObjectResponse>> {
            get("${Properties.CLOUD_URL}$DEMO_OBJECTS_API") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun getDemoObjectById(id: String): NetworkResult<DemoObjectResponse> {
        return httpClient.safeRequest<DemoObjectResponse> {
            httpClient.get("${Properties.CLOUD_URL}$DEMO_OBJECTS_API/$id") {
                contentType(ContentType.Application.Json)
            }
        }
    }

    override suspend fun deleteDemoObjectById(id: String): NetworkResult<Unit> {
        return httpClient.safeRequest<Unit> {
            httpClient.delete("${Properties.CLOUD_URL}$DEMO_OBJECTS_API/$id") {
                contentType(ContentType.Application.Json)
            }
        }
    }
}