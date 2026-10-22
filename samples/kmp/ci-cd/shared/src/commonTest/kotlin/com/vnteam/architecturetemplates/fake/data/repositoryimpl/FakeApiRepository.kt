package com.vnteam.architecturetemplates.fake.data.repositoryimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.fake.domain.models.fakeException

class FakeApiRepository : ApiRepository {
    var isSuccessResult = true
    var isInsertDemoObjectsToApiCalled = false
    var isDeleteDemoObjectByIdCalled = false
    var demoObject: DemoObject? = null
    var demoObjects: List<DemoObject>? = null

    override suspend fun getDemoObjectsFromApi(): List<DemoObject> {
        if (isSuccessResult) {
            return demoObjects.orEmpty()
        } else {
            throw fakeException
        }
    }

    override suspend fun insertDemoObjectsToApi(demoObjects: List<DemoObject>?) {
        if (isSuccessResult) {
            isInsertDemoObjectsToApiCalled = true
        } else {
            throw fakeException
        }
    }

    override suspend fun getDemoObjectById(demoObjectId: String?): DemoObject? {
        if (isSuccessResult) {
            return demoObject
        } else {
            throw fakeException
        }
    }

    override suspend fun deleteDemoObjectById(demoObjectId: String) {
        if (isSuccessResult) {
            isDeleteDemoObjectByIdCalled = true
        } else {
            throw fakeException
        }
    }
}
