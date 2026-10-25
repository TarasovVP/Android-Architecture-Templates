package com.vnteam.architecturetemplates

import android.app.Application
import com.vnteam.architecturetemplates.di.AppComponent
import com.vnteam.architecturetemplates.di.AppModule
import com.vnteam.architecturetemplates.di.DaggerAppComponent
import io.realm.Realm

class AppApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Realm.init(this)
    }

    companion object {
        var instance: AppApplication? = null
            private set
    }
}