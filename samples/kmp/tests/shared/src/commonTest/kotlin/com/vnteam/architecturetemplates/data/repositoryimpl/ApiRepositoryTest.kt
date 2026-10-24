package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.data.network.ApiService
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.fake.data.network.FakeApiService
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObject
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjects
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjectsResponse
import com.vnteam.architecturetemplates.fake.domain.models.fakeException
import com.vnteam.architecturetemplates.fake.domain.models.toDemoObjectResponse
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ApiRepositoryTest : BaseKoinTest() {

    override val overrideModule: Module
        get() = module {
            single<ApiService> { FakeApiService() }
        }

    private val repository by inject<ApiRepository>()
    private val apiService by injectAs<ApiService, FakeApiService>()

    @Test
    fun testGetDemoObjectsSuccessful() = runTest {
        apiService.demoObjectsResponse = fakeDemoObjectsResponse
        val result = repository.getDemoObjectsFromApi()
        assertEquals(fakeDemoObjects, result)

    }

    @Test
    fun testGetDemoObjectsFailed() = runTest {
        apiService.isSuccessful = false
        val exception = assertFailsWith<Exception> {
            repository.getDemoObjectsFromApi()
        }
        assertEquals(fakeException.message, exception.message)

    }

    @Test
    fun testInsertDemoObjectsSuccessful() = runTest {
        val result = repository.insertDemoObjectsToApi(fakeDemoObjects)
        assertEquals(Unit, result)
        assertEquals(
            fakeDemoObjects.map { it.toDemoObjectResponse() },
            apiService.demoObjectsResponse
        )
    }

    @Test
    fun testInsertDemoObjectsFailed() = runTest {
        apiService.isSuccessful = false
        val exception = assertFailsWith<Exception> {
            repository.insertDemoObjectsToApi(fakeDemoObjects)
        }
        assertEquals(fakeException.message, exception.message)
    }

    @Test
    fun testGetDemoObjectByIdSuccessful() = runTest {
        apiService.demoObjectsResponse = fakeDemoObjectsResponse
        val id = fakeDemoObject.demoObjectId
        val result = repository.getDemoObjectById(id)
        assertEquals(
            fakeDemoObjectsResponse.first { it.demoObjectId == id },
            result?.toDemoObjectResponse()
        )
    }

    @Test
    fun testGetDemoObjectByIdFailed() = runTest {
        apiService.isSuccessful = false
        val exception = assertFailsWith<Exception> {
            repository.getDemoObjectById(fakeDemoObject.demoObjectId)
        }
        assertEquals(fakeException.message, exception.message)
    }

    @Test
    fun testDeleteDemoObjectByIdSuccessful() = runTest {
        apiService.demoObjectsResponse = fakeDemoObjectsResponse
        val id = fakeDemoObject.demoObjectId.orEmpty()
        val result = repository.deleteDemoObjectById(id)
        assertEquals(Unit, result)
        assertTrue(apiService.demoObjectsResponse?.none { it.demoObjectId == id } == true)
    }

    @Test
    fun testDeleteDemoObjectByIdFailed() = runTest {
        apiService.isSuccessful = false
        val exception = assertFailsWith<Exception> {
            repository.deleteDemoObjectById(fakeDemoObject.demoObjectId.orEmpty())
        }
        assertEquals(fakeException.message, exception.message)
    }
}