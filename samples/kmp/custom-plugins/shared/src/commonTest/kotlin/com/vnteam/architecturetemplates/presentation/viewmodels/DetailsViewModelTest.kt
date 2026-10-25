package com.vnteam.architecturetemplates.presentation.viewmodels

import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectUseCase
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObject
import com.vnteam.architecturetemplates.fake.domain.models.fakeDemoObjectUI
import com.vnteam.architecturetemplates.fake.domain.models.fakeException
import com.vnteam.architecturetemplates.fake.domain.usecaseimpl.FakeGetDemoObjectUseCase
import com.vnteam.architecturetemplates.injectAs
import com.vnteam.architecturetemplates.presentation.intents.DetailsIntent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest : BaseViewModelTest() {
    override val overrideModule: Module
        get() =
            module {
                single<GetDemoObjectUseCase> { FakeGetDemoObjectUseCase() }
            }

    private val detailsViewModel by inject<DetailsViewModel>()

    private val fakeGetDemoObjectUseCase by injectAs<GetDemoObjectUseCase, FakeGetDemoObjectUseCase>()

    @BeforeTest
    override fun setup() {
        super.setup()
        fakeGetDemoObjectUseCase.demoObject = fakeDemoObject
    }

    @Test
    fun testLoadDemoObject() =
        runTest {
            detailsViewModel.processIntent(
                DetailsIntent.LoadDemoObject(
                    fakeDemoObject.demoObjectId.orEmpty(),
                ),
            )
            runCurrent()

            val currentState = detailsViewModel.state.first()
            assertEquals(fakeDemoObjectUI, currentState.demoObjectUI)
        }

    @Test
    fun testLoadDemoObjectIsUpdatedClearsState() =
        runTest {
            detailsViewModel.processIntent(
                DetailsIntent.LoadDemoObject(
                    fakeDemoObject.demoObjectId.orEmpty(),
                    isUpdated = false,
                ),
            )
            runCurrent()
            assertNotNull(detailsViewModel.state.first().demoObjectUI)

            detailsViewModel.processIntent(
                DetailsIntent.LoadDemoObject(
                    fakeDemoObject.demoObjectId.orEmpty(),
                    isUpdated = true,
                ),
            )
            assertNull(detailsViewModel.state.first().demoObjectUI)
        }

    @Test
    fun testLoadDemoObjectException() =
        runTest {
            fakeGetDemoObjectUseCase.isSuccessful = false

            detailsViewModel.processIntent(
                DetailsIntent.LoadDemoObject(
                    fakeDemoObject.demoObjectId.orEmpty(),
                    isUpdated = false,
                ),
            )
            runCurrent()
            assertEquals(
                fakeException.message,
                detailsViewModel.screenState.value.appMessageState.messageText,
            )
        }
}
