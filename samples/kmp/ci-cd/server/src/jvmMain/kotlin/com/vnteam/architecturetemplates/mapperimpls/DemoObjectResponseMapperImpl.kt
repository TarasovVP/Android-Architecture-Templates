package com.vnteam.architecturetemplates.mapperimpls

import com.vnteam.architecturetemplates.domain.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerResponseMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.models.Owner
import com.vnteam.architecturetemplates.domain.responses.DemoObjectResponse
import com.vnteam.architecturetemplates.domain.responses.OwnerResponse

class DemoObjectResponseMapperImpl(
    private val ownerResponseMapper: OwnerResponseMapper,
) : DemoObjectResponseMapper {
    override fun mapToImplModel(from: DemoObject): DemoObjectResponse =
        DemoObjectResponse(
            demoObjectId = from.demoObjectId,
            name = from.name,
            owner = ownerResponseMapper.mapToImplModel(from.owner ?: Owner()),
            htmlUrl = from.htmlUrl,
            description = from.description,
        )

    override fun mapFromImplModel(to: DemoObjectResponse): DemoObject =
        DemoObject(
            demoObjectId = to.demoObjectId,
            name = to.name,
            owner = ownerResponseMapper.mapFromImplModel(to.owner ?: OwnerResponse()),
            htmlUrl = to.htmlUrl,
            description = to.description,
        )

    override fun mapToImplModelList(fromList: List<DemoObject>): List<DemoObjectResponse> = fromList.map { mapToImplModel(it) }

    override fun mapFromImplModelList(toList: List<DemoObjectResponse>): List<DemoObject> = toList.map { mapFromImplModel(it) }
}
