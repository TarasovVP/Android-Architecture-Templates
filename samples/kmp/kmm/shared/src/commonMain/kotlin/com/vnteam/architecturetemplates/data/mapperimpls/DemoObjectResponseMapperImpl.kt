package com.vnteam.architecturetemplates.data.mapperimpls

import com.vnteam.architecturetemplates.data.network.responses.DemoObjectResponse
import com.vnteam.architecturetemplates.data.network.responses.OwnerResponse
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerResponseMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.models.Owner

class DemoObjectResponseMapperImpl(private val ownerResponseMapper: OwnerResponseMapper) :
    DemoObjectResponseMapper {

    override fun mapToImplModel(from: DemoObject): DemoObjectResponse {
        return DemoObjectResponse(
            id = from.id,
            name = from.name,
            fullName = from.fullName,
            owner = ownerResponseMapper.mapToImplModel(from.owner ?: Owner()),
            htmlUrl = from.htmlUrl,
            description = from.description
        )
    }

    override fun mapFromImplModel(to: DemoObjectResponse): DemoObject {
        return DemoObject(
            id = to.id,
            name = to.name,
            fullName = to.fullName,
            owner = ownerResponseMapper.mapFromImplModel(to.owner ?: OwnerResponse()),
            htmlUrl = to.htmlUrl,
            description = to.description
        )
    }

    override fun mapToImplModelList(fromList: List<DemoObject>): List<DemoObjectResponse> {
        return fromList.map { mapToImplModel(it) }
    }

    override fun mapFromImplModelList(toList: List<DemoObjectResponse>): List<DemoObject> {
        return toList.map { mapFromImplModel(it) }
    }
}