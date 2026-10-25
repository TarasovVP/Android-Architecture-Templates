package com.vnteam.architecturetemplates

import android.app.Application
import com.vnteam.architecturetemplates.di.initKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(this)
    }
}
