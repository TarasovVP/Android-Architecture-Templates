package com.vnteam.architecturetemplates.fake.domain.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.InsertDemoObjectsUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeException

class FakeInsertDemoObjectsUseCase : InsertDemoObjectsUseCase {
    var demoObjects: List<DemoObject>? = null
    var isSuccessful = true

    override suspend fun execute(params: List<DemoObject>) {
        if (isSuccessful) {
            demoObjects = params
        } else {
            throw fakeException
        }
    }
}
