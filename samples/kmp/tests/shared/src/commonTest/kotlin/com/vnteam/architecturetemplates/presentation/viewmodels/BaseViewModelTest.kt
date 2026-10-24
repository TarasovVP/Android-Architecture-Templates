package com.vnteam.architecturetemplates.presentation.viewmodels

import com.vnteam.architecturetemplates.BaseKoinTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseViewModelTest : BaseKoinTest() {

    override val overrideModule: Module = module { }

    @BeforeTest
    override fun setup() {
        super.setup()
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @AfterTest
    override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }
}
