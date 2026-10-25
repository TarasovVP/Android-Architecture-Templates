package com.vnteam.architecturetemplates.data.mapperimpls

import com.vnteam.architecturetemplates.DemoObjectWithOwner
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectDBMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.models.Owner

class DemoObjectDBMapperImpl : DemoObjectDBMapper {

    override fun mapToImplModel(from: DemoObject): DemoObjectWithOwner {
        return DemoObjectWithOwner(id = 0,
            demoObjectId = from.demoObjectId.orEmpty(),
            name = from.name,
            ownerId = from.owner?.ownerId,
            login = from.owner?.login,
            avatarUrl = from.owner?.avatarUrl,
            htmlUrl = from.htmlUrl,
            description = from.description,
            url = from.owner?.url)
    }

    override fun mapFromImplModel(to: DemoObjectWithOwner): DemoObject {
        return DemoObject(demoObjectId = to.demoObjectId,
        name = to.name,
        owner = Owner(ownerId = to.ownerId,
            login = to.login,
            avatarUrl = to.avatarUrl,
            url = to.url),
        htmlUrl = to.htmlUrl,
        description = to.description)
    }

    override fun mapToImplModelList(fromList: List<DemoObject>): List<DemoObjectWithOwner> {
        return fromList.map { mapToImplModel(it) }
    }

    override fun mapFromImplModelList(toList: List<DemoObjectWithOwner>): List<DemoObject> {
        return toList.map { mapFromImplModel(it) }
    }
}