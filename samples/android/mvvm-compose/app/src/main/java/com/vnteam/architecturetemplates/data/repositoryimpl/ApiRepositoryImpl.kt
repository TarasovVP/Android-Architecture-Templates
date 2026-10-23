package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.data.network.ApiService
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository

class ApiRepositoryImpl(private val apiService: ApiService, private val demoObjectResponseMapper: DemoObjectResponseMapper) :
    ApiRepository {

    override suspend fun getDemoObjectsFromApi(): List<DemoObject> {
        return demoObjectResponseMapper.mapFromImplModelList(apiService.getDemoObjects().body().orEmpty())
    }
}