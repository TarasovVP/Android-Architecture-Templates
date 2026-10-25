package com.vnteam.architecturetemplates.data.mapperimpls

import com.vnteam.architecturetemplates.data.database.entities.DemoObjectDB
import com.vnteam.architecturetemplates.data.database.entities.OwnerDB
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectDBMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerDBMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.models.Owner

class DemoObjectDBMapperImpl(private val ownerDBMapper: OwnerDBMapper) : DemoObjectDBMapper {

    override fun mapToImplModel(from: DemoObject): DemoObjectDB {
        return DemoObjectDB(id = from.id,
            name = from.name,
            fullName = from.fullName,
            owner = ownerDBMapper.mapToImplModel(from.owner ?: Owner()),
            htmlUrl = from.htmlUrl,
            description = from.description)
    }

    override fun mapFromImplModel(to: DemoObjectDB): DemoObject {
        return DemoObject(id = to.id,
        name = to.name,
        fullName = to.fullName,
        owner = ownerDBMapper.mapFromImplModel(to.owner ?: OwnerDB()),
        htmlUrl = to.htmlUrl,
        description = to.description)
    }

    override fun mapToImplModelList(fromList: List<DemoObject>): List<DemoObjectDB> {
        return fromList.map { mapToImplModel(it) }
    }

    override fun mapFromImplModelList(toList: List<DemoObjectDB>): List<DemoObject> {
        return toList.map { mapFromImplModel(it) }
    }
}