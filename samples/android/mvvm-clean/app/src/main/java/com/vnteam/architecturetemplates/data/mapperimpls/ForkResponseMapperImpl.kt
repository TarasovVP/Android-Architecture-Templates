package com.vnteam.architecturetemplates.data.mapperimpls

import com.vnteam.architecturetemplates.data.network.responses.ForkResponse
import com.vnteam.architecturetemplates.data.network.responses.OwnerResponse
import com.vnteam.architecturetemplates.domain.mappers.ForkResponseMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerResponseMapper
import com.vnteam.architecturetemplates.domain.models.Fork
import com.vnteam.architecturetemplates.domain.models.Owner

class ForkResponseMapperImpl(private val ownerResponseMapper: OwnerResponseMapper) :
    ForkResponseMapper {

    override fun mapToImplModel(from: Fork): ForkResponse {
        return ForkResponse(
            id = from.id,
            name = from.name,
            fullName = from.fullName,
            owner = ownerResponseMapper.mapToImplModel(from.owner ?: Owner()),
            htmlUrl = from.htmlUrl,
            description = from.description
        )
    }

    override fun mapFromImplModel(to: ForkResponse): Fork {
        return Fork(
            id = to.id,
            name = to.name,
            fullName = to.fullName,
            owner = ownerResponseMapper.mapFromImplModel(to.owner ?: OwnerResponse()),
            htmlUrl = to.htmlUrl,
            description = to.description
        )
    }

    override fun mapToImplModelList(fromList: List<Fork>): List<ForkResponse> {
        return fromList.map { mapToImplModel(it) }
    }

    override fun mapFromImplModelList(toList: List<ForkResponse>): List<Fork> {
        return toList.map { mapFromImplModel(it) }
    }
}