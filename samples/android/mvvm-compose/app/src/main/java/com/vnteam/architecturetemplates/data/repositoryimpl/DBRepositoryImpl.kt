package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.data.database.DemoObjectDao
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectDBMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.DBRepository

class DBRepositoryImpl(private val demoObjectDao: DemoObjectDao, private val demoObjectDBMapper: DemoObjectDBMapper):
    DBRepository {

    override fun insertDemoObjectsToDB(demoObjects: List<DemoObject>) {
        demoObjectDao.insertDemoObjects(demoObjectDBMapper.mapToImplModelList(demoObjects))
    }

    override fun getDemoObjectsFromDB(): List<DemoObject> {
        return demoObjectDBMapper.mapFromImplModelList(demoObjectDao.getDemoObjects())
    }

    override fun getDemoObjectById(demoObjectId: Long): DemoObject {
        return demoObjectDBMapper.mapFromImplModel(demoObjectDao.getDemoObjectById(demoObjectId))
    }
}