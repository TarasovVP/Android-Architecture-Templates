package com.vnteam.architecturetemplates

import android.app.Application
import com.vnteam.architecturetemplates.di.doInitKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        doInitKoin(this)
    }
}
