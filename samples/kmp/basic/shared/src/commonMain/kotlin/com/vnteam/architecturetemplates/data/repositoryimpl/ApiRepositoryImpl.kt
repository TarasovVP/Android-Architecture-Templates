package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.data.network.ApiService
import com.vnteam.architecturetemplates.data.network.NetworkResult
import com.vnteam.architecturetemplates.data.network.handleResponse
import com.vnteam.architecturetemplates.data.network.responses.DemoObjectResponse
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApiRepositoryImpl(private val apiService: ApiService, private val demoObjectResponseMapper: DemoObjectResponseMapper) :
    ApiRepository {

    override suspend fun getDemoObjectsFromApi(): Flow<List<DemoObject>> {
        when (val response = apiService.getDemoObjects().handleResponse<List<DemoObjectResponse>>()) {
            is NetworkResult.Success -> {
                val demoObjects = response.data.orEmpty()
                return flowOf(demoObjects.map { demoObjectResponseMapper.mapFromImplModel(it) })
            }
            is NetworkResult.Failure -> {
                println(response.errorMessage)
                throw Exception(response.errorMessage)
            }
        }
    }
}