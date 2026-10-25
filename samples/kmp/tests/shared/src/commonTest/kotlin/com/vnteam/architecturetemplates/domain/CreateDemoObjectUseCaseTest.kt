package com.vnteam.architecturetemplates.domain

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.usecase.CreateDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.data.repositoryimpl.FakeApiRepository
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObject
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertTrue

class CreateDemoObjectUseCaseTest : BaseKoinTest() {

    override val overrideModule: Module
        get() = module {
            single<ApiRepository> { FakeApiRepository() }
        }

    private val createDemoObjectUseCase by inject<CreateDemoObjectUseCase>()
    private val repository by injectAs<ApiRepository, FakeApiRepository>()

    @Test
    fun testCreateDemoObject() = runTest {
        createDemoObjectUseCase.execute(fakeDemoObject)
        assertTrue(repository.isInsertDemoObjectsToApiCalled)
    }
}