package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.data.database.ForkDao
import com.vnteam.architecturetemplates.domain.mappers.ForkDBMapper
import com.vnteam.architecturetemplates.domain.models.Fork
import com.vnteam.architecturetemplates.domain.repositories.DBRepository

class DBRepositoryImpl(private val forkDao: ForkDao, private val forkDBMapper: ForkDBMapper):
    DBRepository {

    override fun insertForksToDB(forks: List<Fork>) {
        forkDao.insertForks(forkDBMapper.mapToImplModelList(forks))
    }

    override fun getForksFromDB(): List<Fork> {
        return forkDBMapper.mapFromImplModelList(forkDao.getForks())
    }

    override fun getForkById(forkId: Long): Fork {
        return forkDBMapper.mapFromImplModel(forkDao.getForkById(forkId))
    }
}