package com.vnteam.architecturetemplates.di_android

import android.app.Application
import com.vnteam.architecturetemplates.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

fun doInitKoin(application: Application) = startKoin {
    androidLogger()
    androidContext(application)
    modules(appModule + androidModule)
}