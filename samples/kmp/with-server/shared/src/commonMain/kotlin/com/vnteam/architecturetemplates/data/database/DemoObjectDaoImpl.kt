package com.vnteam.architecturetemplates.data.database

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOneOrNull
import com.vnteam.architecturetemplates.DemoObjectWithOwner
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DemoObjectDaoImpl(private val sharedDatabase: SharedDatabase): DemoObjectDao {
    override suspend fun clearDemoObjects() {
        sharedDatabase { database ->
            database.appDatabaseQueries.clearDemoObjects()
        }
    }

    override suspend fun insertDemoObjectWithOwners(demoObjects: List<DemoObjectWithOwner>) {
        sharedDatabase { database ->
            database.appDatabaseQueries.transaction {
                demoObjects.forEach { demoObject ->
                    database.appDatabaseQueries.insertDemoObjectWithOwner(
                        demoObjectId = demoObject.demoObjectId,
                        name = demoObject.name,
                        ownerId = demoObject.ownerId,
                        login = demoObject.login,
                        avatarUrl = demoObject.avatarUrl,
                        htmlUrl = demoObject.htmlUrl,
                        description = demoObject.description,
                        url = demoObject.url
                    )
                }
            }
        }
    }

    override suspend fun getDemoObjectWithOwners(): Flow<List<DemoObjectWithOwner>> = callbackFlow {
        sharedDatabase { database ->
            trySend(database.appDatabaseQueries.getDemoObjectWithOwners().awaitAsList()).isSuccess
        }
        awaitClose { }
    }

    override suspend fun getDemoObjectById(id: String): Flow<DemoObjectWithOwner?> = callbackFlow {
        sharedDatabase { database ->
            trySend(database.appDatabaseQueries.getDemoObjectWithOwnerById(id).awaitAsOneOrNull()).isSuccess
        }
        awaitClose { }
    }

    override suspend fun deleteDemoObjectById(id: String) {
        sharedDatabase { database ->
            database.appDatabaseQueries.deleteDemoObjectWithOwnerById(id)
        }
    }
}