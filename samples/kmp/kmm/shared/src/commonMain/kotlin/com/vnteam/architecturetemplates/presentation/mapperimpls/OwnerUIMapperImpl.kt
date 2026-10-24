package com.vnteam.architecturetemplates.presentation.mapperimpls

import presentation.mappers.OwnerUIMapper
import presentation.mappers.OwnerUIMapper
import com.vnteam.architecturetemplates.domain.models.Owner
import com.vnteam.architecturetemplates.presentation.uimodels.OwnerUI

class OwnerUIMapperImpl : OwnerUIMapper {

    override fun mapToImplModel(from: Owner): OwnerUI {
        return OwnerUI(login = from.login,
        ownerId = from.ownerId,
        avatarUrl = from.avatarUrl)
    }

    override fun mapFromImplModel(to: OwnerUI): Owner {
        return Owner(login = to.login,
            ownerId = to.ownerId,
            avatarUrl = to.avatarUrl)
    }

    override fun mapToImplModelList(fromList: List<Owner>): List<OwnerUI> {
        return fromList.map { mapToImplModel(it) }
    }

    override fun mapFromImplModelList(toList: List<OwnerUI>): List<Owner> {
        return toList.map { mapFromImplModel(it) }
    }
}