package com.vnteam.architecturetemplates.data.repositoryimpl

import com.vnteam.architecturetemplates.BaseKoinTest
import com.vnteam.architecturetemplates.data.database.DemoObjectDao
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.fake.data.database.FakeDemoObjectDao
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObject
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjects
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjectsWithOwner
import com.vnteam.architecturetemplates.injectAs
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DBRepositoryTest : BaseKoinTest() {
    override val overrideModule: Module
        get() =
            module {
                single<DemoObjectDao> { FakeDemoObjectDao() }
            }

    private val repository by inject<DBRepository>()
    private val demoObjectDao by injectAs<DemoObjectDao, FakeDemoObjectDao>()

    @Test
    fun clearDemoObjects() =
        runTest {
            repository.insertDemoObjectsToDB(fakeDemoObjects)
            assertFalse(demoObjectDao.demoObjects.isEmpty())
            repository.clearDemoObjects()
            assertTrue(demoObjectDao.demoObjects.isEmpty())
        }

    @Test
    fun testInsertDemoObjectsToDB() =
        runTest {
            val fakeDemoObjects = fakeDemoObjects
            repository.insertDemoObjectsToDB(fakeDemoObjects)
            assertEquals(fakeDemoObjectsWithOwner, demoObjectDao.demoObjects)
        }

    @Test
    fun testGetDemoObjectsFromDB() =
        runTest {
            repository.insertDemoObjectsToDB(fakeDemoObjects)
            val result = repository.getDemoObjectsFromDB().firstOrNull()
            assertEquals(fakeDemoObjects, result)
        }

    @Test
    fun testGetDemoObjectById() =
        runTest {
            repository.insertDemoObjectsToDB(fakeDemoObjects)
            val result =
                repository.getDemoObjectById(fakeDemoObject.demoObjectId.orEmpty()).firstOrNull()
            assertEquals(fakeDemoObject, result)
        }

    @Test
    fun testDeleteDemoObjectById() =
        runTest {
            repository.insertDemoObjectsToDB(fakeDemoObjects)
            assertFalse(demoObjectDao.demoObjects.isEmpty())
            val demoObjectToDelete = fakeDemoObjects.first()
            repository.deleteDemoObjectById(demoObjectToDelete.demoObjectId.orEmpty())
            assertTrue(demoObjectDao.demoObjects.none { it.demoObjectId == demoObjectToDelete.demoObjectId })
        }
}
