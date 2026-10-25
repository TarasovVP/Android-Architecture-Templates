package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.data.database.DemoObjectDao
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectDBMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DBRepositoryImpl(
    private val demoObjectDao: DemoObjectDao,
    private val demoObjectDBMapper: DemoObjectDBMapper
) : DBRepository {

    override suspend fun clearDemoObjects() {
        demoObjectDao.clearDemoObjects()

    }

    override suspend fun insertDemoObjectsToDB(demoObjects: List<DemoObject>) {
        val mappedDemoObjects = demoObjectDBMapper.mapToImplModelList(demoObjects)
        demoObjectDao.insertDemoObjectWithOwners(mappedDemoObjects)
    }

    override fun getDemoObjectsFromDB(): Flow<List<DemoObject>> =
        demoObjectDao.getDemoObjectWithOwners().map { demoObjectWithOwners ->
            demoObjectDBMapper.mapFromImplModelList(demoObjectWithOwners)
        }


    override fun getDemoObjectById(demoObjectId: String): Flow<DemoObject?> =
        demoObjectDao.getDemoObjectById(demoObjectId).map { demoObjectWithOwner ->
            demoObjectWithOwner?.let { demoObjectDBMapper.mapFromImplModel(it) }
        }

    override suspend fun deleteDemoObjectById(demoObjectId: String) {
        demoObjectDao.deleteDemoObjectById(demoObjectId)
    }
}