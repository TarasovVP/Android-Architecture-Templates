package com.vnteam.architecturetemplates.domain.usecase

import com.vnteam.architecturetemplates.domain.models.Fork

interface ForkUseCase {

    suspend fun getForksFromApi(): List<Fork>?

    fun insertForksToDB(forks: List<Fork>)

    fun getForksFromDB(): List<Fork>

    fun getForkById(forkId: Long): Fork
}