package com.vnteam.architecturetemplates.fake.data.repositoryimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import kotlinx.coroutines.flow.flowOf

class FakeDBRepository : DBRepository {
    var isClearDemoObjectsCalled = false
    var isInsertDemoObjectsToDBCalled = false
    var isDeleteDemoObjectByIdCalled = false
    var demoObject: DemoObject? = null
    var demoObjects: List<DemoObject>? = null

    override suspend fun clearDemoObjects() {
        isClearDemoObjectsCalled = true
    }

    override suspend fun insertDemoObjectsToDB(demoObjects: List<DemoObject>) {
        isInsertDemoObjectsToDBCalled = true
    }

    override fun getDemoObjectsFromDB() = flowOf(demoObjects.orEmpty())

    override fun getDemoObjectById(demoObjectId: String) = flowOf(demoObject)

    override suspend fun deleteDemoObjectById(demoObjectId: String) {
        isDeleteDemoObjectByIdCalled = true
    }
}
