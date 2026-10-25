package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.data.database.DemoObjectDao
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectDBMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DBRepositoryImpl(private val demoObjectDao: DemoObjectDao, private val demoObjectDBMapper: DemoObjectDBMapper):
    DBRepository {

    override fun insertDemoObjectsToDB(demoObjects: List<DemoObject>) {
        demoObjectDao.insertDemoObjectWithOwners(demoObjectDBMapper.mapToImplModelList(demoObjects))
    }

    override fun getDemoObjectsFromDB(): Flow<List<DemoObject>> {
        return flowOf( demoObjectDBMapper.mapFromImplModelList(demoObjectDao.getDemoObjects()) )
    }

    override fun getDemoObjectById(demoObjectId: Long): Flow<DemoObject?> {
        return flowOf( demoObjectDao.getDemoObjectById(demoObjectId)?.let { demoObjectDBMapper.mapFromImplModel(it) } )
    }
}