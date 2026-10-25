package com.vnteam.architecturetemplates.domain

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.InsertDemoObjectsUseCase
import com.vnteam.architecturetemplates.fake.data.repositoryimpl.FakeDBRepository
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjects
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertTrue

class InsertDemoObjectsUseCaseTest : BaseKoinTest() {
    override val overrideModule: Module
        get() =
            module {
                single<DBRepository> { FakeDBRepository() }
            }

    private val insertDemoObjects by inject<InsertDemoObjectsUseCase>()
    private val repository by injectAs<DBRepository, FakeDBRepository>()

    @Test
    fun testInsertDemoObjectsToDB() =
        runTest {
            insertDemoObjects.execute(fakeDemoObjects)
            assertTrue(repository.isInsertDemoObjectsToDBCalled)
        }
}
