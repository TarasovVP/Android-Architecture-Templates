@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package com.vnteam.architecturetemplates.di

import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

private var koinRef: Koin? = null

fun initKoin(config: (KoinApplication) -> Unit = {}) {
    if (koinRef == null) {
        val app =
            startKoin {
                config(this)
                modules(commonModule, platformModule())
            }
        koinRef = app.koin
    }
}

expect fun platformModule(): Module
