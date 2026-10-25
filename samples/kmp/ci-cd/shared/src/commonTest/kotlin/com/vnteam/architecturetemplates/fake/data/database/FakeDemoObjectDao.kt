package com.vnteam.architecturetemplates.fake.data.database

import com.vnteam.architecturetemplates.DemoObjectWithOwner
import com.vnteam.architecturetemplates.data.database.DemoObjectDao
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FakeDemoObjectDao : DemoObjectDao {
    val demoObjects = mutableListOf<DemoObjectWithOwner>()

    override suspend fun clearDemoObjects() {
        demoObjects.clear()
    }

    override suspend fun insertDemoObjectWithOwners(demoObjects: List<DemoObjectWithOwner>) {
        this.demoObjects.addAll(demoObjects)
    }

    override fun getDemoObjectWithOwners(): Flow<List<DemoObjectWithOwner>> =
        callbackFlow {
            trySend(demoObjects).isSuccess
            awaitClose { }
        }

    override fun getDemoObjectById(id: String): Flow<DemoObjectWithOwner?> =
        callbackFlow {
            trySend(demoObjects.find { it.demoObjectId == id }).isSuccess
            awaitClose { }
        }

    override suspend fun deleteDemoObjectById(id: String) {
        demoObjects.removeAll { it.demoObjectId == id }
    }
}
