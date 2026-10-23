package com.vnteam.architecturetemplates.domain.repositories

import com.vnteam.architecturetemplates.domain.models.Fork

interface DBRepository {

    fun insertForksToDB(forks: List<Fork>)

    fun getForksFromDB(): List<Fork>

    fun getForkById(forkId: Long): Fork
}