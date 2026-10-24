package com.vnteam.architecturetemplates.domain

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromDBUseCase
import com.vnteam.architecturetemplates.domain.usecase.execute
import com.vnteam.architecturetemplates.fake.data.repositoryimpl.FakeDBRepository
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjects
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals

class GetDemoObjectsFromDBUseCaseTest : BaseKoinTest() {
    override val overrideModule: Module
        get() =
            module {
                single<DBRepository> { FakeDBRepository() }
            }

    private val getDemoObjectsUseCase by inject<GetDemoObjectsFromDBUseCase>()
    private val repository by injectAs<DBRepository, FakeDBRepository>()

    @Test
    fun testGetDemoObjectsFromDB() =
        runTest {
            repository.demoObjects = fakeDemoObjects
            val result = getDemoObjectsUseCase.execute().firstOrNull()
            assertEquals(fakeDemoObjects, result)
        }
}
