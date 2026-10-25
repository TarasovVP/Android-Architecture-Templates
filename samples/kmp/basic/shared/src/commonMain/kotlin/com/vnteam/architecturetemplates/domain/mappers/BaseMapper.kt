package com.vnteam.architecturetemplates.domain.mappers

interface BaseMapper<From, To> {

    fun mapToImplModel(from: From): To

    fun mapFromImplModel(to: To): From

    fun mapToImplModelList(fromList: List<From>): List<To>

    fun mapFromImplModelList(toList: List<To>): List<From>
}