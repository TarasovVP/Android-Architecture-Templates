package com.vnteam.architecturetemplates.di_desktop

import com.vnteam.architecturetemplates.di.appModule
import org.koin.core.context.startKoin

fun doInitKoin() = startKoin {
    modules(appModule, desktopModule)
}