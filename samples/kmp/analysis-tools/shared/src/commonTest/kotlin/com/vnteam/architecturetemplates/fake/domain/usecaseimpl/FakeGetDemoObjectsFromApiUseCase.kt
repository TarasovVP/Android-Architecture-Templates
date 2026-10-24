package com.vnteam.architecturetemplates.fake.domain.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromApiUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeException

class FakeGetDemoObjectsFromApiUseCase : GetDemoObjectsFromApiUseCase {
    var demoObjects: List<DemoObject>? = null
    var isSuccessful = true

    override suspend fun execute(params: Nothing?): List<DemoObject> {
        if (isSuccessful) {
            return demoObjects.orEmpty()
        } else {
            throw fakeException
        }
    }
}
