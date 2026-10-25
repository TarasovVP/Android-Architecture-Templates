package com.vnteam.architecturetemplates.domain.usecase

interface DataUseCase<in SetParams, out GetResult> {
    fun get(): GetResult
    suspend fun set(params: SetParams)
}