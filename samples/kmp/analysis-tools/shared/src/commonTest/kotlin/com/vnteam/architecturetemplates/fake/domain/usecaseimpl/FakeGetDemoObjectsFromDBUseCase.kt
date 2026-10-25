package com.vnteam.architecturetemplates.fake.domain.usecaseimpl

import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromDBUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGetDemoObjectsFromDBUseCase : GetDemoObjectsFromDBUseCase {
    var demoObjects: List<DemoObject>? = null
    var isSuccessful = true

    override suspend fun execute(params: Nothing?): Flow<List<DemoObject>?> {
        if (isSuccessful) {
            return flowOf(demoObjects)
        } else {
            throw fakeException
        }
    }
}
