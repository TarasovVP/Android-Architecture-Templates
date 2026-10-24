package com.vnteam.architecturetemplates.presentation.viewmodels

import com.vnteam.architecturetemplates.domain.usecase.ClearDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.DeleteDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromApiUseCase
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromDBUseCase
import com.vnteam.architecturetemplates.domain.usecase.InsertDemoObjectsUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObject
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjects
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjectsUI
import com.vnteam.architecturetemplates.fake.domain.models.fakeException
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeClearDemoObjectsUseCase
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeDeleteDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeGetDemoObjectsFromApiUseCase
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeGetDemoObjectsFromDBUseCase
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeInsertDemoObjectsUseCase
import com.vnteam.architecturetemplates.injectAs
import com.vnteam.architecturetemplates.presentation.intents.ListIntent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest : BaseViewModelTest() {
    override val overrideModule: Module
        get() =
            module {
                single<ClearDemoObjectUseCase> { FakeClearDemoObjectsUseCase() }
                single<GetDemoObjectsFromDBUseCase> { FakeGetDemoObjectsFromDBUseCase() }
                single<GetDemoObjectsFromApiUseCase> { FakeGetDemoObjectsFromApiUseCase() }
                single<InsertDemoObjectsUseCase> { FakeInsertDemoObjectsUseCase() }
                single<DeleteDemoObjectUseCase> { FakeDeleteDemoObjectUseCase() }
            }

    private val listViewModel by inject<ListViewModel>()

    private val fakeClearUseCase by injectAs<ClearDemoObjectUseCase, FakeClearDemoObjectsUseCase>()
    private val fakeGetDBUseCase by injectAs<GetDemoObjectsFromDBUseCase, FakeGetDemoObjectsFromDBUseCase>()
    private val fakeGetApiUseCase by injectAs<GetDemoObjectsFromApiUseCase, FakeGetDemoObjectsFromApiUseCase>()
    private val fakeInsertUseCase by injectAs<InsertDemoObjectsUseCase, FakeInsertDemoObjectsUseCase>()
    private val fakeDeleteUseCase by injectAs<DeleteDemoObjectUseCase, FakeDeleteDemoObjectUseCase>()

    @Test
    fun testClearDemoObjects() =
        runTest {
            listViewModel.processIntent(ListIntent.ClearDemoObjects())
            runCurrent()

            assertTrue(fakeClearUseCase.isExecuteCalled)
            val finalState = listViewModel.state.first()
            assertTrue(finalState.clearSuccessResult)
        }

    @Test
    fun testClearDemoObjectsExeption() =
        runTest {
            fakeClearUseCase.isSuccessful = false
            listViewModel.processIntent(ListIntent.ClearDemoObjects())
            runCurrent()

            assertEquals(
                fakeException.message,
                listViewModel.screenState.value.appMessageState.messageText,
            )
        }

    @Test
    fun testLoadDemoObjects() =
        runTest {
            fakeGetApiUseCase.demoObjects = fakeDemoObjects
            fakeGetDBUseCase.demoObjects = fakeDemoObjects
            listViewModel.processIntent(ListIntent.LoadDemoObjects(isInit = true))
            runCurrent()

            assertEquals(fakeDemoObjects, fakeInsertUseCase.demoObjects)
            val state = listViewModel.state.first()
            assertEquals(fakeDemoObjectsUI, state.demoObjectUIs)
        }

    @Test
    fun testLoadDemoObjectException() =
        runTest {
            fakeGetApiUseCase.isSuccessful = false

            listViewModel.processIntent(ListIntent.LoadDemoObjects(isInit = true))
            runCurrent()
            assertEquals(
                fakeException.message,
                listViewModel.screenState.value.appMessageState.messageText,
            )
        }

    @Test
    fun testDeleteDemoObject() =
        runTest {
            listViewModel.processIntent(ListIntent.DeleteDemoObject(fakeDemoObject.demoObjectId.orEmpty()))
            runCurrent()

            assertTrue(fakeDeleteUseCase.isExecuteCalled)
            val finalState = listViewModel.state.first()
            assertTrue(finalState.successResult)
        }

    @Test
    fun testDeleteDemoObjectException() =
        runTest {
            fakeDeleteUseCase.isSuccessful = false
            listViewModel.processIntent(ListIntent.DeleteDemoObject(fakeDemoObject.demoObjectId.orEmpty()))
            runCurrent()
            assertEquals(
                fakeException.message,
                listViewModel.screenState.value.appMessageState.messageText,
            )
        }
}
