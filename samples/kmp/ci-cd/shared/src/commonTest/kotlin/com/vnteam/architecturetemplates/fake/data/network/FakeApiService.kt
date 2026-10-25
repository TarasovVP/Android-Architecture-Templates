package com.vnteam.architecturetemplates.fake.data.network

import com.vnteam.architecturetemplates.data.network.ApiService
import com.vnteam.architecturetemplates.data.network.NetworkResult
import com.vnteam.architecturetemplates.data.network.responses.DemoObjectResponse
import com.vnteam.architecturetemplates.fake.domain.models.fakeException

class FakeApiService : ApiService {
    var demoObjectsResponse: List<DemoObjectResponse>? = null
    var isSuccessful = true

    override suspend fun insertDemoObjectsToApi(demoObjects: List<DemoObjectResponse>): NetworkResult<Unit> {
        demoObjectsResponse = demoObjects
        return if (isSuccessful) {
            NetworkResult.Success(Unit)
        } else {
            NetworkResult.Failure(fakeException.message)
        }
    }

    override suspend fun getDemoObjectsFromApi(): NetworkResult<List<DemoObjectResponse>> {
        return if (isSuccessful) {
            NetworkResult.Success(demoObjectsResponse)
        } else {
            NetworkResult.Failure(fakeException.message)
        }
    }

    override suspend fun getDemoObjectById(id: String): NetworkResult<DemoObjectResponse> {
        return if (isSuccessful) {
            NetworkResult.Success(demoObjectsResponse?.firstOrNull { it.demoObjectId == id })
        } else {
            NetworkResult.Failure(fakeException.message)
        }
    }

    override suspend fun deleteDemoObjectById(id: String): NetworkResult<Unit> {
        return if (isSuccessful) {
            demoObjectsResponse =
                demoObjectsResponse?.toMutableList()?.apply {
                    removeAll { it.demoObjectId == id }
                }
            NetworkResult.Success(Unit)
        } else {
            NetworkResult.Failure(fakeException.message)
        }
    }
}
