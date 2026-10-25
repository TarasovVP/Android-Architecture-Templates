package com.vnteam.architecturetemplates.data.network

import com.vnteam.architecturetemplates.data.network.responses.DemoObjectResponse

interface ApiService {
    suspend fun insertDemoObjectsToApi(demoObjects: List<DemoObjectResponse>): NetworkResult<Unit>

    suspend fun getDemoObjectsFromApi(): NetworkResult<List<DemoObjectResponse>>

    suspend fun getDemoObjectById(id: String): NetworkResult<DemoObjectResponse>

    suspend fun deleteDemoObjectById(id: String): NetworkResult<Unit>
}