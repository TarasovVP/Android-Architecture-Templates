package com.vnteam.architecturetemplates.domain

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromApiUseCase
import com.vnteam.architecturetemplates.domain.usecase.execute
import com.vnteam.architecturetemplates.fake.data.repositoryimpl.FakeApiRepository
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjects
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDemoObjectsFromApiUseCaseTest : BaseKoinTest() {
    override val overrideModule: Module
        get() =
            module {
                single<ApiRepository> { FakeApiRepository() }
            }

    private val getDemoObjectsUseCase by inject<GetDemoObjectsFromApiUseCase>()
    private val repository by injectAs<ApiRepository, FakeApiRepository>()

    @Test
    fun testGetDemoObjectsFromApi() =
        runTest {
            repository.demoObjects = fakeDemoObjects
            val result = getDemoObjectsUseCase.execute()
            assertEquals(fakeDemoObjects, result)
        }
}
