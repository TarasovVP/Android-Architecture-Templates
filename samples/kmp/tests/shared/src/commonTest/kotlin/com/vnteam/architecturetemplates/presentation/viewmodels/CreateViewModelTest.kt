package com.vnteam.architecturetemplates.presentation.viewmodels

import com.vnteam.architecturetemplates.domain.usecase.CreateDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.InsertDemoObjectsUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObject
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjectUI
import com.vnteam.architecturetemplates.fake.domain.models.fakeException
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeCreateDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeGetDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeInsertDemoObjectsUseCase
import com.vnteam.architecturetemplates.injectAs
import com.vnteam.architecturetemplates.presentation.intents.CreateIntent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CreateViewModelTest : BaseViewModelTest() {

    override val overrideModule: Module
        get() = module {
            single<GetDemoObjectUseCase> { FakeGetDemoObjectUseCase() }
            single<InsertDemoObjectsUseCase> { FakeInsertDemoObjectsUseCase() }
            single<CreateDemoObjectUseCase> { FakeCreateDemoObjectUseCase() }
        }

    private val createViewModel by inject<CreateViewModel>()

    private val fakeGetDemoObjectUseCase by injectAs<GetDemoObjectUseCase, FakeGetDemoObjectUseCase>()
    private val fakeInsertDemoObjectsUseCase by injectAs<InsertDemoObjectsUseCase, FakeInsertDemoObjectsUseCase>()
    private val fakeCreateDemoObjectUseCase by injectAs<CreateDemoObjectUseCase, FakeCreateDemoObjectUseCase>()

    @Test
    fun testLoadDemoObject() = runTest {
        fakeGetDemoObjectUseCase.demoObject = fakeDemoObject

        createViewModel.processIntent(CreateIntent.LoadDemoObject(fakeDemoObject.demoObjectId.orEmpty()))
        runCurrent()

        val currentState = createViewModel.state.first()
        val uiObject = currentState.demoObject.value
        assertEquals(fakeDemoObjectUI, uiObject)
        assertFalse(currentState.successResult)
    }

    @Test
    fun testLoadDemoObjectException() = runTest {
        fakeGetDemoObjectUseCase.isSuccessful = false

        createViewModel.processIntent(CreateIntent.LoadDemoObject(fakeDemoObject.demoObjectId.orEmpty()))
        runCurrent()
        assertEquals(
            fakeException.message,
            createViewModel.screenState.value.appMessageState.messageText
        )
    }

    @Test
    fun testCreateDemoObject() = runTest {
        createViewModel.processIntent(CreateIntent.CreateDemoObject(fakeDemoObjectUI))
        runCurrent()

        assertEquals(
            fakeCreateDemoObjectUseCase.demoObject,
            fakeDemoObject
        )
        assertEquals(
            fakeInsertDemoObjectsUseCase.demoObjects,
            listOf(fakeDemoObject)
        )

        val currentState = createViewModel.state.first()
        assertTrue(currentState.successResult)
    }

    @Test
    fun testCreateDemoObjectException() = runTest {
        fakeCreateDemoObjectUseCase.isSuccessful = false

        createViewModel.processIntent(CreateIntent.CreateDemoObject(fakeDemoObjectUI))
        runCurrent()
        assertEquals(
            fakeException.message,
            createViewModel.screenState.value.appMessageState.messageText
        )
    }
}