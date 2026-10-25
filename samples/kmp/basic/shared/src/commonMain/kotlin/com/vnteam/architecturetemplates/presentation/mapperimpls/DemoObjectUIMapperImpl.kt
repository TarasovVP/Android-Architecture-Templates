package com.vnteam.architecturetemplates.presentation.mapperimpls

import com.vnteam.architecturetemplates.presentation.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.presentation.mappers.OwnerUIMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.models.Owner
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import com.vnteam.architecturetemplates.presentation.uimodels.OwnerUI

class DemoObjectUIMapperImpl(private val owner: OwnerUIMapper) : DemoObjectUIMapper {

    override fun mapToImplModel(from: DemoObject): DemoObjectUI {
        return DemoObjectUI(
            id = from.id,
            name = from.name,
            fullName = from.fullName.orEmpty(),
            owner = owner.mapToImplModel(from.owner ?: Owner()),
            htmlUrl = from.htmlUrl,
            description = from.description
        )
    }

    override fun mapFromImplModel(to: DemoObjectUI): DemoObject {
        return DemoObject(
            id = to.id,
            name = to.name,
            fullName = to.fullName,
            owner = owner.mapFromImplModel(to.owner ?: OwnerUI()),
            htmlUrl = to.htmlUrl,
            description = to.description
        )
    }

    override fun mapToImplModelList(fromList: List<DemoObject>): List<DemoObjectUI> {
        return fromList.map { mapToImplModel(it) }
    }

    override fun mapFromImplModelList(toList: List<DemoObjectUI>): List<DemoObject> {
        return toList.map { mapFromImplModel(it) }
    }
}