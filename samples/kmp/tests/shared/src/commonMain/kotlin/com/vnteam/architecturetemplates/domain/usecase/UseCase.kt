package com.vnteam.architecturetemplates.domain.usecase

interface UseCase<in Params, out Result> {
    suspend fun execute(params: Params): Result
}

suspend fun <T : Any> UseCase<Nothing?, T>.execute() = execute(null)