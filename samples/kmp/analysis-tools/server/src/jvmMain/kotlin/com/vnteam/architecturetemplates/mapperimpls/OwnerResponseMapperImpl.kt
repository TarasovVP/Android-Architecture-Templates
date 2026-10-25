package com.vnteam.architecturetemplates.mapperimpls

import com.vnteam.architecturetemplates.domain.mappers.OwnerResponseMapper
import com.vnteam.architecturetemplates.domain.models.Owner
import com.vnteam.architecturetemplates.domain.responses.OwnerResponse

class OwnerResponseMapperImpl : OwnerResponseMapper {
    override fun mapToImplModel(from: Owner): OwnerResponse {
        return OwnerResponse(
            login = from.login,
            ownerId = from.ownerId,
            avatarUrl = from.avatarUrl,
            url = from.url,
        )
    }

    override fun mapFromImplModel(to: OwnerResponse): Owner {
        return Owner(
            login = to.login,
            ownerId = to.ownerId,
            avatarUrl = to.avatarUrl,
            url = to.url,
        )
    }

    override fun mapToImplModelList(fromList: List<Owner>): List<OwnerResponse> {
        return fromList.map { mapToImplModel(it) }
    }

    override fun mapFromImplModelList(toList: List<OwnerResponse>): List<Owner> {
        return toList.map { mapFromImplModel(it) }
    }
}
