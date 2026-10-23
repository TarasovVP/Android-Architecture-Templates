package com.vnteam.architecturetemplates.data.network

import com.vnteam.architecturetemplates.data.network.responses.ForkResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("repos/octocat/Spoon-Knife/forks")
    suspend fun getForks(): Response<List<ForkResponse>>
}