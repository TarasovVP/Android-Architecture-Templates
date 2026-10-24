package com.vnteam.architecturetemplates.domain

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.data.repositoryimpl.FakeApiRepository
import com.vnteam.architecturetemplates.fake.data.repositoryimpl.FakeDBRepository
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObject
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObject2
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDemoObjectUseCaseTest : BaseKoinTest() {

    override val overrideModule: Module
        get() = module {
            single<ApiRepository> { FakeApiRepository() }
            single<DBRepository> { FakeDBRepository() }
        }

    private val getDemoObjectUseCase by inject<GetDemoObjectUseCase>()
    private val apiRepository by injectAs<ApiRepository, FakeApiRepository>()
    private val dbRepository by injectAs<DBRepository, FakeDBRepository>()

    @Test
    fun testGetDemoObject() = runTest {
        dbRepository.demoObject = fakeDemoObject
        val result =
            getDemoObjectUseCase.execute("1")
        assertEquals(fakeDemoObject, result)
    }

    @Test
    fun testGetDemoObjectIfDBNull() = runTest {
        dbRepository.demoObject = null
        apiRepository.demoObject = fakeDemoObject2
        val result =
            getDemoObjectUseCase.execute("2")
        assertEquals(fakeDemoObject2, result)
    }
}