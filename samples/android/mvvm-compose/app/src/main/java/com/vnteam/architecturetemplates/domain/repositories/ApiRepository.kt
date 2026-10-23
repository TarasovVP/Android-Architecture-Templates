package com.vnteam.architecturetemplates.domain.repositories

import com.vnteam.architecturetemplates.domain.models.DemoObject

interface ApiRepository {

    suspend fun getDemoObjectsFromApi(): List<DemoObject>?
}