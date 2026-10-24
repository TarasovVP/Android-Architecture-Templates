package com.vnteam.architecturetemplates.data.database

import com.vnteam.architecturetemplates.DemoObjectWithOwner
import kotlinx.coroutines.flow.Flow

interface DemoObjectDao {
    suspend fun clearDemoObjects()

    suspend fun insertDemoObjectWithOwners(demoObjects: List<DemoObjectWithOwner>)

    fun getDemoObjectWithOwners(): Flow<List<DemoObjectWithOwner>>

    fun getDemoObjectById(id: String): Flow<DemoObjectWithOwner?>

    suspend fun deleteDemoObjectById(id: String)
}
