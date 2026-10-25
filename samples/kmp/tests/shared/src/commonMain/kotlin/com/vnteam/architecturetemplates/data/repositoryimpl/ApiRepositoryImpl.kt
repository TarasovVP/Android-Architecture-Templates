package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.data.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.data.network.ApiService
import com.vnteam.architecturetemplates.data.network.NetworkResult
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository

class ApiRepositoryImpl(
    private val apiService: ApiService,
    private val demoObjectResponseMapper: DemoObjectResponseMapper
) : ApiRepository {

    override suspend fun getDemoObjectsFromApi(): List<DemoObject> {
        when (val response = apiService.getDemoObjectsFromApi()) {
            is NetworkResult.Success -> {
                return response.data?.map {
                    demoObjectResponseMapper.mapFromImplModel(
                        it
                    )
                }.orEmpty()
            }

            is NetworkResult.Failure -> {
                println(response.errorMessage)
                throw Exception(response.errorMessage)
            }
        }
    }

    override suspend fun insertDemoObjectsToApi(demoObjects: List<DemoObject>?) {
        val result = apiService.insertDemoObjectsToApi(
            demoObjectResponseMapper.mapToImplModelList(demoObjects.orEmpty())
        )
        if (result is NetworkResult.Failure) {
            println(result.errorMessage)
            throw Exception(result.errorMessage)
        }
    }

    override suspend fun getDemoObjectById(demoObjectId: String?): DemoObject? {
        when (val response = apiService.getDemoObjectById(demoObjectId.orEmpty())) {
            is NetworkResult.Success -> {
                val demoObject =
                    response.data?.let { demoObjectResponseMapper.mapFromImplModel(it) }
                return demoObject
            }

            is NetworkResult.Failure -> {
                println(response.errorMessage)
                throw Exception(response.errorMessage)
            }
        }
    }

    override suspend fun deleteDemoObjectById(demoObjectId: String) {
        val result = apiService.deleteDemoObjectById(demoObjectId)
        if (result is NetworkResult.Failure) {
            println(result.errorMessage)
            throw Exception(result.errorMessage)
        }
    }
}