package com.vnteam.architecturetemplates.di_desktop

import com.vnteam.architecturetemplates.data.database.DatabaseDriverFactory
import com.vnteam.architecturetemplates.data.local.PreferencesFactory
import com.vnteam.architecturetemplates.presentation.TextToSpeechHelper
import org.koin.dsl.module

val desktopModule = module {
    single {
        DatabaseDriverFactory()
    }
    single {
        PreferencesFactory()
    }
    single {
        TextToSpeechHelper()
    }
}