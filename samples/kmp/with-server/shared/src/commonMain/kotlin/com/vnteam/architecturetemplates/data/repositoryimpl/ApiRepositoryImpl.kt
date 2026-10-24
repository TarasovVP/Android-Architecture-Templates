package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.data.network.ApiService
import com.vnteam.architecturetemplates.data.network.NetworkResult
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApiRepositoryImpl(private val apiService: ApiService, private val demoObjectResponseMapper: DemoObjectResponseMapper) :
    ApiRepository {

    override suspend fun getDemoObjectsFromApi(): Flow<List<DemoObject>> {
        when (val response = apiService.getDemoObjectsFromApi()) {
            is NetworkResult.Success -> {
                return flowOf( response.data?.map { demoObjectResponseMapper.mapFromImplModel(it) }.orEmpty() )
            }
            is NetworkResult.Failure -> {
                println(response.errorMessage)
                throw Exception(response.errorMessage)
            }
        }
    }

    override suspend fun insertDemoObjectsToApi(demoObjects: List<DemoObject>?): Flow<Unit> {
        when (val response = apiService.insertDemoObjectsToApi(demoObjectResponseMapper.mapToImplModelList(demoObjects.orEmpty()))) {
            is NetworkResult.Success -> {
                return flowOf( Unit )
            }
            is NetworkResult.Failure -> {
                println(response.errorMessage)
                throw Exception(response.errorMessage)
            }
        }
    }

    override suspend fun getDemoObjectById(demoObjectId: String?): Flow<DemoObject?> {
        when (val response = apiService.getDemoObjectById(demoObjectId.orEmpty())) {
            is NetworkResult.Success -> {
                return flowOf( response.data?.let { demoObjectResponseMapper.mapFromImplModel(it) } )
            }
            is NetworkResult.Failure -> {
                println(response.errorMessage)
                throw Exception(response.errorMessage)
            }
        }
    }

    override suspend fun deleteDemoObjectById(demoObjectId: String): Flow<Unit> {
        when (val response = apiService.deleteDemoObjectById(demoObjectId)) {
            is NetworkResult.Success -> {
                return flowOf( Unit )
            }
            is NetworkResult.Failure -> {
                println(response.errorMessage)
                throw Exception(response.errorMessage)
            }
        }
    }
}