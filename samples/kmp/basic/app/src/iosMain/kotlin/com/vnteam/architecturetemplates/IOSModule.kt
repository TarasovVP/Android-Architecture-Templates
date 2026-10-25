package com.vnteam.architecturetemplates
import com.vnteam.architecturetemplates.data.database.DatabaseDriverFactory
import org.koin.dsl.module

val iosModule = module {
    single {
        DatabaseDriverFactory()
    }
    single {
        PlatformMessageDisplayer()
    }
}