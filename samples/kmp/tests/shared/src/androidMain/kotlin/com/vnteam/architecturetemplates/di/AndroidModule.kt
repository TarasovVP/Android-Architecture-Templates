package com.vnteam.architecturetemplates.di

import com.vnteam.architecturetemplates.data.database.DatabaseDriverFactory
import com.vnteam.architecturetemplates.data.local.Preferences
import com.vnteam.architecturetemplates.data.local.PreferencesFactory
import com.vnteam.architecturetemplates.shared.TextToSpeechHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidModule = module {
    single {
        DatabaseDriverFactory(androidContext())
    }
    single<Preferences> {
        PreferencesFactory(androidContext())
    }
    single {
        TextToSpeechHelper(androidContext())
    }
}