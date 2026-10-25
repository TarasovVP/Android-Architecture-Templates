package com.vnteam.architecturetemplates.di

import org.koin.core.context.startKoin

fun doInitKoin() =
    startKoin {
        modules(appModule, desktopModule)
    }
