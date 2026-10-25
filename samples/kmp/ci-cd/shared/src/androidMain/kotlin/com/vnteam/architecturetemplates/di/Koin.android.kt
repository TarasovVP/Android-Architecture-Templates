package com.vnteam.architecturetemplates.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module

actual fun platformModule(): Module = androidModule

fun initKoin(application: Application) {
    initKoin { koinApplication ->
        koinApplication.apply {
            androidContext(application)
        }
    }
}
