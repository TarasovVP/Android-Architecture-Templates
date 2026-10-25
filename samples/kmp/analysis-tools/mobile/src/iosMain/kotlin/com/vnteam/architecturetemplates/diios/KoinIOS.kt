package com.vnteam.architecturetemplates.diios

import com.vnteam.architecturetemplates.di.appModule
import org.koin.core.context.startKoin

fun doInitKoin() =
    startKoin {
        modules(appModule, iosModule)
    }
