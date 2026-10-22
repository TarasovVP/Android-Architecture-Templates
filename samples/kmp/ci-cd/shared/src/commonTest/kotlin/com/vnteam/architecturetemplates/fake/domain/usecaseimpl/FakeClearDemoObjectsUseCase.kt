package com.vnteam.architecturetemplates.fake.domain.usecaseimpl

import com.vnteam.architecturetemplates.domain.usecase.ClearDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeException

class FakeClearDemoObjectsUseCase : ClearDemoObjectUseCase {
    var isExecuteCalled = false
    var isSuccessful = true

    override suspend fun execute(params: Nothing?) {
        isExecuteCalled = true
        if (!isSuccessful) {
            throw fakeException
        }
    }
}
