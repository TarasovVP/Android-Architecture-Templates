package com.vnteam.architecturetemplates.fake.domain.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.CreateDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeException

class FakeCreateDemoObjectUseCase : CreateDemoObjectUseCase {
    var demoObject: DemoObject? = null
    var isSuccessful = true

    override suspend fun execute(params: DemoObject) {
        if (isSuccessful) {
            demoObject = params
        } else {
            throw fakeException
        }
    }
}
