package com.vnteam.architecturetemplates.di

import com.vnteam.architecturetemplates.data.database.DatabaseDriverFactory
import com.vnteam.architecturetemplates.data.local.Preferences
import com.vnteam.architecturetemplates.data.local.PreferencesFactory
import com.vnteam.architecturetemplates.shared.TextToSpeechHelper
import org.koin.dsl.module

val desktopModule =
    module {
        single {
            DatabaseDriverFactory()
        }
        single<Preferences> {
            PreferencesFactory()
        }
        single {
            TextToSpeechHelper()
        }
    }
