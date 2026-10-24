package com.vnteam.architecturetemplates.fake.domain.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeException

class FakeGetDemoObjectUseCase : GetDemoObjectUseCase {

    var demoObject: DemoObject? = null
    var isSuccessful = true

    override suspend fun execute(params: String): DemoObject? {
        if (isSuccessful) {
            return demoObject
        } else {
            throw fakeException
        }
    }
}