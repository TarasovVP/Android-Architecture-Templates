package com.vnteam.architecturetemplates.domain.repositories

import com.vnteam.architecturetemplates.domain.models.DemoObject
import kotlinx.coroutines.flow.Flow

interface ApiRepository {

    suspend fun getDemoObjectsFromApi(): Flow<List<DemoObject>?>
}