package com.vnteam.architecturetemplates.data.network

import com.vnteam.architecturetemplates.data.network.responses.DemoObjectResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("repos/octocat/Spoon-Knife/forks")
    suspend fun getDemoObjects(): Response<List<DemoObjectResponse>>
}