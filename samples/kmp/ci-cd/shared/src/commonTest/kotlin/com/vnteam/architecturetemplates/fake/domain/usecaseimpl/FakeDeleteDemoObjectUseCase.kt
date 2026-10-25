package com.vnteam.architecturetemplates.fake.domain.usecaseimpl

import com.vnteam.architecturetemplates.domain.usecase.DeleteDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeException

class FakeDeleteDemoObjectUseCase : DeleteDemoObjectUseCase {
    var isExecuteCalled = false
    var isSuccessful = true

    override suspend fun execute(params: String) {
        isExecuteCalled = true
        if (!isSuccessful) {
            throw fakeException
        }
    }
}
