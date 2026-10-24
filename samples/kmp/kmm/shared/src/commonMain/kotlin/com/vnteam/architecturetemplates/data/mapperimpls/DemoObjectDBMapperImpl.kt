package com.vnteam.architecturetemplates.data.mapperimpls

import com.vnteam.architecturetemplates.DemoObjectWithOwner
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectDBMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.models.Owner

class DemoObjectDBMapperImpl : DemoObjectDBMapper {

    override fun mapToImplModel(from: DemoObject): DemoObjectWithOwner {
        return DemoObjectWithOwner(id = from.id ?: 0,
            name = from.name,
            fullName = from.fullName,
            ownerId = from.owner?.ownerId,
            login = from.owner?.login,
            avatarUrl = from.owner?.avatarUrl,
            htmlUrl = from.htmlUrl,
            description = from.description)
    }

    override fun mapFromImplModel(to: DemoObjectWithOwner): DemoObject {
        return DemoObject(id = to.id,
        name = to.name,
        fullName = to.fullName,
        owner = Owner(ownerId = to.ownerId,
            login = to.login,
            avatarUrl = to.avatarUrl),
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