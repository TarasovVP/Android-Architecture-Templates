package com.vnteam.architecturetemplates.demoobjectservice

import com.vnteam.architecturetemplates.ServerDatabaseQueries
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.mapperimpls.toDemoObject

class DemoObjectServiceImpl(private val serverDatabaseQueries: ServerDatabaseQueries) :
    DemoObjectService {
    override suspend fun insertDemoObjects(demoObjects: List<DemoObject>) {
        demoObjects.forEach {
            serverDatabaseQueries.insertDemoObjectWithOwner(
                demoObjectId = it.demoObjectId.orEmpty(),
                name = it.name,
                htmlUrl = it.htmlUrl,
                description = it.description,
                login = it.owner?.login,
                ownerId = it.owner?.ownerId,
                avatarUrl = it.owner?.avatarUrl,
                url = it.owner?.url,
            )
        }
    }

    override suspend fun getDemoObjects(): List<DemoObject> {
        return serverDatabaseQueries.getDemoObjectWithOwners().executeAsList()
            .map { it.toDemoObject() }
    }

    override suspend fun getDemoObjectById(demoObjectId: String): DemoObject? =
        serverDatabaseQueries.getDemoObjectWithOwnerById(demoObjectId).executeAsOneOrNull()?.toDemoObject()

    override suspend fun deleteDemoObjectById(demoObjectId: String) {
        serverDatabaseQueries.deleteDemoObjectWithOwnerById(demoObjectId)
    }
}
