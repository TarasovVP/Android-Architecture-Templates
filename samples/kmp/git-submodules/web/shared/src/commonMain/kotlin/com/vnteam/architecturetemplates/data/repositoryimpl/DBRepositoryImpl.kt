package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.data.database.DemoObjectDao
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectDBMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class DBRepositoryImpl(private val demoObjectDao: DemoObjectDao, private val demoObjectDBMapper: DemoObjectDBMapper):
    DBRepository {

    override suspend fun clearDemoObjects() {
        demoObjectDao.clearDemoObjects()

    }

    override suspend fun insertDemoObjectsToDB(demoObjects: List<DemoObject>): Flow<Unit> = flow {
        demoObjectDao.insertDemoObjectWithOwners(demoObjectDBMapper.mapToImplModelList(demoObjects))
        emit(Unit)
    }

    override suspend fun getDemoObjectsFromDB(): Flow<List<DemoObject>> =
        demoObjectDao.getDemoObjectWithOwners().map { demoObjectWithOwners ->
            demoObjectDBMapper.mapFromImplModelList(demoObjectWithOwners)
        }


    override suspend fun getDemoObjectById(demoObjectId: String): Flow<DemoObject?> =
        demoObjectDao.getDemoObjectById(demoObjectId).map { demoObjectWithOwner ->
            demoObjectWithOwner?.let { demoObjectDBMapper.mapFromImplModel(it) }
        }

    override suspend fun deleteDemoObjectById(demoObjectId: String): Flow<Unit> = flow {
        demoObjectDao.deleteDemoObjectById(demoObjectId)
        emit(Unit)
    }
}