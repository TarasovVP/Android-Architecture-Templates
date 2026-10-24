package com.vnteam.architecturetemplates.domain

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.usecase.DeleteDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.data.repositoryimpl.FakeDBRepository
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObject
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertTrue

class DeleteDemoObjectUseCaseTest : BaseKoinTest() {

    override val overrideModule: Module
        get() = module {
            single<DBRepository> { FakeDBRepository() }
        }

    private val deleteDemoObjectUseCase by inject<DeleteDemoObjectUseCase>()
    private val repository by injectAs<DBRepository, FakeDBRepository>()

    @Test
    fun testDeleteDemoObject() = runTest {
        deleteDemoObjectUseCase.execute(fakeDemoObject.demoObjectId.orEmpty())
        assertTrue(repository.isDeleteDemoObjectByIdCalled)
    }
}