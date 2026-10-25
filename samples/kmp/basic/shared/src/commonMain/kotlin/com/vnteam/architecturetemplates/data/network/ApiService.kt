package com.vnteam.architecturetemplates.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse


class ApiService(
    private val baseUrl: String,
    private val httpClient: HttpClient,
) {

    suspend fun getDemoObjects(): HttpResponse {

        val httpResponse = try {
            httpClient.get("${baseUrl}repos/octocat/Spoon-Knife/forks") {
            }
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
        return httpResponse
    }
}