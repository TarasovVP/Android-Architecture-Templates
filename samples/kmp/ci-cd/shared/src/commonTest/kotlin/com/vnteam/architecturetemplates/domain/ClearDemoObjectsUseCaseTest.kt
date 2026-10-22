package com.vnteam.architecturetemplates.domain

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.ClearDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.execute
import com.vnteam.architecturetemplates.fake.data.repositoryimpl.FakeDBRepository
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertTrue

class ClearDemoObjectsUseCaseTest : BaseKoinTest() {
    override val overrideModule: Module
        get() =
            module {
                single<DBRepository> { FakeDBRepository() }
            }

    private val clearDemoObjectUseCase by inject<ClearDemoObjectUseCase>()
    private val repository by injectAs<DBRepository, FakeDBRepository>()

    @Test
    fun testClearDemoObjects() =
        runTest {
            clearDemoObjectUseCase.execute()
            assertTrue(repository.isClearDemoObjectsCalled)
        }
}
