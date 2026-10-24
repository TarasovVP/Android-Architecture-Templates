package com.vnteam.architecturetemplates

import com.vnteam.architecturetemplates.di.testModule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class BaseKoinTest : KoinTest {

    abstract val overrideModule: Module

    @BeforeTest
    open fun setup() {
        startKoin {
            modules(
                testModule + overrideModule
            )
        }
    }

    @AfterTest
    open fun tearDown() {
        stopKoin()
    }
}