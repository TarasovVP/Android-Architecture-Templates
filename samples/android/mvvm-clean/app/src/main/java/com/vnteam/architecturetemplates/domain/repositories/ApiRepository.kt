package com.vnteam.architecturetemplates.domain.repositories

import com.vnteam.architecturetemplates.domain.models.Fork

interface ApiRepository {

    suspend fun getForksFromApi(): List<Fork>?
}